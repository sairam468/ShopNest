package com.shopnest.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shopnest.entities.Order;
import com.shopnest.entities.OrderItem;
import com.shopnest.entities.Product;
import com.shopnest.entities.User;
import com.shopnest.repositories.OrderRepository;
import com.shopnest.repositories.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ProductRepository productRepo;

    private final OrderRepository orderRepo;

    public CartController(ProductRepository productRepo,OrderRepository orderRepo) {
        this.productRepo = productRepo;
        this.orderRepo=orderRepo;
    }

    // ✅ ADD TO CART
    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session) {

        List<Long> cart =
                (List<Long>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        cart.add(id);

        session.setAttribute("cart", cart);
        session.setAttribute("cartCount", cart.size());

        return "redirect:/dashboard";
    }

    // ✅ VIEW CART
    @GetMapping
    public String viewCart(HttpSession session, Model model) {

        List<Long> cart =
                (List<Long>) session.getAttribute("cart");

        List<Product> cartProducts = new ArrayList<>();

        if (cart != null) {
            for (Long id : cart) {
                productRepo.findById(id)
                        .ifPresent(cartProducts::add);
            }
        }

        model.addAttribute("cartProducts", cartProducts);
        model.addAttribute("cartCount", cartProducts.size());

        return "cart";
    }

    // ✅ PROCEED TO BUY
    @GetMapping("/checkout")
    public String checkout(HttpSession session) {

        // 🔐 LOGIN CHECK
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        if (session.getAttribute("cart") == null) {
            return "redirect:/dashboard";
        }

        return "checkout";
    }


    // ✅ PLACE ORDER
    @PostMapping("/place-order")
    public String placeOrder(HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        List<Long> cart = (List<Long>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            return "redirect:/dashboard";
        }

        Order order = new Order();
        order.setUser(user);

        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Long productId : cart) {
            Product product = productRepo.findById(productId).orElse(null);
            if (product == null) continue;

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(1);
            item.setPrice(product.getUnitPrice());

            total = total.add(product.getUnitPrice());
            items.add(item);
        }

        order.setItems(items);
        order.setTotalAmount(total);

        orderRepo.save(order); // saves order + items

        session.removeAttribute("cart");
        session.setAttribute("cartCount", 0);

        return "redirect:/order-success";
    }

    
    
}
