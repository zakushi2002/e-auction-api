package com.e.auction.api.controller;

import com.e.auction.api.repository.GroupRepository;
import com.e.auction.api.repository.PermissionRepository;
import com.e.auction.api.view.dto.ApiMessageDto;
import com.e.auction.api.view.dto.ErrorCode;
import com.e.auction.api.view.dto.ResponseListDto;
import com.e.auction.api.view.dto.group.GroupDto;
import com.e.auction.api.view.form.group.CreateGroupForm;
import com.e.auction.api.view.form.group.UpdateGroupForm;
import com.e.auction.api.view.mapper.GroupMapper;
import com.e.auction.api.exception.UnauthorizationException;
import com.e.auction.api.model.Group;
import com.e.auction.api.model.Permission;
import com.e.auction.api.model.criteria.GroupCriteria;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/group")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@ApiIgnore
public class GroupController extends BaseController {
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    GroupMapper groupMapper;
    @Autowired
    PermissionRepository permissionRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('GR_C')")
    @Transactional
    public ApiMessageDto<String> createGroup(@Valid @RequestBody CreateGroupForm createGroupForm, BindingResult bindingResult) {
        if (!isSuperAdmin()) {
            throw new UnauthorizationException("Not allowed create.");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Group group = groupRepository.findFirstByName(createGroupForm.getName());
        if (group != null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.GROUP_ERROR_NAME_EXIST);
            apiMessageDto.setMessage("Group name is exist");
            return apiMessageDto;
        }
        group = groupMapper.fromCreateGroupFormToEntity(createGroupForm);
        List<Permission> permissionList = new ArrayList<>();
        for (long permissionId : createGroupForm.getPermissions()) {
            Permission permission = permissionRepository.findById(permissionId).orElse(null);
            if (permission != null) {
                permissionList.add(permission);
            }
        }
        group.setPermissions(permissionList);
        groupRepository.save(group);
        apiMessageDto.setMessage("Create group success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('GR_U')")
    public ApiMessageDto<String> updateGroup(@Valid @RequestBody UpdateGroupForm updateGroupForm, BindingResult bindingResult) {
        if (!isSuperAdmin()) {
            throw new UnauthorizationException("Not allowed update.");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Group group = groupRepository.findById(updateGroupForm.getId()).orElse(null);
        if (group == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.GROUP_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Group name doesn't exist");
            return apiMessageDto;
        }
        Group groupCheck = groupRepository.findFirstByName(updateGroupForm.getName());
        if (groupCheck != null && !Objects.equals(groupCheck.getId(), group.getId())) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.GROUP_ERROR_NAME_EXIST);
            apiMessageDto.setMessage("Group name is exist");
            return apiMessageDto;
        }
        if (StringUtils.isNoneBlank(updateGroupForm.getName())) {
            group.setName(updateGroupForm.getName());
        }
        if (StringUtils.isNoneBlank(updateGroupForm.getDescription())) {
            group.setDescription(updateGroupForm.getDescription());
        }
        List<Permission> permissionList = new ArrayList<>();
        for (long permissionId : updateGroupForm.getPermissions()) {
            Permission permission = permissionRepository.findById(permissionId).orElse(null);
            if (permission != null) {
                permissionList.add(permission);
            }
        }
        group.setPermissions(permissionList);
        groupRepository.save(group);
        apiMessageDto.setMessage("Update group success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('GR_L')")
    public ApiMessageDto<ResponseListDto<GroupDto>> listGroup(@Valid GroupCriteria groupCriteria, Pageable pageable) {
        if (!isSuperAdmin()) {
            throw new UnauthorizationException("Not allowed list.");
        }
        Page<Group> page = groupRepository.findAll(groupCriteria.getSpecification(), pageable);
        ResponseListDto<GroupDto> responseListDto = new ResponseListDto(groupMapper.fromEntityToGroupDtoList(page.getContent()), page.getTotalElements(), page.getTotalPages());
        ApiMessageDto<ResponseListDto<GroupDto>> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List group success");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('GR_V')")
    public ApiMessageDto<GroupDto> getGroup(@PathVariable("id") Long id) {
        if (!isSuperAdmin()) {
            throw new UnauthorizationException("Not allowed to get.");
        }
        ApiMessageDto<GroupDto> apiMessageDto = new ApiMessageDto<>();
        Group group = groupRepository.findById(id).orElse(null);
        apiMessageDto.setData(groupMapper.fromEntityToGroupDto(group));
        apiMessageDto.setMessage("Get group success");
        return apiMessageDto;
    }
}
