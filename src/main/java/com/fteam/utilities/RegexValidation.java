package com.fteam.utilities;

public class RegexValidation {
	
	// alphabet including Unicode UTF-8
	public static final String NAME_REGEX = "^[A-Za-zẮẰẲẴẶĂẤẦẨẪẬÂÁÀÃẢẠĐẾỀỂỄỆÊÉÈẺẼẸÍÌỈĨỊỐỒỔỖỘÔỚỜỞỠỢƠÓÒÕỎỌỨỪỬỮỰƯÚÙỦŨỤÝỲỶỸỴ"
			+ "ắằẳẵặăấầẩẫậâáàãảạđếềểễệêéèẻẽẹíìỉĩịốồổỗộôớờởỡợơóòõỏọứừửữựưúùủũụýỳỷỹỵ]+"
			+ "(\\s[A-Za-zẮẰẲẴẶĂẤẦẨẪẬÂÁÀÃẢẠĐẾỀỂỄỆÊÉÈẺẼẸÍÌỈĨỊỐỒỔỖỘÔỚỜỞỠỢƠÓÒÕỎỌỨỪỬỮỰƯÚÙỦŨỤÝỲỶỸỴ"
			+ "ắằẳẵặăấầẩẫậâáàãảạđếềểễệêéèẻẽẹíìỉĩịốồổỗộôớờởỡợơóòõỏọứừửữựưúùủũụýỳỷỹỵ]+)+$";

	// Vietnam phone number, only contain 10 -> 12 numberic characters
	public static final String PHONE_REGEX = "^\\d{10,12}$";

	// local-part + @ + domain part
	public static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*" + "@"
			+ "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

	// 5->18 characters, can contains alphanumeric characters not case sensitive
	// allows '.', '_'
	public static final String USERNAME_REGEX = "^[a-zA-Z0-9]([._](?![._])|[a-zA-Z0-9]){5,18}[a-zA-Z0-9]$";

	// 1. Password must contain at least one digit [0-9].
	// 2. Password must contain at least one lowercase Latin character [a-z].
	// 3. Password must contain at least one uppercase Latin character [A-Z].
	// 4. Password must contain at least one special character like ! @ # & ( ).
	// 5. Password must contain a length of at least 8 characters and a maximum of
	// 36 characters.
	public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,64}$";

}
