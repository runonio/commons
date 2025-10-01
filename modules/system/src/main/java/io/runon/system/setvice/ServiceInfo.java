package io.runon.system.setvice;

import io.runon.jdbc.annotation.Column;
import io.runon.jdbc.annotation.Table;
import io.runon.jdbc.annotation.PrimaryKey;
import io.runon.jdbc.annotation.DateTime;

/**
 * 서비스 정보
 * @author macle
 */
@Table(name="service")
public class ServiceInfo {
    @PrimaryKey(seq = 1)
    @Column(name = "service_id")
    String serviceId;

    @Column(name = "service_name")
    String serviceName;

    @Column(name = "service_type")
    String serviceType;

    @Column(name = "host_address")
    String hostAddress;

    @Column(name = "port")
    Integer port;

    @Column(name = "description")
    String description;

    @Column(name = "meta_data")
    String metaData;

    @DateTime
    @Column(name = "started_at")
    Long startedAt;

    @DateTime
    @Column(name = "ended_at")
    Long endedAt;

    @DateTime
    @Column(name = "updated_at")
    long updatedAt  = System.currentTimeMillis();


}