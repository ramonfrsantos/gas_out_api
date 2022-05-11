package br.com.gasoutapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gasoutapp.domain.Room;
import br.com.gasoutapp.domain.User;
import br.com.gasoutapp.dto.RoomDTO;
import br.com.gasoutapp.exception.RoomAlreadyExistsException;
import br.com.gasoutapp.exception.RoomNotFoundException;
import br.com.gasoutapp.exception.UserNotFoundException;
import br.com.gasoutapp.repository.RoomRepository;
import br.com.gasoutapp.repository.UserRepository;

@Service
public class RoomService {

	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<Room> getAllRooms() {
		return roomRepository.findAll();
	}
	
	public List<Room> getAllUserRooms(String login) {
		User user = userRepository.findByLogin(login);
		return roomRepository.findAllByUser(user);
	}

	public Room createRoom(RoomDTO dto) {	
		List<Room> newUserRooms = new ArrayList<>();
		User newUser = new User();
		
		User user = userRepository.findByLogin(dto.getUser().getEmail());
		if(user == null) {
			throw new UserNotFoundException();
		} 
		newUser = user;
				
		List<Room> rooms = roomRepository.findAllByUser(user);
		for (Room room: rooms){
			if(room.getName().equals(dto.getName())) {
				throw new RoomAlreadyExistsException();				
			}
		}
		newUserRooms = rooms;
		
		Room newRoom = new Room();
		
		newRoom.setUser(user);
		newRoom.setName(dto.getName());
		newRoom.setAlarmOn(false);
		newRoom.setSprinklersOn(false);
		newRoom.setNumberOfSensors(dto.getNumberOfSensors());
		
		newRoom = roomRepository.save(newRoom);
		newUserRooms.add(newRoom);
		
		newUser.setRooms(newUserRooms);
		userRepository.save(newUser);
				
		return newRoom;
	}

	public void deleteRoom(Long id, String login) {
		Room checkedRoom = roomRepository.getById(id);
		if(checkedRoom == null) {
			throw new RoomNotFoundException();
		}
		User user = userRepository.findByLogin(login);
		if(user != null) {
			User newUser = new User();
			newUser = user;
			List<Room> roomsList = roomRepository.findAllByUser(user);
			for(Room room: roomsList) {
				if(room.getId() == id) {
					room.setUser(null);
					roomRepository.save(room);
					
					newUser.setRooms(roomRepository.findAllByUser(user));
					userRepository.save(newUser);
					roomRepository.delete(room);	
				}
			}
		}
	}
}