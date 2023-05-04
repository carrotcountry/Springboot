package jpabook.jpashop.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();
        model.addAttribute("formArray", new ArrayList<>());
        model.addAttribute("members", members);
        model.addAttribute("items", items);


        return "order/orderForm";
    }

    @ResponseBody
    @RequestMapping (value = "/order", method = RequestMethod.POST)
    public String order(@RequestBody String json) {
        try {
            System.out.println("json : " + json);
            JSONArray array = new JSONArray(json);


//            System.out.println("array : " + array);
//            System.out.println("array length : " + array.length());

//            for (int i = 0; i < array.length(); i++) {
//                Object a = array.get(i);
//                System.out.println("array" + i + " : " + a);
//            }
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return "FAIL";
        }
//      return "redirect:/orders";2
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";
    }
//    @PutMapping("/orders/{name}")
//    public String putOrder()




}


