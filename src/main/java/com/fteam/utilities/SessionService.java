package com.fteam.utilities;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
	
	@Autowired
	HttpSession session;
	
	/**
	 * Đọc giá trị attribute trong session
	 * @param name tên attribute
	 * @return giá trị đọc được hoặc null nếu không tồn tại
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String name, Object defaultValue) {
		Object value = session.getAttribute(name);
		if (value == null) {
			return (T) defaultValue;
		}
		return (T) value;
	}
	
	/**
	 * Thay đổi hoặc tạo mới attribute trong session
	 * @param name tên attribute
	 * @param value giá trị attribute
	 */
	public void set(String name, Object value) {
		session.setAttribute(name, value);
	}
	
	/**
	 * Xoá attribute trong session
	 * @param name tên attribute cần xoá
	 */
	public void remove(String name) {
		session.removeAttribute(name);
	}
	

}
