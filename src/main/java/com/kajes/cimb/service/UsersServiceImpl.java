package com.kajes.cimb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajes.cimb.dto.AccountDetails;
import com.kajes.cimb.dto.MessageDetail;
import com.kajes.cimb.dto.UserModel;
import com.kajes.cimb.dto.UserRequest;
import com.kajes.cimb.dto.UserResponse;
import com.kajes.cimb.exception.UserNotFoundException;
import com.kajes.cimb.model.UserAccounts;
import com.kajes.cimb.model.Users;
import com.kajes.cimb.repository.UserAccountsRepository;
import com.kajes.cimb.repository.UsersRepository;
import com.kajes.cimb.util.BaseConstants;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	UsersRepository usersRepo;
	
	@Autowired
	UserAccountsRepository userAccountsRepo;

	@Override
	public UserResponse createUser(UserRequest userRequest) {

		UserResponse response = new UserResponse();

		UserModel userReq = userRequest.getUserInfo();

		Users user = new Users();

		user.setFirstName(userReq.getFirstName());
		user.setLastName(userReq.getLastName());
		user.setEmail(userReq.getEmail());
		user.setPhoneNumber(userReq.getPhoneNumber());
		user.setAddressLine1(userReq.getAddressLine1());
		user.setAddressLine2(userReq.getAddressLine2());
		user.setIsActive("Y");
		user.setCreatedBy("User_Kaj");
		user.setCreatedOn(new Date());

		Set<UserAccounts> userAccounts = new HashSet<>();

		for (AccountDetails accountDetail : userReq.getAccountDetails()) {

			UserAccounts userAccount = new UserAccounts();
			userAccount.setAccountNumber(accountDetail.getAccountNumber());
			userAccount.setUser(user);
			userAccount.setAccountType(accountDetail.getAccountType());
			userAccount.setBankName(accountDetail.getBankName());
			userAccount.setBranchName(accountDetail.getBranchName());
			userAccount.setBalance(accountDetail.getBalance());
			userAccount.setAccountStatus("A");
			userAccount.setCreatedBy("User_Kaj");
			userAccount.setCreatedOn(new Date());
			userAccounts.add(userAccount);
		}

		user.setUserAccounts(userAccounts);
		Users insertedUser = usersRepo.save(user);

		MessageDetail messageDetail = new MessageDetail();

		if (insertedUser != null) {

			UserModel userModel = new UserModel();
			userModel.setUserId(insertedUser.getUserId());
			response.setUserInfo(userModel);
			messageDetail.setStatusCode(BaseConstants.SUCCESS_CODE);
			messageDetail.setStatus(BaseConstants.SUCCESS);
			messageDetail.setStatusMessage("User Created Successfully");

		} else {
			messageDetail.setStatusCode(BaseConstants.FAILURE_CODE);
			messageDetail.setStatus(BaseConstants.FAILURE);
			messageDetail.setStatusMessage("Internal Error !! Please try again afetr some time.");
		}

		response.setMessage(messageDetail);
		return response;
	}

	@Override
	public UserResponse deleteUser(Long userId) throws UserNotFoundException {
		UserResponse response = new UserResponse();
		Users user = usersRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("Employee not found for this id :: " + userId));

		usersRepo.delete(user);
		MessageDetail messageDetail = new MessageDetail();

		messageDetail.setStatusCode(BaseConstants.SUCCESS_CODE);
		messageDetail.setStatus(BaseConstants.SUCCESS);
		messageDetail.setStatusMessage("User Deleted Successfully");
		response.setMessage(messageDetail);
		return response;
	}

	@Override
	public UserResponse getUserById(Long userId) throws UserNotFoundException {
		Users user = usersRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found for the id :: " + userId));

		UserResponse response = new UserResponse();

		UserModel userResp = new UserModel();
		userResp.setUserId(user.getUserId());
		userResp.setFirstName(user.getFirstName());
		userResp.setLastName(user.getLastName());
		userResp.setEmail(user.getEmail());
		userResp.setPhoneNumber(user.getPhoneNumber());
		userResp.setAddressLine1(user.getAddressLine1());
		userResp.setAddressLine2(user.getAddressLine2());
		userResp.setIsActive(user.getIsActive());
		userResp.setCreatedBy(user.getCreatedBy());
		userResp.setCreatedOn(user.getCreatedOn());

		Set<AccountDetails> userAccounts = new HashSet<>();

		for (UserAccounts accountDetail : user.getUserAccounts()) {

			AccountDetails userAccount = new AccountDetails();
			userAccount.setAccountId(accountDetail.getAccountId());
			userAccount.setAccountNumber(accountDetail.getAccountNumber());
			userAccount.setAccountType(accountDetail.getAccountType());
			userAccount.setBankName(accountDetail.getBankName());
			userAccount.setBranchName(accountDetail.getBranchName());
			userAccount.setBalance(accountDetail.getBalance());
			userAccount.setAccountStatus("A");
			userAccounts.add(userAccount);
		}

		userResp.setAccountDetails(userAccounts);

		MessageDetail messageDetail = new MessageDetail();
		messageDetail.setStatusCode(BaseConstants.SUCCESS_CODE);
		messageDetail.setStatus(BaseConstants.SUCCESS);
		messageDetail.setStatusMessage("User details returned Successfully");

		response.setUserInfo(userResp);
		response.setMessage(messageDetail);

		return response;
	}

	@Override
	public UserResponse getAllUsers() {

		UserResponse response = new UserResponse();
		MessageDetail messageDetail = new MessageDetail();

		Iterable<Users> users = usersRepo.findAll();

		List<UserModel> allUsers = new ArrayList<>();
		
		for (Users user : users) {

			UserModel userResp = new UserModel();
			userResp.setUserId(user.getUserId());
			userResp.setFirstName(user.getFirstName());
			userResp.setLastName(user.getLastName());
			userResp.setEmail(user.getEmail());
			userResp.setPhoneNumber(user.getPhoneNumber());
			userResp.setAddressLine1(user.getAddressLine1());
			userResp.setAddressLine2(user.getAddressLine2());
			userResp.setIsActive(user.getIsActive());
			userResp.setCreatedBy(user.getCreatedBy());
			userResp.setCreatedOn(user.getCreatedOn());

			Set<AccountDetails> userAccounts = new HashSet<>();

			for (UserAccounts accountDetail : user.getUserAccounts()) {

				AccountDetails userAccount = new AccountDetails();
				userAccount.setAccountId(accountDetail.getAccountId());
				userAccount.setAccountNumber(accountDetail.getAccountNumber());
				userAccount.setAccountType(accountDetail.getAccountType());
				userAccount.setBankName(accountDetail.getBankName());
				userAccount.setBranchName(accountDetail.getBranchName());
				userAccount.setBalance(accountDetail.getBalance());
				userAccount.setAccountStatus("A");
				userAccounts.add(userAccount);
			}

			userResp.setAccountDetails(userAccounts);

			allUsers.add(userResp);

		}
		
		messageDetail.setStatusCode(BaseConstants.SUCCESS_CODE);
		messageDetail.setStatus(BaseConstants.SUCCESS);
		messageDetail.setStatusMessage("All Users details returned Successfully");

		response.setAllUsersInfo(allUsers);
		response.setMessage(messageDetail);

		return response;
	}

	@Override
	public UserResponse updateUser(Long userId, UserRequest userRequest) throws UserNotFoundException {
		Users user = usersRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found for the id :: " + userId));

		UserModel userReq = userRequest.getUserInfo();
		UserResponse response = new UserResponse();

		user.setFirstName(userReq.getFirstName());
		user.setLastName(userReq.getLastName());
		user.setEmail(userReq.getEmail());
		user.setPhoneNumber(userReq.getPhoneNumber());
		user.setAddressLine1(userReq.getAddressLine1());
		user.setAddressLine2(userReq.getAddressLine2());
		user.setIsActive(userReq.getIsActive());
		user.setModifiedBy("User_Kaj");
		user.setModifiedOn(new Date());
		
		Set<UserAccounts> userAccounts = new HashSet<>();

		for (AccountDetails accountDetail : userReq.getAccountDetails()) {
			
			UserAccounts userAccount = new UserAccounts();
			
			if(accountDetail.getAccountNumber() != null) {
				 userAccount = userAccountsRepo.findByAccountNumber(accountDetail.getAccountNumber()).orElse(new UserAccounts());
			}
			
			userAccount.setAccountNumber(accountDetail.getAccountNumber());
			userAccount.setUser(user);
			userAccount.setAccountType(accountDetail.getAccountType());
			userAccount.setBankName(accountDetail.getBankName());
			userAccount.setBranchName(accountDetail.getBranchName());
			userAccount.setBalance(accountDetail.getBalance());
			userAccount.setAccountStatus("A");
			userAccount.setModifiedBy("User_Kaj");
			userAccount.setModifiedOn(new Date());
			userAccounts.add(userAccount);
		}

		user.setUserAccounts(userAccounts);

		final Users updatedUser = usersRepo.save(user);

		MessageDetail messageDetail = new MessageDetail();

		if (updatedUser != null) {
			UserModel userModel = new UserModel();
			userModel.setUserId(updatedUser.getUserId());
			response.setUserInfo(userModel);
			messageDetail.setStatusCode(BaseConstants.SUCCESS_CODE);
			messageDetail.setStatus(BaseConstants.SUCCESS);
			messageDetail.setStatusMessage("User Updated Successfully");

		} else {
			messageDetail.setStatusCode(BaseConstants.FAILURE_CODE);
			messageDetail.setStatus(BaseConstants.FAILURE);
			messageDetail.setStatusMessage("Internal Error !! Please try again afetr some time.");
		}

		response.setMessage(messageDetail);

		return response;

	}

	@Override
	public UserResponse patchUser(Long userId, UserRequest userRequest) throws UserNotFoundException {
		Users user = usersRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found for the id :: " + userId));

		UserModel userReq = userRequest.getUserInfo();
		UserResponse response = new UserResponse();
		if(userReq.getFirstName() != null)
		user.setFirstName(userReq.getFirstName());
		if(userReq.getLastName() != null)
		user.setLastName(userReq.getLastName());
		if(userReq.getEmail() != null)
		user.setEmail(userReq.getEmail());
		if(userReq.getPhoneNumber() != null)
		user.setPhoneNumber(userReq.getPhoneNumber());
		if(userReq.getAddressLine1() != null)
		user.setAddressLine1(userReq.getAddressLine1());
		if(userReq.getAddressLine2() != null)
		user.setAddressLine2(userReq.getAddressLine2());
		user.setModifiedBy("User_Kaj");
		user.setModifiedOn(new Date());
		
		Set<UserAccounts> userAccounts = new HashSet<>();

		for (AccountDetails accountDetail : userReq.getAccountDetails()) {
			
			UserAccounts userAccount = new UserAccounts();
			
			if(accountDetail.getAccountId() != null) {
				 userAccount = userAccountsRepo.getOne(accountDetail.getAccountId());
			}
			
			if(accountDetail.getAccountNumber() != null)
			userAccount.setAccountNumber(accountDetail.getAccountNumber());
			userAccount.setUser(user);
			if(accountDetail.getAccountType() != null)
			userAccount.setAccountType(accountDetail.getAccountType());
			if(accountDetail.getBankName() != null)
			userAccount.setBankName(accountDetail.getBankName());
			if(accountDetail.getBranchName() != null)
			userAccount.setBranchName(accountDetail.getBranchName());
			if(accountDetail.getBalance() != null)
			userAccount.setBalance(accountDetail.getBalance());
			userAccount.setModifiedBy("User_Kaj");
			userAccount.setModifiedOn(new Date());
			userAccounts.add(userAccount);
		}

		user.setUserAccounts(userAccounts);

		final Users updatedUser = usersRepo.save(user);

		MessageDetail messageDetail = new MessageDetail();

		if (updatedUser != null) {
			UserModel userModel = new UserModel();
			userModel.setUserId(updatedUser.getUserId());
			response.setUserInfo(userModel);
			messageDetail.setStatusCode(BaseConstants.SUCCESS_CODE);
			messageDetail.setStatus(BaseConstants.SUCCESS);
			messageDetail.setStatusMessage("User Updated Successfully");

		} else {
			messageDetail.setStatusCode(BaseConstants.FAILURE_CODE);
			messageDetail.setStatus(BaseConstants.FAILURE);
			messageDetail.setStatusMessage("Internal Error !! Please try again afetr some time.");
		}

		response.setMessage(messageDetail);

		return response;
	}

}
