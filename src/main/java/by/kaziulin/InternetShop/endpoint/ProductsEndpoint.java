package by.kaziulin.InternetShop.endpoint;

import by.kaziulin.InternetShop.dto.ProductDTO;
import by.kaziulin.InternetShop.service.ProductService;
import by.kaziulin.InternetShop.ws.products.GetProductRequest;
import by.kaziulin.InternetShop.ws.products.GetProductResponse;
import by.kaziulin.InternetShop.ws.products.ProductsWS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ProductsEndpoint {

    public static final String NAMESPACE_URL = "http://kaziulin.by/InternetShop/ws/products";
    private ProductService productService;

    @Autowired
    public ProductsEndpoint(ProductService productService) {
        this.productService = productService;
    }


    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getProductsRequest")
    @ResponsePayload
    public GetProductResponse getProductsWS(@RequestPayload GetProductRequest request) {
        GetProductResponse response = new GetProductResponse();
        productService.getAll()
                .forEach(dto -> response.getProducts().add(createProductWS(dto)));
        return response;
    }

    private ProductsWS createProductWS(ProductDTO dto) {
        ProductsWS ws = new ProductsWS();
        ws.setId(dto.getId());
        ws.setPrice(Double.parseDouble(dto.getPrice().toString()));
        ws.setTitle(dto.getTitle());
        return ws;
    }
}

