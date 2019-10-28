package com.wenli.springbootdemo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wenli.springbootdemo.common.HttpCode;
import com.wenli.springbootdemo.common.MyException;
import com.wenli.springbootdemo.common.PageParam;
import com.wenli.springbootdemo.dao.OrderHeadDao;
import com.wenli.springbootdemo.dao.ProductDao;
import com.wenli.springbootdemo.model.OrderDetail;
import com.wenli.springbootdemo.model.OrderHead;
import com.wenli.springbootdemo.model.Product;
import com.wenli.springbootdemo.model.ShoppingCar;
import com.wenli.springbootdemo.service.OrderDetailService;
import com.wenli.springbootdemo.service.OrderHeadService;
import com.wenli.springbootdemo.service.ProductService;
import com.wenli.springbootdemo.service.ShoppingCarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: springbootdemo
 * @description:
 * @author: Koty
 * @create: 2019-07-11 19:19
 **/
@Slf4j
@Service
public class OrderHeadServiceImpl implements OrderHeadService {

    @Autowired
    OrderHeadDao orderHeadDao;

    @Autowired
    ProductService productService;

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    ShoppingCarService shoppingCarService;

    @Cacheable(key = "#p0", value = "OrderHeads")
    @Override
    public Object getAllOrderHead(PageParam<OrderHead> pageParam) {

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

        List<OrderHead> orderHeadList = orderHeadDao.getAllOrderHead(pageParam.getModel());
        // 将OrderHeaddao中查出来的OrderHeadlist赋给OrderHeadpageinfo并返回
        PageInfo<OrderHead> orderHeadPageInfo = new PageInfo<>(orderHeadList);

        return orderHeadPageInfo;
    }



    /**p
     * @Cacheable(key = "#p0", value = "OrderHeads")
     * 当缓存中没有想要的值（key）时，才调用下面的方法,
     * #p（取参数）0（下面方法中的第1个）及id  赋值给key
     * value 的值为下面方法返回值   todo(是不是对的)
     */
    @Cacheable(key = "#p0", value = "OrderHeads")
    @Override
    public OrderHead getOrderHeadById(int id) {
        log.info("走的是数据库");
        return orderHeadDao.getOrderHeadById(id);
    }


    @CachePut(key = "#p0.id", value = "OrderHeads")
    @Override
    public Object addOrderHead(OrderHead orderHead) {

        log.info("走的是数据库");

        orderHeadDao.addOrderHead(orderHead); // mapper文件中添加完后会执行selectKey并返回id的值

        return orderHeadDao.getOrderHeadById(orderHead.getId()); // 根据id的值得到整个对象并返回
    }

    @CacheEvict(key = "#p0", value = "OrderHeads")
    @Override
    public boolean deleteOrderHeadById(int id) {

        log.info("走的是数据库");

        return orderHeadDao.deleteOrderHeadById(id) == 1;
    }

    @Override
    public boolean updateOrderHead(OrderHead orderHead) {

        if(StringUtils.isEmpty(orderHead.getId())){
            throw new MyException(HttpCode.ERROR).msg("通过id修改订单表头时，id不能为空");
        }

        return orderHeadDao.updateOrderHead(orderHead) == 1;
    }


