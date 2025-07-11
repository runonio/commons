
CREATE TABLE category
(
    category_id          VARCHAR NOT NULL,
    category_type        VARCHAR NULL,
    name_ko              VARCHAR NULL,
    name_en              VARCHAR NULL,
    description          VARCHAR NULL,
    meta_data            VARCHAR NULL,
    is_del               boolean NOT NULL DEFAULT false,
    updated_at           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (category_id)
);

create index idx_category_01
    on category (updated_at desc);


CREATE TABLE category_code
(
    category_id          VARCHAR NOT NULL,
    code                 VARCHAR NOT NULL,
    name_ko              VARCHAR NULL,
    name_en              VARCHAR NULL,
    description          VARCHAR NULL,
    meta_data            VARCHAR NULL,
    is_del               boolean NOT NULL DEFAULT false,
    updated_at           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (category_id,code)
);


create index idx_category_code_01
    on category_code (updated_at desc);

CREATE TABLE category_key_value
(
    category_id          VARCHAR NOT NULL,
    data_key             VARCHAR NOT NULL,
    data_value           VARCHAR NULL,
    meta_data            VARCHAR NULL,
    updated_at           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (category_id,data_key)
);


create index idx_category_key_value_01
    on category_key_value (updated_at desc);


CREATE TABLE file
(
    file_id              VARCHAR NOT NULL,
    file_name            VARCHAR NULL,
    file_bytes           bytea NULL,
    sha256               VARCHAR NULL,
    split_type           VARCHAR NOT NULL DEFAULT 'single',
    split_info           VARCHAR NULL,
    encrypt_type         VARCHAR NOT NULL DEFAULT 'N',
    file_path_type       VARCHAR NULL DEFAULT 'DB',
    file_path            VARCHAR NULL,
    meta_data            VARCHAR NULL,
    created_at           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (file_id)
);


create index idx_file_01
    on file (updated_at desc);


CREATE TABLE key_value
(
    data_key             VARCHAR NOT NULL,
    data_value           VARCHAR NULL,
    data_type            VARCHAR NULL DEFAULT 'CONFIG',
    meta_data            VARCHAR NULL,
    is_del               boolean NOT NULL DEFAULT false,
    updated_at           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (data_key)
);


create index idx_key_value_01
    on key_value (updated_at desc);


create index idx_key_value_02
    on key_value (data_type desc);



comment on table category is '카테고리';
        comment on column category.category_id is '카테고리아이디';
         comment on column category.category_type is '카테고리유형';
         comment on column category.name_ko is '이름_한글';
         comment on column category.name_en is '이름_영문';
         comment on column category.description is 'description';
         comment on column category.meta_data is 'meta_data';
         comment on column category.is_del is '삭제여부';
         comment on column category.updated_at is '업데이트일시';

comment on table category_code is '카테고리코드';
        comment on column category_code.category_id is '카테고리아이디';
         comment on column category_code.code is '코드';
         comment on column category_code.name_ko is '이름_한글';
         comment on column category_code.name_en is '이름_영문';
         comment on column category_code.description is 'description';
         comment on column category_code.meta_data is 'meta_data';
         comment on column category_code.is_del is '삭제여부';
         comment on column category_code.updated_at is '업데이트일시';

comment on table category_key_value is '카테고리key_value';
        comment on column category_key_value.category_id is '카테고리아이디';
         comment on column category_key_value.data_key is '데이터키';
         comment on column category_key_value.data_value is '데이터값';
         comment on column category_key_value.meta_data is 'meta_data';
         comment on column category_key_value.updated_at is '업데이트일시';

comment on table file is '파일';
        comment on column file.file_id is '파일아이디';
         comment on column file.file_name is '파일명';
         comment on column file.file_bytes is '파일_바이트배열';
         comment on column file.sha256 is 'sha256';
         comment on column file.split_type is '분할유형';
         comment on column file.split_info is '분할정보';
         comment on column file.encrypt_type is '암호화모드';
         comment on column file.file_path_type is '파일경로유형';
         comment on column file.file_path is '파일참조경로';
         comment on column file.meta_data is 'meta_data';
         comment on column file.created_at is '생성일시';
         comment on column file.updated_at is '업데이트일시';

comment on table key_value is 'key_value';
        comment on column key_value.data_key is '데이터키';
         comment on column key_value.data_value is '데이터값';
         comment on column key_value.data_type is '데이터유형';
         comment on column key_value.meta_data is 'meta_data';
         comment on column key_value.is_del is '삭제여부';
         comment on column key_value.updated_at is '업데이트일시';




