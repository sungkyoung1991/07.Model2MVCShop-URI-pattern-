package com.model2.mvc.web.product;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

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

	//@Test
	/*@RequestMapping("/addProduct")
	public ModelAndView addProduct(@ModelAttribute("product") Product product) throws Exception {

		System.out.println("/addProduct");

		product.setManuDate(product.getManuDate().replaceAll("-", ""));

		productService.addProduct(product);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("product", product);
		modelAndView.setViewName("forward:/product/addProduct.jsp");

		return modelAndView;
	}*/
	
	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	public ModelAndView addProduct(@ModelAttribute("product") Product product, HttpServletRequest request)
			throws Exception {


		System.out.println("/addProduct");

		ModelAndView modelAndView = new ModelAndView();

		if (FileUpload.isMultipartContent(request)) {
			String temDir = "/Users/sungkyoung-kim/git/07.Model2MVCShop(URI,pattern)/"
					+ "07.Model2MVCShop(URI,pattern)/WebContent/images/uploadFiles";

			DiskFileUpload fileUpload = new DiskFileUpload();
			fileUpload.setRepositoryPath(temDir);
			fileUpload.setSizeMax(1024 * 1024 * 10);

			fileUpload.setSizeThreshold(1024 * 100);

			if (request.getContentLength() < fileUpload.getSizeMax()) {
				StringTokenizer token = null;
				List fileItemList = fileUpload.parseRequest(request);
				int Size = fileItemList.size();
				for (int i = 0; i < Size; i++) {
					FileItem fileItem = (FileItem) fileItemList.get(i);
					if (fileItem.isFormField()) {
						if (fileItem.getFieldName().equals("manuDate")) {
							token = new StringTokenizer(fileItem.getString("euc-kr"), "-");
							String manuDate = token.nextToken() + token.nextToken() + token.nextToken();
							product.setManuDate(manuDate);
						} else if (fileItem.getFieldName().equals("prodName"))
							product.setProdName(fileItem.getString("euc-kr"));
						else if (fileItem.getFieldName().equals("prodDetail"))
							product.setProdDetail(fileItem.getString("euc-kr"));
						else if (fileItem.getFieldName().equals("price"))
							product.setPrice(Integer.parseInt(fileItem.getString("euc-kr")));

					} else {
						if (fileItem.getSize() > 0) {
							int idx = fileItem.getName().lastIndexOf("\\");
							if (idx == -1) {
								idx = fileItem.getName().lastIndexOf("/");

							}
							String fileName = fileItem.getName().substring(idx + 1);
							product.setFileName(fileName);
							try {
								File UploadedFile = new File(temDir, fileName);
								fileItem.write(UploadedFile);
							} catch (IOException e) {
								System.out.println(e);
							}
						}
					}
				}
				productService.addProduct(product);
				modelAndView.addObject("product", product);
				modelAndView.setViewName("forward:/product/addProduct.jsp");
			} else {
				int overSize = (request.getContentLength() / 1000000);
				System.out.println("<script>alert('파일의 크기는 1MB 까지입니다. 올리신 파일용량은 " + overSize + "MB입니다');");

			}
		} else {
			System.out.println("인코딩 타입이 multipart/form-data가 아닙니다.");
		}
		modelAndView.setViewName("forward:/product/addProduct.jsp");

		return modelAndView;
	}

	
	

	@RequestMapping("/getProduct")
	public ModelAndView getProduct(@RequestParam("menu") String menu, @RequestParam("prodNo") int prodNo)
			throws Exception {

		System.out.println("/getProduct");
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

	@RequestMapping("/updateProductView")
	public ModelAndView updateProductView(@RequestParam("prodNo") int prodNo) throws Exception {

		System.out.println("/updateProductView");

		Product product = productService.getProduct(prodNo);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/product/updateProductView.jsp");
		modelAndView.addObject("product", product);

		return modelAndView;
	}

	@RequestMapping("/updateProduct")
	public ModelAndView updateProduct(@ModelAttribute("product") Product product, @RequestParam(value = "menu", required = false ) String menu)
			throws Exception {

		System.out.println("/updateProduct");

		System.out.println("update menu Check" + menu);
		
		System.out.println("product......" + product);

		product.setManuDate(product.getManuDate().replaceAll("-", ""));

		productService.updateProduct(product);
		System.out.println("updateProduct : " + product);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/product/updateProduct.jsp");
		modelAndView.addObject("product", product);
		modelAndView.addObject("menu", menu);

		return modelAndView;
	}

	@RequestMapping("/listProduct")
	public ModelAndView getProductList(@ModelAttribute("search") Search search, @ModelAttribute("page") Page page)
			throws Exception {

		System.out.println("/listProduct");

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