    /**
     * 当用户点击结算购物车时，后端拿到一个要执行支付操作的商品集合，然后生成一个订单头
     * @param shoppingCarList 要执行支付操作的商品集合
     * @return 执行操作后生成的订单头
     */
    @Override
    public OrderHead createOrderHead(List<ShoppingCar> shoppingCarList) {

        if (shoppingCarList.size()==0) {
            throw new MyException(HttpCode.ERROR).msg("生成订单失败，原因：没有要支付的商品");
        }

        OrderHead orderHead = new OrderHead(); // 最后要生成的订单表头

        /**
         * 生成的订单头中包括的字段：
         * 1.总的商品件数
         * 2.默认显示第一个商品的图片和名称
         * 3.本次支付操作的总价格
         * 4.本次支付操作的总折扣
         * 5.本次支付操作的总秒杀折扣
         * 6.执行本次操作的用户id
         */
        int totalProductNum = 0; // 1.总的商品件数
        String firstProductName = shoppingCarList.get(0).getProductName(); // 2.第一个商品的名称
        String firstProductImg = shoppingCarList.get(0).getProductImg(); // 2.第一个商品的图片
        double totalPrice = 0; // 3.本次支付操作的总价格，循环遍历实现累加
        double totalDiscount = 0; // 4.本次支付操作的总折扣,计算总折扣，循环遍历实现累加
        double totalKillDiscount = 0; // 5.本次支付操作的总秒杀折扣,计算总秒杀折扣，循环遍历实现累加
        int userId = shoppingCarList.get(0).getUserId(); // 6.执行本次操作的用户id

        /**
         * 循环遍历购物车商品集合
         * 1.计算商品总件数（一种商品可以买多件，商品集合中可以有多种商品）
         * 2.计算本次支付操作的总价格（同上）
         * 3.计算本次支付操作的总折扣
         * 4.计算本次支付操作的总秒杀折扣
         */
        Product product; // 要执行支付操作的商品，在遍历商品集合时会取出一些商品字段信息
        for(ShoppingCar shoppingCar : shoppingCarList){
            totalProductNum += shoppingCar.getProductNum(); // 1.计算商品总件数

            // 初始化要执行支付操作的商品
            product = productService.getProductById(shoppingCar.getProductId());
            if (product == null) {
                throw new MyException(HttpCode.ERROR).msg("您下单的商品查询不到");
            }
            if(product.getDeserveNum() < shoppingCar.getProductNum()){
                throw new MyException(HttpCode.ERROR).msg("您下单的商品"+shoppingCar.getProductName()+"库存不足！下单失败！");
            }

            double discount = product.getIsInDiscount()==2 ? product.getDiscount() : 0; // 商品折扣
            double killDiscount = product.getIsInKill()==2 ? product.getKillDiscount() : 0; // 商品秒杀折扣
            // 3.计算本次支付操作的总折扣
            totalDiscount += discount * shoppingCar.getProductNum();
            // 4.计算本次支付操作的总秒杀折扣
            totalKillDiscount += killDiscount * shoppingCar.getProductNum();

            // 2.计算本次支付操作的总价格
            totalPrice += ( product.getNormalPrice() - discount - killDiscount ) * shoppingCar.getProductNum();

        }

        /**
         * 添加订单表头到数据库
         */
        orderHead.setKillDiscount(totalKillDiscount);
        orderHead.setDiscount(totalDiscount);
        orderHead.setFirstProductImg(firstProductImg);
        orderHead.setFirstProductName(firstProductName);
        orderHead.setTotalPrice(totalPrice);
        orderHead.setTotalProductNum(totalProductNum);
        orderHead.setUserId(userId);
        orderHeadDao.addOrderHead(orderHead);

        // 如果订单表头创建成功  需要及时减去所有库存
        for(ShoppingCar shoppingCar:shoppingCarList){
            product=productService.getProductById(shoppingCar.getProductId());
            if(product==null){
                throw new MyException(HttpCode.ERROR).msg("您下单的商品查询不到");
            }
            // 计算新库存
            int newDeserveNum = product.getDeserveNum() - shoppingCar.getProductNum();
            // 减掉库存
            product.setDeserveNum(newDeserveNum);
            // sql update
            boolean flag = productService.updateProduct(product);
            if(!flag){
                throw new MyException(HttpCode.ERROR).msg("库存扣取操作失败");
            }
        }

        /**
         * 添加订单详情到数据库
         * (后期如果一个订单商品数量过多,可以将该方法设为异步操作,如果是分布式架构可采用消息队列提高后台响应速度)
         */
        OrderDetail orderDetail;
        // 创建一个变量  存放插入成功的记录条数
        int successInsert = 0;
        for(ShoppingCar shoppingCar:shoppingCarList){
            orderDetail = new OrderDetail();
            double paymentPrice;
            BeanUtils.copyProperties(shoppingCar,orderDetail); // 从shoppingCar拷贝到orderDetail
            // 计算订单支付价格
            paymentPrice = orderDetail.getNormalPrice() - (orderDetail.getIsInDiscount()==2?orderDetail.getDiscount():0) - (orderDetail.getIsInKill()==2?orderDetail.getKillDiscount():0);
            orderDetail.setPaymentPrice(paymentPrice);
            orderDetail.setOrderHeadId(orderHead.getId()); // 添加表头id
            orderDetail.setId(0); // 将订单详情id设为空让数据库自增，mapper文件中int类型的0会被当做null处理
            // sql  add
            successInsert += (orderDetailService.addOrderDetail(orderDetail)!=null?1:0);
        }

        if(successInsert != shoppingCarList.size()){ // 插入成功的记录条数与要购买的商品数目不相同
            throw new MyException(HttpCode.ERROR).msg("订单详情数据录入不完整");
        }

        //订单生成成功  需要清空已被结算的购物车的商品
        for(ShoppingCar shoppingCar:shoppingCarList){
            shoppingCarService.deleteShoppingCarById(shoppingCar.getId());
        }

        // 返回订单表头，在数据库中用id再查询一遍并返回
        return orderHeadDao.getOrderHeadById(orderHead.getId());
    }


}
