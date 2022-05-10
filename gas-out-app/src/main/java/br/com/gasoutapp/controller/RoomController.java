package br.com.gasoutapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.gasoutapp.domain.Room;
import br.com.gasoutapp.dto.RoomDTO;
import br.com.gasoutapp.service.RoomService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("room")
public class RoomController {
	@Autowired
	private RoomService roomService;
	
	@CrossOrigin(origins = "*", maxAge = 7200)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Listar cômodos cadastrados")
	@RequestMapping(value = "/find-all", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	public List<Room> getAllRooms(){
		return roomService.getAllRooms();
	}
	
	@CrossOrigin(origins = "*", maxAge = 7200)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Listar cômodos cadastrados do usuário")
	@RequestMapping(value = "/find-all/{login}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	public List<Room> getAllUserRooms(@PathVariable String login){
		return roomService.getAllUserRooms(login);
	}	
	
	@CrossOrigin(origins = "*", maxAge = 7200)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Criar cômodo para o usuário")
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	public Room createRoom(@RequestBody RoomDTO dto){
		return roomService.createRoom(dto);
	}
	
	@CrossOrigin(origins = "*", maxAge = 7200)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Excluir cômodo por id")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	public void  deleteRoom(@PathVariable Long id){
		roomService.deleteRoom(id);
	}	
}