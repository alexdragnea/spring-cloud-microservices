package net.dg.ratingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.dg.ratingservice.entity.Rating;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTemplate {

	private Book book;

	private List<Rating> ratingList;

}