package com.example.user.carematch;

public class Products extends ProductsId {

    private String Products_name;
    private String Products_image;
    private String Products_storename;
    public Products(){

    }
    public Products(String products_name, String products_image, String products_storename){
        Products_name=products_name;
        Products_image=products_image;
        Products_storename=products_storename;
    }
    public String getProducts_name() {
        return Products_name;
    }

    public void setProducts_name(String products_name) {
        Products_name=products_name;
    }
    public String getProducts_image() {
        return Products_image;
    }

    public void setProducts_image(String products_image) { Products_image=products_image;}
    public String getProducts_storename() {
        return Products_storename;
    }

    public void setProducts_storename(String products_storename) { Products_storename=products_storename;}

}
