package com.job_portal.job_portal.service;

import com.job_portal.job_portal.entity.JobPost;
import com.job_portal.job_portal.repository.JobPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class JobServiceImp implements JobPostService{
    public static String DIRECTORY_PATH = "/u02/uploads/";
    private final JobPostRepository jobPostRepository;

    public JobServiceImp(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    @Override
    public JobPost createJob(JobPost job) {
         JobPost jobPost=jobPostRepository.findByJobTitle(job.getJobTitle()).orElse(null);
         if (jobPost==null){
             JobPost jobPost1=JobPost.builder()
                     .jobDescription(job.getJobDescription())
                     .jobRole(job.getJobRole())
                     .approved(false)
                     .employer(job.getEmployer())
                     .postedDate(LocalDate.now())
                     .jobTitle(job.getJobTitle())
                     .experienceRequired(job.getExperienceRequired())
                     .location(job.getLocation())
                     .requiredSkills(job.getRequiredSkills())
                     .build();
             return jobPostRepository.save(jobPost1);
         }else{
             return null;
         }
    }
    public String saveFileToStorage(MultipartFile file){
        String extensionType=file.getContentType();//image/png

        String extension= "";
        if (!extensionType.isEmpty()) {
            String[] parts = extensionType.split("/");
            if (parts.length > 1) {
                extension = "." + parts[1];
            }
        }
        String fileName=UUID.randomUUID().toString().replace("-","") +extension;
        try{
            File directory=new File(DIRECTORY_PATH);
            if(!directory.exists()){
                directory.mkdirs();
            }

            File outputFile=new File(DIRECTORY_PATH+fileName);
            FileOutputStream outputStream=new FileOutputStream(outputFile);
            outputStream.write(file.getBytes());
            outputStream.close();
            System.out.println("File saved successfully to: " + outputFile.getAbsolutePath());
        }catch(IOException e){
            System.out.println("Error saving file: " + e.getMessage());
        }
        return fileName;
    }
    }


