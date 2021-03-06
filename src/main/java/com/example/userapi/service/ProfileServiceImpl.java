package com.example.userapi.service;

import com.example.userapi.model.Profile;
import com.example.userapi.model.User;
import com.example.userapi.repository.ProfileRepository;
import com.example.userapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProfileServiceImpl implements ProfileService {

    private static Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class.getName());

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Override
    public Profile createProfile(String username, Profile newProfile) {
        String email = userRepository.findByUsername(username).getEmail();
        User user = userRepository.findByEmail(email);
        if(user.getProfile() == null) {
            user.setProfile(newProfile);
            profileRepository.save(newProfile);
        }
        logger.info("Post created: " + newProfile);
        return userRepository.findByUsername(username).getProfile();
    }


    @Override
    public Profile getProfile(String username) {
        return profileRepository.findProfileByUsername(username);
    }

    @Override
    public Profile updateProfile(String username, Profile updateProfile) {
        User user = userRepository.findByUsername(username);
        Profile profile = userRepository.findByUsername(username).getProfile();

        if(updateProfile.getAddress() != null) profile.setAddress(updateProfile.getAddress());
        if(updateProfile.getMobile() != null) profile.setMobile(updateProfile.getMobile());
        if(updateProfile.getEmail() != null) profile.setEmail(updateProfile.getEmail());

        profileRepository.save(profile);
        user.setProfile(profile);
        logger.info("Post updated: " + user.getProfile());
        return user.getProfile();
    }

}