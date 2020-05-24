package per.cz.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2020/5/23.
 */
@RestController
public class LoginController {



	@RequestMapping("/login-success")
	public String loginSuccess(){
		return "success~";
	}



	@GetMapping("r/r1")
	@PreAuthorize("hasAuthority('sys:user:add')")
	public String getPermission1(HttpSession session){
		return  " requesting1~";
	}


	@GetMapping("r/r2")
	public String getPermission2(HttpSession session){
		return  " requesting2~";
	}


}
