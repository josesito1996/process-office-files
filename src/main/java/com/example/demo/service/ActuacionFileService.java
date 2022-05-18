package com.example.demo.service;

import com.example.demo.model.ActuacionFileRequest;
import com.example.demo.model.ActuacionFileResponse;

public interface ActuacionFileService {

	ActuacionFileResponse uploadFile(ActuacionFileRequest request);

}
