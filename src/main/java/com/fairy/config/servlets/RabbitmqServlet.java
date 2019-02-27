package com.fairy.config.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;

import com.alibaba.fastjson.JSON;
import com.fairy.FairyProApplication;
import com.fairy.models.common.StreamUtil;
import com.fairy.models.dto.ResponseDto;
import com.google.common.base.Charsets;

@WebServlet(urlPatterns="/models/*")
public class RabbitmqServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	protected Object sendAndReceive(String type,HttpServletRequest req,HttpServletResponse resp) throws AmqpException, IOException {
		AmqpTemplate amqp = FairyProApplication.getSpringContext().getBean(AmqpTemplate.class);
		String requestUrl = req.getRequestURI();
		Object msg= amqp.convertSendAndReceive(
				type+requestUrl,
				StreamUtil.readBytes(req.getInputStream()));		
		if(msg == null) {
			throw new RuntimeException("Message queue call failed !");
		}
	  	return msg;
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Object body = sendAndReceive("get",req,resp);
			resp.getOutputStream().write(JSON.toJSONString(ResponseDto.getSuccess(body)).getBytes(Charsets.UTF_8));
		} catch (Exception e) {
			e.printStackTrace();
			ResponseDto<?> respDto = new ResponseDto<>();
			respDto.setMessage(e.getMessage());
			respDto.setStatus(520);
			resp.getOutputStream().write(JSON.toJSONString(respDto).getBytes(Charsets.UTF_8));
		}
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Object body = sendAndReceive("head",req,resp);
			resp.getOutputStream().write(JSON.toJSONString(ResponseDto.getSuccess(body)).getBytes(Charsets.UTF_8));
		} catch (Exception e) {
			e.printStackTrace();
			ResponseDto<?> respDto = new ResponseDto<>();
			respDto.setMessage(e.getMessage());
			respDto.setStatus(520);
			resp.getOutputStream().write(JSON.toJSONString(respDto).getBytes(Charsets.UTF_8));
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Object body = sendAndReceive("post",req,resp);
			resp.getOutputStream().write(JSON.toJSONString(ResponseDto.getSuccess(body)).getBytes(Charsets.UTF_8));
		} catch (Exception e) {
			e.printStackTrace();
			ResponseDto<?> respDto = new ResponseDto<>();
			respDto.setMessage(e.getMessage());
			respDto.setStatus(520);
			resp.getOutputStream().write(JSON.toJSONString(respDto).getBytes(Charsets.UTF_8));
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Object body = sendAndReceive("put",req,resp);
			resp.getOutputStream().write(JSON.toJSONString(ResponseDto.getSuccess(body)).getBytes(Charsets.UTF_8));
		} catch (Exception e) {
			e.printStackTrace();
			ResponseDto<?> respDto = new ResponseDto<>();
			respDto.setMessage(e.getMessage());
			respDto.setStatus(520);
			resp.getOutputStream().write(JSON.toJSONString(respDto).getBytes(Charsets.UTF_8));
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Object body = sendAndReceive("delete",req,resp);
			resp.getOutputStream().write(JSON.toJSONString(ResponseDto.getSuccess(body)).getBytes(Charsets.UTF_8));
		} catch (Exception e) {
			e.printStackTrace();
			ResponseDto<?> respDto = new ResponseDto<>();
			respDto.setMessage(e.getMessage());
			respDto.setStatus(520);
			resp.getOutputStream().write(JSON.toJSONString(respDto).getBytes(Charsets.UTF_8));
		}
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Object body = sendAndReceive("options",req,resp);
			resp.getOutputStream().write(JSON.toJSONString(ResponseDto.getSuccess(body)).getBytes(Charsets.UTF_8));
		} catch (Exception e) {
			e.printStackTrace();
			ResponseDto<?> respDto = new ResponseDto<>();
			respDto.setMessage(e.getMessage());
			respDto.setStatus(520);
			resp.getOutputStream().write(JSON.toJSONString(respDto).getBytes(Charsets.UTF_8));
		}
	}

	@Override
	protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Object body = sendAndReceive("trace",req,resp);
			resp.getOutputStream().write(JSON.toJSONString(ResponseDto.getSuccess(body)).getBytes(Charsets.UTF_8));
		} catch (Exception e) {
			e.printStackTrace();
			ResponseDto<?> respDto = new ResponseDto<>();
			respDto.setMessage(e.getMessage());
			respDto.setStatus(520);
			resp.getOutputStream().write(JSON.toJSONString(respDto).getBytes(Charsets.UTF_8));
		}
	}

}
