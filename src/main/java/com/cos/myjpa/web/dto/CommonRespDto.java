package com.cos.myjpa.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonRespDto<T> {

	private int statusCode; //1정상, -1실패
	private String msg; //오류메시지
	private T data;
}
