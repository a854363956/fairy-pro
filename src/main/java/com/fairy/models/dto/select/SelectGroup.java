package com.fairy.models.dto.select;

import java.util.List;

import lombok.Data;

public @Data class SelectGroup {
	private List<Select> groupList;
	private String groupName;
}