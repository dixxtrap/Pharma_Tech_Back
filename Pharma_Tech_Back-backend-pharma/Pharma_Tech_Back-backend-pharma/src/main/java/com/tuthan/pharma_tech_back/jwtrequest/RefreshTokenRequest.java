package com.tuthan.pharma_tech_back.jwtrequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RefreshTokenRequest {
  @NotBlank
  private String refreshToken;

}
