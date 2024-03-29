package by.kaziulin.InternetShop.controller;

import by.kaziulin.InternetShop.dto.ProductDTO;
import by.kaziulin.InternetShop.service.ProductService;
import by.kaziulin.InternetShop.service.SessionObjectHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final SessionObjectHolder sessionObjectHolder;


    public ProductController(ProductService productService, SessionObjectHolder sessionObjectHolder) {
        this.productService = productService;
        this.sessionObjectHolder = sessionObjectHolder;
    }

    @GetMapping
    public String List(Model model) {
        sessionObjectHolder.addClick();
        List<ProductDTO> list = productService.getAll();
        model.addAttribute("products", list);
        return "products";
    }

   @GetMapping("/{id}/bucket")
    public String addBucket(@PathVariable Long id, Principal principal) {
       sessionObjectHolder.addClick();
        if (principal == null) {
            return "redirect:/products";
        }
        productService.addToUserBucket(id, principal.getName());
        return "redirect:/products";
   }

   @PostMapping
    public ResponseEntity<Void> addProduct(ProductDTO dto) {
        productService.addProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
   }


   @MessageMapping("/products")
    public void messageAddProduct(ProductDTO dto) {
        productService.addProduct(dto);
   }
}
