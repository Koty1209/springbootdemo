package com.wenli.springbootdemo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wenli.springbootdemo.common.HttpCode;
import com.wenli.springbootdemo.common.MyException;
import com.wenli.springbootdemo.common.PageParam;
import com.wenli.springbootdemo.dao.LogisticsDao;
import com.wenli.springbootdemo.dao.ProductDao;
import com.wenli.springbootdemo.model.Logistics;
import com.wenli.springbootdemo.model.OrderDetail;
import com.wenli.springbootdemo.model.Product;
import com.wenli.springbootdemo.model.User;
import com.wenli.springbootdemo.service.LogisticsService;
import com.wenli.springbootdemo.service.ProductService;
import com.wenli.springbootdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: springbootdemo
 * @description:
 * @author: Koty
 * @create: 2019-07-11 19:19
 **/
@Slf4j
@Service
public class LogisticsServiceImpl implements LogisticsService {

    @Autowired
    LogisticsDao logisticsDao;

    @Autowired
    UserService userService;

    @Cacheable(key = "#p0", value = "logistics")
    @Override
    public Object getAllLogistics(PageParam<Logistics> pageParam) {

        log.info("走的是数据库");

        /**
         * 1.设置分页的页面属性
         * pagehelper插件中的开始页设置，这里传了一个（第几页）pageNum，和一个（页尺寸）pageSize
         */
        PageHelper.startPage(pageParam.getPageNum(),pageParam.getPageSize());

        /**
         * 2.实现（多）排序
         * 根据排序参数实现排序功能，用遍历实现多条件排序（需要排序的参数可能不止一个：id、username...）
         * 按照排序参数数组中的索引顺序依次实现排序需求
         */
        for (int i = 0; i < pageParam.getOrderParams().length; i++) {
            // 调用pagehelper的排序方法对排序参数数组依次排序
            PageHelper.orderBy(pageParam.getOrderParams()[i]);
        }

        List<Logistics> logisticsList = logisticsDao.getAllLogistics(pageParam.getModel());
        // 将Logisticsdao中查出来的Logisticslist赋给Logisticspageinfo并返回
        PageInfo<Logistics> logisticsPageInfo = new PageInfo<>(logisticsList);


        return logisticsPageInfo;
    }



    /**p
     * @Cacheable(key = "#p0", value = "Logistics")
     * 当缓存中没有想要的值（key）时，才调用下面的方法,
     * #p（取参数）0（下面方法中的第1个）及id  赋值给key
     * value 的值为下面方法返回值   todo(是不是对的)
     */
    @Cacheable(key = "#p0", value = "logistics")
    @Override
    public Logistics getLogisticsById(int id) {
        log.info("走的是数据库");
        return logisticsDao.getLogisticsById(id);
    }


    @CachePut(key = "#p0.id", value = "logistics")
    @Override
    public Object addLogistics(Logistics logistics) {

        log.info("走的是数据库");

        logisticsDao.addLogistics(logistics); // mapper文件中添加完后会执行selectKey并返回id的值

        return logisticsDao.getLogisticsById(logistics.getId()); // 根据id的值得到整个对象并返回
    }

    @CacheEvict(key = "#p0", value = "logistics")
    @Override
    public boolean deleteLogisticsById(int id) {

        log.info("走的是数据库");

        return logisticsDao.deleteLogisticsById(id) == 1;
    }

    @Override
    public boolean updateLogistics(Logistics logistics) {

        if(StringUtils.isEmpty(logistics.getId())){
            throw new MyException(HttpCode.ERROR).msg("通过id修改物流时，id不能为空");
        }

        return logisticsDao.updateLogistics(logistics) == 1;
    }

    @Override
    public List<Logistics> getLogisticsByReceiverId(int receiverId) {
        return logisticsDao.getLogisticsByUserId(receiverId);
    }

    /**
     * 拿到用户购买的商品集合，依次生成物流信息
     * @param orderDetailList
     * @return
     */
    @Override
    public List<Logistics> createLogistics(List<OrderDetail> orderDetailList) {

        if (orderDetailList.size()==0) {
            throw new MyException(HttpCode.ERROR).msg("生成物流信息失败，原因：没有购买商品");
        }

        List<Logistics> logisticsList = new ArrayList<Logistics>(); // 最后要生成的物流信息集合

        /**
         * 生成物流信息（集合）需要的字段：
         * 1.订单号    2.发货时间（默认自动生成当前时间）  3.商品id  4.商品名称  5.商品图片  6.购买的商品数量
         * 7.用户支付的价格（商品总价格）    8.用户id（收货人id）    9.收货人名称（用户名）
         * 10.收货地址  11.发货地址  12.收货时间（用户收货时实现）  13.收货人邮件（用户邮件）
         */
        int receiverId = orderDetailList.get(0).getUserId(); // 8.用户id（收货人id）
        User user = userService.getUserById(receiverId);

        if (user == null) {
            throw new MyException(HttpCode.ERROR).msg("生成物流信息失败，原因：没有收货人");
        }
        String receiverName = user.getUsername(); // 9.收货人名称（用户名）
        String shippingAddress = user.getShippingAddress(); // 10.收货地址
        String receiverEmail = user.getEmail(); // 13.收货人邮件（用户邮件）

        /**
         * 循环遍历购买的商品集合，依次生成每种商品的物流信息
         */
        // 创建一个变量  存放插入成功的记录条数
        int successInsert = 0;
        for (OrderDetail orderDetail : orderDetailList){ // 遍历购买的商品集合，依次生成物流信息
            Logistics logistics = new Logistics(); // 要生成的物流信息类
            // 从购买商品类中拷贝相同字段信息到物流类中
            BeanUtils.copyProperties(orderDetail,logistics);
            /**
             * 添加购买商品中没有的字段信息到物流类中：
             * 1.订单号  2.用户支付的价格（商品总价格）  3.收货人id
             * 4.收货人名称  5.收货地址  6.收货人邮件
             */
            logistics.setOrderId(logistics.getId()); // 1.订单号   拷贝的id为orderId，将其改正
            logistics.setId(0); // 将id设为空让数据库自增，mapper文件中int类型的0会被当做null处理

            // 2.用户支付的价格（商品总价格） 计算用户支付的价格
            double totalPrice = 0;
            double normalPrice = orderDetail.getNormalPrice();
            double discount = orderDetail.getDiscount();
            double killDiscount = orderDetail.getKillDiscount();
            boolean isInDiscount = orderDetail.getIsInDiscount()==2;
            boolean isInKill = orderDetail.getIsInKill()==2;
            int productNum = orderDetail.getProductNum();
            totalPrice = ( normalPrice - (isInDiscount?discount:0) - (isInKill?killDiscount:0) ) * productNum;
            logistics.setTotalPrice(totalPrice);
            // 3.收货人id
            logistics.setReceiverId(receiverId);
            // 4.收货人名称
            logistics.setReceiverName(receiverName);
            // 5.收货地址
            logistics.setShippingAddress(shippingAddress);
            // 6.收货人邮件
            logistics.setReceiverEmail(receiverEmail);

            // sql add
            successInsert += logisticsDao.addLogistics(logistics); // 添加成功返回1，否则返回0

            // 将添加完的物流信息放到返回的集合中去
            logisticsList.add(logistics);

        }

        if(successInsert != logisticsList.size()){ // 插入成功的记录条数与要购买的商品数目不相同
            throw new MyException(HttpCode.ERROR).msg("生成物流信息数据不完整");
        }

        // 返回物流集合
        return logisticsList;
    }



}
