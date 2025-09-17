package com.ecommerce.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("wishlist")
public class WishlistItem {
  @Id
  private String id;
  private String productId;

  public WishlistItem(){}
  public WishlistItem(String productId){ this.productId = productId; }
  // getters & setters
  public String getId(){return id;}
  public void setId(String id){this.id=id;}
  public String getProductId(){return productId;}
  public void setProductId(String productId){this.productId=productId;}
}
