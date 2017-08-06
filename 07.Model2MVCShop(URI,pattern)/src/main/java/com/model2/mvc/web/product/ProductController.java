package com.model2.mvc.web.product;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

import junit.framework.Assert;

//==> 회원관리 Controller
@Controller
public class ProductController {

	/// Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	// setter Method 구현 않음

	public ProductController() {
		System.out.println(this.getClass());
	}

	// ==> classpath:config/common.properties ,
	// classpath:config/commonservice.xml 참조 할것
	// ==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	// @Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;

	@Value("#{commonProperties['pageSize']}")
	// @Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;

	@Test
	@RequestMapping("/addProduct.do")
	public ModelAndView addProduct(@ModelAttribute("product") Product product) throws Exception {

		System.out.println("/addProduct.do");

		product.setManuDate(product.getManuDate().replaceAll("-", ""));

		productService.addProduct(product);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("product", product);
		modelAndView.setViewName("forward:/product/addProduct.jsp");

		return modelAndView;
	}

	@RequestMapping("/getProduct.do")
	public ModelAndView getProduct(@RequestParam("menu") String menu, @RequestParam("prodNo") int prodNo)
			throws Exception {

		System.out.println("/getProduct.do");
		// Business Logic

		Product product = productService.getProduct(prodNo);

		System.out.println("Controller Product Check : " + product);
		System.out.println("menu check : " + menu);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("product", product);
		modelAndView.addObject("menu", menu);
		// modelAndView.setViewName("forward:/product/getProduct.jsp");

		if (menu.equals("manage")) {
			modelAndView.setViewName("forward:/product/updateProductView.jsp");
		} else {
			modelAndView.setViewName("forward:/product/getProduct.jsp");
		}

		return modelAndView;
	}

	@RequestMapping("/updateProductView.do")
	public ModelAndView updateProductView(@RequestParam("prodNo") int prodNo) throws Exception {

		System.out.println("/updateProductView.do");

		Product product = productService.getProduct(prodNo);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/product/updateProductView.jsp");
		modelAndView.addObject("product", product);

		return modelAndView;
	}

	@RequestMapping("/updateProduct.do")
	public ModelAndView updateProduct(@ModelAttribute("product") Product product, @RequestParam("menu") String menu)
			throws Exception {

		System.out.println("/updateProduct.do");

		System.out.println("update menu Check" + menu);

		product.setManuDate(product.getManuDate().replaceAll("-", ""));

		productService.updateProduct(product);
		System.out.println("updateProduct : " + product);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/product/updateProduct.jsp");
		modelAndView.addObject("product", product);
		modelAndView.addObject("menu", menu);

		return modelAndView;
	}

	@RequestMapping("/listProduct.do")
	public ModelAndView getProductList(@ModelAttribute("search") Search search, @ModelAttribute("page") Page page)
			throws Exception {

		System.out.println("/listProduct.do");

		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);

		Map<String, Object> map = productService.getProductList(search);

		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit,
				pageSize);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("search", search);
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);

		modelAndView.setViewName("forward:/product/listProduct.jsp");

		return modelAndView;

	}

}