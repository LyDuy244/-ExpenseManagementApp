/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.configs;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ADMIN
 */
public class Utility {
    public static String getSiteUrl(HttpServletRequest request){
        String siteUrl = request.getRequestURI().toString();
        return siteUrl.replace(request.getServletPath(), "");
    }
}
