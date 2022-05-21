package com.fteam.utilities;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CookieService {
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpServletResponse response;
	
	/**
	 * Đọc cookie từ request
	 * @param name tên cookie cần đọc
	 * @return đối tượng cookie đọc được hoặc null nếu không tồn tại
	 */
	public Cookie get(String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie: cookies) {
				if (cookie.getName().equalsIgnoreCase(name)) {
					return cookie;
				}
			}
		}
		return null;
	}
	
	/**
	 * Đọc giá trị cookie từ request
	 * @param name tên cookie cần đọc
	 * @return chuỗi giá trị đọc được hoặc rỗng nếu không tồn tại
	 */
	public String getValue(String name) {
		if (this.get(name) == null) {
			return "";
		}
		return this.get(name).getValue();
	}
	
	/**
	 * Tạo và gửi cookie về client
	 * @param name tên cookie
	 * @param value giá trị cookie
	 * @param days thời hạn (ngày)
	 * @return đối tượng cookie đã tạo
	 */
	public Cookie add(String name, String value, int days) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(days * 60 * 60 * 24);
		cookie.setPath("/");
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		
		response.addCookie(cookie);
		return cookie;
	}
	
	/**
	 * Xoá cookie khỏi client
	 * @param name tên cookie cần xoá
	 */
	public void remove(String name) {
		Cookie cookie = this.get(name);
		if (cookie == null) {
			return;
		}
		
		cookie.setMaxAge(0);;
	}

}
