package com.fairy.models.common;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

public class DockerUtils {
	protected static DockerClient dockerClient;
	
	static {
		dockerClient = getDockerClient();
	}
	
	protected DockerUtils() {
	}
	
	public static DockerClient getDockerClient() {
		if(dockerClient == null ) {
			DefaultDockerClientConfig.Builder config = DefaultDockerClientConfig
					.createDefaultConfigBuilder();
			dockerClient = DockerClientBuilder
			  .getInstance(config)
			  .build();
		}
		return dockerClient;
	}
	
	public static CreateContainerResponse createFairyModel(String gitAddr) {
		return dockerClient
		.createContainerCmd("fairy/model:latest")
		.withEnv(String.format("GIT_ADDR=%s", gitAddr))
		.exec();
	}
}
