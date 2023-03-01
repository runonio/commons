

CREATE TABLE collect
(
    collect_id           VARCHAR NOT NULL,
    name                 VARCHAR NULL,
    collect_type         VARCHAR NOT NULL,
    collect_path         VARCHAR NULL,
    data_value           VARCHAR NULL,
    description          VARCHAR NULL,
    updated_at           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (collect_id)
);



CREATE TABLE collect_group
(
    collect_group_id     VARCHAR NOT NULL,
    name                 VARCHAR NULL,
    data_value           VARCHAR NULL,
    description          VARCHAR NULL,
    updated_at           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (collect_group_id)
);



CREATE TABLE collect_group_map
(
    collect_group_id     VARCHAR NOT NULL,
    collect_id           VARCHAR NOT NULL,
    created_at           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (collect_group_id,collect_id)
);



CREATE TABLE collect_info
(
    collect_id           VARCHAR NOT NULL,
    data_key             VARCHAR NOT NULL,
    collect_info         TEXT NULL,
    collected_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (collect_id,data_key)
);



comment on table collect is '수집기';
        comment on column collect.collect_id is '수집기아이디';
         comment on column collect.name is '이름';
         comment on column collect.collect_type is '수집유형';
         comment on column collect.collect_path is '수집경로';
         comment on column collect.data_value is '데이터값';
         comment on column collect.description is 'description';
         comment on column collect.updated_at is '업데이트일시';

comment on table collect_group is '수집그룹';
        comment on column collect_group.collect_group_id is '수집그릅아이디';
         comment on column collect_group.name is '이름';
         comment on column collect_group.data_value is '데이터값';
         comment on column collect_group.description is 'description';
         comment on column collect_group.updated_at is '업데이트일시';

comment on table collect_group_map is '수집기그룹맵';
        comment on column collect_group_map.collect_group_id is '수집그릅아이디';
         comment on column collect_group_map.collect_id is '수집기아이디';
         comment on column collect_group_map.created_at is '등록일시';

comment on table collect_info is '수집정보';
        comment on column collect_info.collect_id is '수집기아이디';
         comment on column collect_info.data_key is '데이터키';
         comment on column collect_info.collect_info is '수집정보';
         comment on column collect_info.collected_at is '수집일시';




