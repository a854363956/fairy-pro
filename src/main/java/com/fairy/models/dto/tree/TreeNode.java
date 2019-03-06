package com.fairy.models.dto.tree;

import java.util.List;

import lombok.Data;

public @Data class TreeNode {
	private String title;
	private Long key;
	private List<TreeNode> childrens;
	
}
