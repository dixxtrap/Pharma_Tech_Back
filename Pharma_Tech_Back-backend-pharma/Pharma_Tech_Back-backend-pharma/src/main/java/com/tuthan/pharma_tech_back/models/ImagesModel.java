package com.tuthan.pharma_tech_back.models;

import javax.persistence.*;

@Entity
@Table(name = "image_model")
public class ImagesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String type;
    @Column(length = 50000000)
    private byte[] pycByte;

    public ImagesModel() {
    }

    public ImagesModel(String name, String type, byte[] pycByte) {
        this.name = name;
        this.type = type;
        this.pycByte = pycByte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getPycByte() {
        return pycByte;
    }

    public void setPycByte(byte[] pycByte) {
        this.pycByte = pycByte;
    }
}
