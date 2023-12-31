package com.shravani.TimeSheetApp.service;
import com.shravani.TimeSheetApp.dto.UserDto;
import com.shravani.TimeSheetApp.dto.UserMapper;
import com.shravani.TimeSheetApp.entity.Role;
import com.shravani.TimeSheetApp.entity.User;
import com.shravani.TimeSheetApp.repository.RoleRepository;
import com.shravani.TimeSheetApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleRepository roleRepository;
    @Override
    public List<UserDto> findAllUsers() {

        List<User> users=userRepository.findAll();

        return users.stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDto findUserByEmail(String email) {
        User user=userRepository.findByEmail(email);
        if(user==null)
            return null;
        return userMapper.toDto(user);
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        User user=userMapper.toEntity(userDto);
        List<Role> roles=new ArrayList<>();
        for(String roleName:userDto.getRoles())
        {
            Role role=roleRepository.findByName(roleName);
            if(role==null){
                role= Role.builder().name(roleName).build();
                roleRepository.save(role);
            }
            roles.add(role);
        }
        user.setRoles(roles);
        User savedUser=userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto findUserByName(String userName) {
        User user= userRepository.findByEmail(userName);
        if(user!=null)
            return userMapper.toDto(user);
        else
            return null;

    }

}
