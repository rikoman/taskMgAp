package com.example.backend.api.service;

import com.example.backend.api.DTO.CorporateProjectDTO;
import com.example.backend.api.exception.NotFoundException;
import com.example.backend.story.entity.CorporateProject;
import com.example.backend.story.repository.CorporateProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CorporateProjectService {

    private CorporateProjectRepository corporateProjectRepository;
    private UserService userService;

    public CorporateProject createCorporateProject(CorporateProjectDTO dto){
        CorporateProject project = new CorporateProject();
        project.setTitle(dto.getTitle());
        project.setDescription(dto.getDescription());
        project.setUsers(userService.readAllUserByIds(dto.getUsers()));
        return corporateProjectRepository.save(project);
    }

    public List<CorporateProject> readAllProject(){
        return corporateProjectRepository.findAll();
    }

    public List<CorporateProject> readAllCorporateProjectByUserId(Long id){
        return corporateProjectRepository.findByUsersId(id);
    }

    public CorporateProject readCorporateProjectById(Long id){
        return corporateProjectRepository.findById(id).orElseThrow(()->new NotFoundException(String.format("Corporate project with %s id doesn' exist",id)));
    }

    public CorporateProject updateCorporateProject(CorporateProject corporateProject){
        return corporateProjectRepository.save(corporateProject);
    }

    public void deleteCorporateProject(Long id){
        readCorporateProjectById(id);
        corporateProjectRepository.deleteById(id);
    }
}