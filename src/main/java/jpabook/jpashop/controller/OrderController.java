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
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
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
    public String order(@RequestBody String json) { //String json
        try {
            JSONArray array = new JSONArray(json);
            System.out.println(json);

            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = (JSONObject)array.get(i);

                //orderService에 넘길 멤버 Id
                String MemberId = (String) jsonObject.get("memberId");
                Long MemberIdToInt = Long.parseLong(MemberId);

                //orderService에 넘길 아이템 Id
                String itemId = (String) jsonObject.get("itemId");
                Long itemIdToInt = Long.parseLong(itemId);

                //orderService에 넘길 주문 수량
                String count = (String) jsonObject.get("count");
                int OrderCount = Integer.parseInt(count);

                orderService.order(MemberIdToInt, itemIdToInt, OrderCount);
            }
            return "redirect:/orders";
        } catch (Exception e) {
            e.printStackTrace();
            return "FAIL";
        }
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";
    }
}