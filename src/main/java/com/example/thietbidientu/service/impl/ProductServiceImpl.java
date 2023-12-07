package com.example.thietbidientu.service.impl;

import com.example.thietbidientu.dto.ProductDto;
import com.example.thietbidientu.dto.ProductResponse;
import com.example.thietbidientu.entity.ImageData;
import com.example.thietbidientu.entity.Product;
import com.example.thietbidientu.exception.ResourceNotFoundException;
import com.example.thietbidientu.repository.ProductRepository;
import com.example.thietbidientu.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProductDto createProduct(ProductDto productdto) {
        Product pr = convertProduct(productdto);
        Product newPr = productRepository.save(pr);
        return convertProductDto(newPr);
    }

    @Override
    public ProductResponse getAllProduct(int pageNo, int pageSize, String sortBy, String sortDir) {
        // câu lệnh này sẽ xem xét sortDir là asc hay des
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);  // set up số trang, số lượng trong một trang và sắp xếp theo miền nào

        Page<Product> productList =  productRepository.findAll(pageable); // findAll với số trang và số lượng trang sẽ trong về Page

        List<Product> listOfProduct = productList.getContent(); // lấy danh sách trang đã được phân trong postList

        List<ProductDto> content = listOfProduct.stream().map(this::convertProductDto).toList();
        ProductResponse productResponse = new  ProductResponse();
        productResponse.setContent(content);
        productResponse.setPageNo(pageNo);
        productResponse.setPageSize(pageSize);
        productResponse.setTotalElement(productList.getTotalElements());
        productResponse.setTotalPage(productList.getTotalPages());
        productResponse.setLast(productList.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse getAllProductByCategory(int pageNo, int pageSize, String sortBy, String sortDir, String category) {
        // câu lệnh này sẽ xem xét sortDir là asc hay des
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);  // set up số trang, số lượng trong một trang và sắp xếp theo miền nào

        Page<Product> productList =  productRepository.findAllByCategory(pageable, category); // findAll với số trang và số lượng trang sẽ trong về Page

        List<Product> listOfProduct = productList.getContent(); // lấy danh sách trang đã được phân trong postList

        List<ProductDto> content = listOfProduct.stream().map(this::convertProductDto).toList();
        ProductResponse productResponse = new  ProductResponse();
        productResponse.setContent(content);
        productResponse.setPageNo(pageNo);
        productResponse.setPageSize(pageSize);
        productResponse.setTotalElement(productList.getTotalElements());
        productResponse.setTotalPage(productList.getTotalPages());
        productResponse.setLast(productList.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse getAllProductByBrand(int pageNo, int pageSize, String sortBy, String sortDir, String brand) {
        // câu lệnh này sẽ xem xét sortDir là asc hay des
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);  // set up số trang, số lượng trong một trang và sắp xếp theo miền nào

        Page<Product> productList =  productRepository.findAllByBrand(pageable, brand); // findAll với số trang và số lượng trang sẽ trong về Page

        List<Product> listOfProduct = productList.getContent(); // lấy danh sách trang đã được phân trong postList

        List<ProductDto> content = listOfProduct.stream().map(this::convertProductDto).toList();
        ProductResponse productResponse = new  ProductResponse();
        productResponse.setContent(content);
        productResponse.setPageNo(pageNo);
        productResponse.setPageSize(pageSize);
        productResponse.setTotalElement(productList.getTotalElements());
        productResponse.setTotalPage(productList.getTotalPages());
        productResponse.setLast(productList.isLast());
        return productResponse;
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product =  productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product","id",String.valueOf(id)));
        return convertProductDto(product);
    }

    @Override
    public ProductResponse searchProduct(int pageNo, int pageSize, String sortBy, String sortDir, String name) {
        // câu lệnh này sẽ xem xét sortDir là asc hay des
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);  // set up số trang, số lượng trong một trang và sắp xếp theo miền nào

        Page<Product> productList =  productRepository.findByNameContaining(pageable, name); // findAll với số trang và số lượng trang sẽ trong về Page

        List<Product> listOfProduct = productList.getContent(); // lấy danh sách trang đã được phân trong postList

        List<ProductDto> content = listOfProduct.stream().map(this::convertProductDto).toList();
        ProductResponse productResponse = new  ProductResponse();
        productResponse.setContent(content);
        productResponse.setPageNo(pageNo);
        productResponse.setPageSize(pageSize);
        productResponse.setTotalElement(productList.getTotalElements());
        productResponse.setTotalPage(productList.getTotalPages());
        productResponse.setLast(productList.isLast());
        return productResponse;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product","id",String.valueOf(id)));
        product.setName(productDto.getName());
        product.setCategory(productDto.getCategory());
        product.setBrand(productDto.getBrand());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCost(productDto.getCost());
        product.setQuantity(productDto.getQuantity());
        product.setStatus(productDto.isStatus());
        Product newPro = productRepository.save(product);
        return convertProductDto(newPro);

    }

    @Override
    public void deleteProduct(Long id) {
        Product post =  productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id",String.valueOf(id)));
        productRepository.delete(post);
    }

    private Product convertProduct(ProductDto productDto){
        return modelMapper.map(productDto,Product.class);
    }
    private ProductDto convertProductDto(Product product){
        return modelMapper.map(product,ProductDto.class);
    }
}
