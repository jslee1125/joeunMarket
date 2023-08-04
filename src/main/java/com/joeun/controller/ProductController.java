package com.joeun.controller;

import com.joeun.dto.ProductCategoryDto;
import com.joeun.dto.ProductDto;
import com.joeun.mapper.PagingMapper;
import com.joeun.mapper.ProductMapper;
import com.joeun.service.PagingService;
import com.joeun.service.ProductService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import com.joeun.dto.ProductCategoryDto;
import com.joeun.dto.ProductDto;
import com.joeun.mapper.ProductMapper;
import com.joeun.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
	private final PagingService pagingService; 
    private final ProductMapper productMapper;
    private final PagingMapper pagingMapper; 
    

    @GetMapping("/addProduct.do")
    public String goToAddProduct(Model model) {
        List<ProductCategoryDto> categoryList = productService.findAllCategory();
        model.addAttribute("categoryList", categoryList);
        return "/admin/addProduct";
    }

    @GetMapping("/addCategory.do")
    public String goToAddCategory() {
        return "/admin/addCategory";
    }

    @GetMapping("/productList.do")
    public String goToProductList(Model model) {
        List<ProductDto> productList = productService.findAllProduct();
        model.addAttribute("products", productList);
        return "/admin/adminProductList";
    }

    @PostMapping("/addCategory")
    public String addCategory(final ProductCategoryDto categoryDto) {
        productService.addCategory(categoryDto);
        return "redirect:/admin.do";
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestParam("imgs") List<MultipartFile> imgs, ProductDto product) {
        System.out.println("상품 등록");
        String originPath = "";
        String[] filePath = new String[4];
        String[] tempArray;
        for (MultipartFile file : imgs) {
            String temp = productService.uploadFile(file);
            originPath += temp + " ";
        }
        tempArray = originPath.split(" ");

        for (int i = 0; i < filePath.length; i++) {
            if (!tempArray[i].isEmpty()) {
                filePath[i] = tempArray[i];
            } else {
                filePath[i] = null;
            }

        }
        product.setImg1(filePath[0]);
        product.setImg2(filePath[1]);
        product.setImg3(filePath[2]);
        product.setImg4(filePath[3]);
        productService.addProduct(product);
        return "/admin/adminMain";
    }


    @GetMapping("/productlist")
	public String showProductList(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String keyword, // 검색어 파라미터 추가
			@RequestParam(required = false) String Sequence, // 순서 검색기능 추가
			Model model) {
		int totalCount; 
		int totalPages;
		List<ProductDto> products;

		// 검색어가 있는 경우와 없는 경우를 구분하여 처리 
		if (keyword != null && !keyword.isEmpty()) {
			totalCount = pagingService.countProductsByKeyword(keyword);
			totalPages = (int) Math.ceil((double) totalCount / size);

			if (page < 1) {
				page = 1;
			} else if (page > totalPages) {
				page = totalPages;
			}

			int offset = (page - 1) * size;
			if (Sequence != null && !Sequence.isEmpty()) {
				products = productService.findProductsByKeywordPrice(offset, size, keyword);
			} else {
				products = productService.findProductsByKeywordPaging(offset, size, keyword);
			}
		} else {
			totalCount = pagingService.countAllProducts();
			totalPages = (int) Math.ceil((double) totalCount / size);

			if (page < 1) {
				page = 1;
			} else if (page > totalPages) {
				page = totalPages;
			}

			int offset = (page - 1) * size;
			if (Sequence != null && !Sequence.isEmpty()) {
				products = productService.findAllProductsPrice(offset, size);
			} else {
				products = productService.findAllProductsPaging(offset, size);
			}
		}

		model.addAttribute("items", products);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("keyword", keyword); // 검색어를 다시 뷰로 전달

		return "productlist";
    }

    @GetMapping("productUpdate.do")
    public String goToProductUpdate(Model model) {
        List<Integer> idList = productService.findAllProductId();
        List<ProductCategoryDto> categoryList = productService.findAllCategory();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("idList", idList);
        return "/admin/productUpdate";
    }

    @GetMapping("/getProductInfo")
    @ResponseBody
    public ProductDto getProductInfo(Model model, int productId) {
//        ProductDto product = productService.findProductById(productId);
        List<ProductCategoryDto> category = productService.findAllCategory();
        model.addAttribute("categoryList", category);
        return productService.findProductById(productId);
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@RequestParam("imgs") List<MultipartFile> imgs, ProductDto product) {
        System.out.println("상품 수정");
        String originPath = "";
        String[] filePath = new String[4];
        String[] tempArray;
        for (MultipartFile file : imgs) {
            String temp = productService.uploadFile(file);
            originPath += temp + " ";
        }
        tempArray = originPath.split(" ");

        for (int i = 0; i < filePath.length; i++) {
            if (!tempArray[i].isEmpty()) {
                filePath[i] = tempArray[i];
            } else {
                filePath[i] = null;
            }

        }
        product.setImg1(filePath[0]);
        product.setImg2(filePath[1]);
        product.setImg3(filePath[2]);
        product.setImg4(filePath[3]);
        productService.updateProduct(product);
        return "/admin/adminMain";
    }

}