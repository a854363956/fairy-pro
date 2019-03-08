package com.fairy.controllers.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fairy.models.common.Session;
import com.fairy.models.dto.RequestDto;
import com.fairy.models.dto.ResponseDto;
import com.fairy.models.dto.jpa.FairyBaseRole;
import com.fairy.models.exception.FairyException;
import com.fairy.models.logic.MenuModel;
import com.fairy.models.logic.MenuModel.Menu;

@RestController
@RequestMapping(value ="/api/menu")
public class MenuController {
	@Autowired private MenuModel menuModel;
	@Autowired private Session session;
	
	@PostMapping("/getAccessibleMenuAll")
	public ResponseDto<List<Menu>> getAccessibleMenuAll(@RequestBody RequestDto<JSONObject> request) throws FairyException{
		Optional<FairyBaseRole> opt = session.getCurrentRole(request);
		if(opt.isPresent()) {
			return ResponseDto.getSuccess(menuModel.findAccessibleMenuAll(0L, opt.get().getId())) ;
		}else {
			throw new FairyException("Current personnel do not exist");
		}
	}
}
