package org.dampati.backend.dto;

public class SearchProfileDTO {
    private Long id;
    private String name;
    private String gender;
    private String dateOfBirth;
    private String religion;
    private String motherTongue;
    private String location;
    private String education;
    private String occupation;

    public SearchProfileDTO(
            Long id,
            String name,
            String gender,
            String dateOfBirth,
            String religion,
            String motherTongue,
            String location,
            String education,
            String occupation
    ) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.religion = religion;
        this.motherTongue = motherTongue;
        this.location = location;
        this.education = education;
        this.occupation = occupation;
    }


    public Long getId() {return id;}
    public String getName() { return name;}
    public String getGender() {return gender;}
    public String getDateOfBirth() {return dateOfBirth;}
    public String getReligion() {return religion;}
    public String getMotherTongue() {return motherTongue;}
    public String getLocation() {return location;}
    public String getEducation() {return education;}
    public String getOccupation() {return occupation;}
}
