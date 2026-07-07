package com.BlogManagment.Blog_App.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private String Status;

    private String message;

    private T data;

}
