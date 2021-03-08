prompt PL/SQL Developer Export User Objects for user XXI@MJ_ORCL
prompt Created by Said on 8 РњР°СЂС‚ 2021 Рі.
set define off
spool MJ_ALL.log

prompt
prompt Creating table AC_ACTION
prompt ========================
prompt
create table XXI.AC_ACTION
(
  id           INTEGER not null,
  idparent     INTEGER not null,
  cdescription VARCHAR2(250) not null,
  cmultidefint VARCHAR2(30)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 80K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.AC_ACTION
  is 'Автозаполнение: Дерево действий. Не очищать!!!';
create index XXI.I_AC_ACTION_IDPARENT on XXI.AC_ACTION (IDPARENT)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.AC_ACTION
  add constraint PK_AC_ACTION primary key (ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.AC_ACTION
  add constraint U_AC_ACTION_MULTIDEFINT unique (CMULTIDEFINT)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete on XXI.AC_ACTION to ODB;

prompt
prompt Creating table AC_DATA
prompt ======================
prompt
create table XXI.AC_DATA
(
  idaction     INTEGER not null,
  cdescription VARCHAR2(60) not null,
  cdefint      VARCHAR2(100) not null,
  cdefinttype  CHAR(1),
  dlastupdate  DATE default TO_DATE ('01010001','DDMMYYYY') not null,
  ccode        CLOB,
  idreporttype INTEGER,
  idreport     INTEGER,
  cactiontype  CHAR(1),
  cinvoupdate  VARCHAR2(1)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 320K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.AC_DATA
  is 'Автозаполнение: Привязка данных к действиям. Не очищать!!!';
create index XXI.IF_AC_DATA_AP_REPORT_CAT on XXI.AC_DATA (IDREPORTTYPE, IDREPORT)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.IF_AC_DATA_ID on XXI.AC_DATA (IDACTION)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.AC_DATA
  add constraint PK_AC_DATA primary key (CDEFINT)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.AC_DATA
  add constraint FK_AC_DATA foreign key (IDACTION)
  references XXI.AC_ACTION (ID) on delete cascade;
grant select, insert, update, delete on XXI.AC_DATA to ODB;

prompt
prompt Creating table RAION
prompt ====================
prompt
create table XXI.RAION
(
  code NUMBER not null,
  name NVARCHAR2(100) not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.RAION
  is 'Список районов РА';
comment on column XXI.RAION.code
  is 'Код';
comment on column XXI.RAION.name
  is 'Наименование';
alter table XXI.RAION
  add constraint PK_RAION_CODE primary key (CODE)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.RAION
  add constraint PK_RAION_NAME unique (NAME)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete on XXI.RAION to ODB;

prompt
prompt Creating table OTD
prompt ==================
prompt
create table XXI.OTD
(
  iotdnum  NUMBER(4) not null,
  cotdname VARCHAR2(500) not null,
  area_id  NUMBER
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.OTD
  is 'Отделения';
comment on column XXI.OTD.iotdnum
  is 'Номер отделения';
comment on column XXI.OTD.cotdname
  is 'Название';
comment on column XXI.OTD.area_id
  is 'Ссылка на страну';
create index XXI.I_AREA_ID on XXI.OTD (AREA_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.OTD
  add constraint P_OTD primary key (IOTDNUM)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.OTD
  add constraint FK_OTD_AREA foreign key (AREA_ID)
  references XXI.RAION (CODE);
grant select, insert, update, delete on XXI.OTD to ODB;

prompt
prompt Creating table ZAGS
prompt ===================
prompt
create table XXI.ZAGS
(
  zags_id       NUMBER not null,
  zags_otd      NUMBER not null,
  zags_name     VARCHAR2(500) not null,
  zags_ruk      VARCHAR2(500) not null,
  zags_adr      VARCHAR2(500),
  zags_city_abh VARCHAR2(500),
  zags_adr_abh  VARCHAR2(500),
  zags_ruk_abh  VARCHAR2(500),
  addr          VARCHAR2(500),
  addr_abh      VARCHAR2(500)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.ZAGS
  is 'Список наименовании органа ЗАГСа';
comment on column XXI.ZAGS.zags_id
  is 'ИД';
comment on column XXI.ZAGS.zags_otd
  is 'Город';
comment on column XXI.ZAGS.zags_name
  is 'Наименование ЗАГСа';
comment on column XXI.ZAGS.zags_ruk
  is 'Руководитель';
comment on column XXI.ZAGS.zags_adr
  is 'Адрес';
comment on column XXI.ZAGS.zags_city_abh
  is 'Город на абх';
comment on column XXI.ZAGS.zags_adr_abh
  is 'Ардес на абх';
comment on column XXI.ZAGS.zags_ruk_abh
  is 'Руководитель на абх';
comment on column XXI.ZAGS.addr
  is 'Адрес';
comment on column XXI.ZAGS.addr_abh
  is 'Адрес на Абх';
alter table XXI.ZAGS
  add constraint ZAGS_ID_PK primary key (ZAGS_ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.ZAGS
  add constraint ZAGS_OTD_FK foreign key (ZAGS_OTD)
  references XXI.OTD (IOTDNUM);
grant select, insert, update, delete on XXI.ZAGS to ODB;

prompt
prompt Creating table DEATH_CERT
prompt =========================
prompt
create table XXI.DEATH_CERT
(
  dc_id             NUMBER not null,
  dc_cus            NUMBER,
  dc_dd             DATE,
  dc_dpl            VARCHAR2(200),
  dc_cd             VARCHAR2(200),
  dc_fnum           VARCHAR2(200),
  dc_fd             DATE,
  dc_ftype          VARCHAR2(1),
  dc_fmon           VARCHAR2(200),
  dc_rcname         VARCHAR2(200),
  dc_nrname         VARCHAR2(200),
  dc_lloc           VARCHAR2(200),
  dc_ztp            VARCHAR2(1),
  dc_fadfirst_name  VARCHAR2(200),
  dc_fadlast_name   VARCHAR2(200),
  dc_fadmiddle_name VARCHAR2(200),
  dc_fadlocation    VARCHAR2(200),
  dc_fadorg_name    VARCHAR2(200),
  dc_fadreg_adr     VARCHAR2(200),
  dc_seria          VARCHAR2(200),
  dc_number         VARCHAR2(200),
  dc_usr            VARCHAR2(200) default user not null,
  dc_open           DATE default sysdate not null,
  dc_zags           NUMBER
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.DEATH_CERT
  is 'Свидетельство о смерти';
comment on column XXI.DEATH_CERT.dc_id
  is 'ID';
comment on column XXI.DEATH_CERT.dc_cus
  is 'Ссылка на cus';
comment on column XXI.DEATH_CERT.dc_dd
  is 'Дата смерти';
comment on column XXI.DEATH_CERT.dc_dpl
  is 'Место смерти';
comment on column XXI.DEATH_CERT.dc_cd
  is 'Причина смерти';
comment on column XXI.DEATH_CERT.dc_fnum
  is 'A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Номер';
comment on column XXI.DEATH_CERT.dc_fd
  is 'A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Дата';
comment on column XXI.DEATH_CERT.dc_ftype
  is 'Док. подтвержд. факт смерти-Тип';
comment on column XXI.DEATH_CERT.dc_fmon
  is 'A-Док. подтвержд. факт смерти-Док. установл. формы о смерти-Наименование мед. организации';
comment on column XXI.DEATH_CERT.dc_rcname
  is 'B-Док. подтвержд. факт смерти-Решение суда-Наименование суда';
comment on column XXI.DEATH_CERT.dc_nrname
  is 'V-Док. подтвержд. факт смерти-Необоснованно репрес.-Наименование органа выд. документ';
comment on column XXI.DEATH_CERT.dc_lloc
  is 'Последнее место жительства';
comment on column XXI.DEATH_CERT.dc_ztp
  is 'Тип заявителя.F-физ.-J-юр.';
comment on column XXI.DEATH_CERT.dc_fadfirst_name
  is 'Имя (Заявитель о смерти)';
comment on column XXI.DEATH_CERT.dc_fadlast_name
  is 'Фамилия (Заявитель о смерти)';
comment on column XXI.DEATH_CERT.dc_fadmiddle_name
  is 'Отчество (Заявитель о смерти)';
comment on column XXI.DEATH_CERT.dc_fadlocation
  is 'Место жительства (Заявитель о смерти)';
comment on column XXI.DEATH_CERT.dc_fadorg_name
  is 'Наименование организации (Заявитель о смерти)';
comment on column XXI.DEATH_CERT.dc_fadreg_adr
  is 'Адрес регистрации (Заявитель о смерти)';
comment on column XXI.DEATH_CERT.dc_seria
  is 'Выдано свидетельство серия';
comment on column XXI.DEATH_CERT.dc_number
  is 'Выдано свидетельство номер';
comment on column XXI.DEATH_CERT.dc_usr
  is 'Пользователь';
comment on column XXI.DEATH_CERT.dc_open
  is 'Дата документа';
comment on column XXI.DEATH_CERT.dc_zags
  is 'Наименование загса';
create index XXI.I_DC_CUS on XXI.DEATH_CERT (DC_CUS)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_DC_DD on XXI.DEATH_CERT (DC_DD)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_DC_FTYPE on XXI.DEATH_CERT (DC_FTYPE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_DC_OPEN on XXI.DEATH_CERT (DC_OPEN)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_DC_USR on XXI.DEATH_CERT (DC_USR)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_DC_ZAGS on XXI.DEATH_CERT (DC_ZAGS)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_DC_ZTP on XXI.DEATH_CERT (DC_ZTP)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.DEATH_CERT
  add constraint PK_DC_ID primary key (DC_ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.DEATH_CERT
  add constraint FK_DC_ZAGS foreign key (DC_ZAGS)
  references XXI.ZAGS (ZAGS_ID);
alter table XXI.DEATH_CERT
  add constraint CH_DC_FTYPE
  check (DC_FTYPE in ('A','B','B1','V'));
alter table XXI.DEATH_CERT
  add constraint CH_DC_ZTP
  check (DC_ZTP in ('F','J'));
grant select, insert, update, delete on XXI.DEATH_CERT to ODB;

prompt
prompt Creating table DIVORCE_CERT
prompt ===========================
prompt
create table XXI.DIVORCE_CERT
(
  divc_id        NUMBER not null,
  divc_he        NUMBER,
  divc_she       NUMBER,
  divc_he_lnbef  VARCHAR2(200),
  divc_he_lnaft  VARCHAR2(200),
  divc_she_lnbef VARCHAR2(200),
  divc_she_lnaft VARCHAR2(200),
  divc_date      DATE default sysdate not null,
  divc_dt        DATE,
  divc_usr       VARCHAR2(100) default user not null,
  divc_type      VARCHAR2(2),
  divc_tchd      DATE,
  divc_tchnum    VARCHAR2(50),
  divc_can       VARCHAR2(200),
  divc_cad       DATE,
  divc_zoscn     VARCHAR2(200),
  divc_zoscd     DATE,
  divc_zosfio    VARCHAR2(100),
  divc_zoscn2    VARCHAR2(200),
  divc_zoscd2    DATE,
  divc_zosfio2   VARCHAR2(100),
  divc_zosprison NUMBER,
  divc_mc_mercer NUMBER,
  divc_num       VARCHAR2(100),
  divc_seria     VARCHAR2(100),
  divc_zags      NUMBER,
  divc_zlname    VARCHAR2(100),
  divc_zаname    VARCHAR2(100),
  divc_zmname    VARCHAR2(100),
  divc_zplace    VARCHAR2(100)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.DIVORCE_CERT
  is 'Свидетельство о расторжении брака';
comment on column XXI.DIVORCE_CERT.divc_id
  is 'ID';
comment on column XXI.DIVORCE_CERT.divc_he
  is 'Он ссылка на CUS';
comment on column XXI.DIVORCE_CERT.divc_she
  is 'Она ссылка на CUS';
comment on column XXI.DIVORCE_CERT.divc_he_lnbef
  is 'Фамилия его до рб';
comment on column XXI.DIVORCE_CERT.divc_he_lnaft
  is 'Фамилия его после рб';
comment on column XXI.DIVORCE_CERT.divc_she_lnbef
  is 'Фамилия ее до рб';
comment on column XXI.DIVORCE_CERT.divc_she_lnaft
  is 'Фамилия ее после рб';
comment on column XXI.DIVORCE_CERT.divc_date
  is 'Дата документа';
comment on column XXI.DIVORCE_CERT.divc_dt
  is 'Дата прекращения брака';
comment on column XXI.DIVORCE_CERT.divc_usr
  is 'Пользователь';
comment on column XXI.DIVORCE_CERT.divc_type
  is 'Типы основании расторжения брака';
comment on column XXI.DIVORCE_CERT.divc_tchd
  is 'A-(Расторжение, совместное заявление) дата';
comment on column XXI.DIVORCE_CERT.divc_tchnum
  is 'A-(Расторжение, совместное заявление) номер';
comment on column XXI.DIVORCE_CERT.divc_can
  is 'B-(Расторжение,решение суда) наименование суда';
comment on column XXI.DIVORCE_CERT.divc_cad
  is 'B-(Расторжение,решение суда) дата';
comment on column XXI.DIVORCE_CERT.divc_zoscn
  is 'V-(Расторжение,заявление одного из супругов) наименование суда';
comment on column XXI.DIVORCE_CERT.divc_zoscd
  is 'V-(Расторжение,заявление одного из супругов) дата решения суда';
comment on column XXI.DIVORCE_CERT.divc_zosfio
  is 'V-(Расторжение,заявление одного из супругов) о признании ФИО';
comment on column XXI.DIVORCE_CERT.divc_zoscn2
  is 'V-(Расторжение,приговор суда) приговор суда=наименование суда ';
comment on column XXI.DIVORCE_CERT.divc_zoscd2
  is 'V-(Расторжение,приговор суда) приговор суда=дата (от)';
comment on column XXI.DIVORCE_CERT.divc_zosfio2
  is 'V-(Расторжение,приговор суда) приговор суда=об осуждении ФИО';
comment on column XXI.DIVORCE_CERT.divc_zosprison
  is 'V-(Расторжение,приговор суда) приговор суда=лишение свободы (количество лет)';
comment on column XXI.DIVORCE_CERT.divc_mc_mercer
  is 'Ссылка на акт о заключении брака';
comment on column XXI.DIVORCE_CERT.divc_num
  is 'Выдано свидетельство номер';
comment on column XXI.DIVORCE_CERT.divc_seria
  is 'Выдано свидетельство серия';
comment on column XXI.DIVORCE_CERT.divc_zags
  is 'Ссылка на загс';
comment on column XXI.DIVORCE_CERT.divc_zlname
  is 'Заявитель Фамилия';
comment on column XXI.DIVORCE_CERT.divc_zаname
  is 'Заявитель Имя';
comment on column XXI.DIVORCE_CERT.divc_zmname
  is 'Заявитель Отчество';
comment on column XXI.DIVORCE_CERT.divc_zplace
  is 'Заявитель Место жительства';
create index XXI.I_DIVC_DATE on XXI.DIVORCE_CERT (DIVC_DATE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_DIVC_HE on XXI.DIVORCE_CERT (DIVC_HE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_DIVC_MC_MERCER on XXI.DIVORCE_CERT (DIVC_MC_MERCER)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_DIVC_SHE on XXI.DIVORCE_CERT (DIVC_SHE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_DIVC_TYPE on XXI.DIVORCE_CERT (DIVC_TYPE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_DIVC_USR on XXI.DIVORCE_CERT (DIVC_USR)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_DIVC_ZAGS on XXI.DIVORCE_CERT (DIVC_ZAGS)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.DIVORCE_CERT
  add constraint PK_DIVORCE_CERT_ID primary key (DIVC_ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.DIVORCE_CERT
  add constraint FK_DIVC_MC_MERCER foreign key (DIVC_MC_MERCER)
  references XXI.MC_MERCER (MERCER_ID) on delete cascade
  disable
  novalidate;
alter table XXI.DIVORCE_CERT
  add constraint FK_DIVC_ZAGS foreign key (DIVC_ZAGS)
  references XXI.ZAGS (ZAGS_ID);
alter table XXI.DIVORCE_CERT
  add constraint CH_DIVC_TYPE
  check (DIVC_TYPE in ('A','B','V1','V2','V3'));
grant select, insert, update, delete on XXI.DIVORCE_CERT to ODB;

prompt
prompt Creating table MC_MERCER
prompt ========================
prompt
create table XXI.MC_MERCER
(
  mercer_id         NUMBER not null,
  mercer_he         NUMBER,
  mercer_she        NUMBER,
  mercer_he_lnbef   VARCHAR2(200),
  mercer_he_lnaft   VARCHAR2(200),
  mercer_she_lnbef  VARCHAR2(200),
  mercer_she_lnbaft VARCHAR2(200),
  mercer_heage      NUMBER,
  mercer_sheage     NUMBER,
  mercer_date       DATE default sysdate not null,
  mercer_usr        VARCHAR2(100) default user not null,
  mercer_zags       NUMBER,
  mercer_divshe     NUMBER,
  mercer_divhe      NUMBER,
  mercer_dspmt_he   VARCHAR2(1),
  mercer_num        VARCHAR2(100),
  mercer_seria      VARCHAR2(100),
  mercer_dieshe     NUMBER,
  mercer_diehe      NUMBER,
  mercer_other      VARCHAR2(400),
  mercer_dspmt_she  VARCHAR2(1),
  mc_date           DATE
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.MC_MERCER
  is 'Запись акта о заключении брака';
comment on column XXI.MC_MERCER.mercer_id
  is 'ID';
comment on column XXI.MC_MERCER.mercer_he
  is 'Он ссылка на CUS';
comment on column XXI.MC_MERCER.mercer_she
  is 'Она ссылка на CUS';
comment on column XXI.MC_MERCER.mercer_he_lnbef
  is 'Фамиля его до зб';
comment on column XXI.MC_MERCER.mercer_he_lnaft
  is 'Фамиля его после зб';
comment on column XXI.MC_MERCER.mercer_she_lnbef
  is 'Фамиля ее до зб';
comment on column XXI.MC_MERCER.mercer_she_lnbaft
  is 'Фамиля ее после зб';
comment on column XXI.MC_MERCER.mercer_heage
  is 'Он лет';
comment on column XXI.MC_MERCER.mercer_sheage
  is 'Она лет';
comment on column XXI.MC_MERCER.mercer_date
  is 'Дата заведения';
comment on column XXI.MC_MERCER.mercer_usr
  is 'пользователь';
comment on column XXI.MC_MERCER.mercer_zags
  is 'Наименование ЗАГСа';
comment on column XXI.MC_MERCER.mercer_divshe
  is 'Свидетельство расторжения брака Она (ссылка)';
comment on column XXI.MC_MERCER.mercer_divhe
  is 'Свидетельство расторжения брака Он (ссылка)';
comment on column XXI.MC_MERCER.mercer_dspmt_he
  is 'Тип докум. подтв. прекр. пред. брака, Он';
comment on column XXI.MC_MERCER.mercer_num
  is 'Номер свид.';
comment on column XXI.MC_MERCER.mercer_seria
  is 'Серия свид.';
comment on column XXI.MC_MERCER.mercer_dieshe
  is 'Свид. о смерти её пред. брака (Ссылка)';
comment on column XXI.MC_MERCER.mercer_diehe
  is 'Свид. о смерти его пред. брака (Ссылка)';
comment on column XXI.MC_MERCER.mercer_other
  is 'Иные сведения и служебные пометки';
comment on column XXI.MC_MERCER.mercer_dspmt_she
  is 'Тип докум. подтв. прекр. пред. брака, Она';
comment on column XXI.MC_MERCER.mc_date
  is 'Дата заключения брака';
create index XXI.I_MERCER_DATE on XXI.MC_MERCER (MERCER_DATE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_MERCER_DIEHE on XXI.MC_MERCER (MERCER_DIEHE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_MERCER_DIESHE on XXI.MC_MERCER (MERCER_DIESHE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_MERCER_DIVHE on XXI.MC_MERCER (MERCER_DIVHE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_MERCER_DIVSHE on XXI.MC_MERCER (MERCER_DIVSHE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_MERCER_DSPMT_HE on XXI.MC_MERCER (MERCER_DSPMT_HE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_MERCER_HE on XXI.MC_MERCER (MERCER_HE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_MERCER_SHE on XXI.MC_MERCER (MERCER_SHE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_MERCER_USR on XXI.MC_MERCER (MERCER_USR)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_MERCER_ZAGS on XXI.MC_MERCER (MERCER_ZAGS)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.MC_MERCER
  add constraint PK_MERCER_ID primary key (MERCER_ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.MC_MERCER
  add constraint FK_MERCER_DIEHE foreign key (MERCER_DIEHE)
  references XXI.DEATH_CERT (DC_ID) on delete cascade
  disable
  novalidate;
alter table XXI.MC_MERCER
  add constraint FK_MERCER_DIESHE foreign key (MERCER_DIESHE)
  references XXI.DEATH_CERT (DC_ID) on delete cascade
  disable
  novalidate;
alter table XXI.MC_MERCER
  add constraint FK_MERCER_DIVSHE foreign key (MERCER_DIVSHE)
  references XXI.DIVORCE_CERT (DIVC_ID) on delete cascade
  disable
  novalidate;
alter table XXI.MC_MERCER
  add constraint FK_MERCER_ZAGS foreign key (MERCER_ZAGS)
  references XXI.ZAGS (ZAGS_ID);
alter table XXI.MC_MERCER
  add constraint CH_MERCER_DSPMT_HE
  check (MERCER_DSPMT_HE in ('A','B'));
alter table XXI.MC_MERCER
  add constraint CH_MERCER_DSPMT_SHE
  check (MERCER_DSPMT_SHE in ('A','B'));
grant select, insert, update, delete on XXI.MC_MERCER to ODB;

prompt
prompt Creating table BRN_BIRTH_ACT
prompt ============================
prompt
create table XXI.BRN_BIRTH_ACT
(
  br_act_id             NUMBER not null,
  br_act_date           DATE default sysdate not null,
  br_act_ztp            VARCHAR2(1),
  br_act_brchcnt        NUMBER,
  br_act_ld             VARCHAR2(1),
  br_act_zgid           NUMBER,
  br_act_user           VARCHAR2(100) default user not null,
  br_act_tgrabf         VARCHAR2(1),
  br_act_mzdate         DATE,
  br_act_dbf            VARCHAR2(1),
  br_act_ch             NUMBER,
  br_act_f              NUMBER,
  br_act_m              NUMBER,
  br_act_medorga        VARCHAR2(200),
  br_act_datedoca       DATE,
  br_act_ndoca          VARCHAR2(200),
  br_act_fiob           VARCHAR2(200),
  br_act_datedocb       DATE,
  br_act_namecourt      VARCHAR2(200),
  br_act_desccourt      VARCHAR2(200),
  br_act_dcourt         DATE,
  br_act_fadfirst_name  VARCHAR2(100),
  br_act_fadlast_name   VARCHAR2(100),
  br_act_fadmiddle_name VARCHAR2(100),
  br_act_fadlocation    VARCHAR2(200),
  br_act_fadorg_name    VARCHAR2(200),
  br_act_fadreg_adr     VARCHAR2(200),
  br_act_mercer_id      NUMBER,
  br_act_num            VARCHAR2(100),
  br_act_seria          VARCHAR2(100),
  br_act_patcer         NUMBER
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.BRN_BIRTH_ACT
  is 'Записи акта о рождении';
comment on column XXI.BRN_BIRTH_ACT.br_act_id
  is 'ИД акта о рождении';
comment on column XXI.BRN_BIRTH_ACT.br_act_date
  is 'Дата заведения';
comment on column XXI.BRN_BIRTH_ACT.br_act_ztp
  is 'Тип заявителя.F-физ.-J-юр.';
comment on column XXI.BRN_BIRTH_ACT.br_act_brchcnt
  is 'Количество родившихся детей';
comment on column XXI.BRN_BIRTH_ACT.br_act_ld
  is 'Живорожденный или мертворожденный';
comment on column XXI.BRN_BIRTH_ACT.br_act_zgid
  is 'Наименование ЗАГСа';
comment on column XXI.BRN_BIRTH_ACT.br_act_user
  is 'Пользователь';
comment on column XXI.BRN_BIRTH_ACT.br_act_tgrabf
  is 'Тип основании сведении об отце';
comment on column XXI.BRN_BIRTH_ACT.br_act_mzdate
  is 'Дата заявления от матери, если (BR_ACT_TGRABF = V)';
comment on column XXI.BRN_BIRTH_ACT.br_act_dbf
  is 'Документ подтверждающий факт орождении ребенка, тип';
comment on column XXI.BRN_BIRTH_ACT.br_act_ch
  is 'Ссылка на ребенка';
comment on column XXI.BRN_BIRTH_ACT.br_act_f
  is 'Ссылка на отца';
comment on column XXI.BRN_BIRTH_ACT.br_act_m
  is 'Ссылка на мать';
comment on column XXI.BRN_BIRTH_ACT.br_act_medorga
  is 'Наименование мед. орг. выд. документ (A-Док. уст. формы)';
comment on column XXI.BRN_BIRTH_ACT.br_act_datedoca
  is 'Дата документа  (A-Док. уст. формы)';
comment on column XXI.BRN_BIRTH_ACT.br_act_ndoca
  is 'Номер документа  (A-Док. уст. формы)';
comment on column XXI.BRN_BIRTH_ACT.br_act_fiob
  is 'ФИО лица присутствовавшего во время родов (Б-Заявление)';
comment on column XXI.BRN_BIRTH_ACT.br_act_datedocb
  is 'Дата документа  (Б-Заявление)';
comment on column XXI.BRN_BIRTH_ACT.br_act_namecourt
  is 'Наимнование суда';
comment on column XXI.BRN_BIRTH_ACT.br_act_desccourt
  is 'Решение суда №';
comment on column XXI.BRN_BIRTH_ACT.br_act_dcourt
  is 'Дата решения суда';
comment on column XXI.BRN_BIRTH_ACT.br_act_fadfirst_name
  is 'Имя (Заявитель о рождении)';
comment on column XXI.BRN_BIRTH_ACT.br_act_fadlast_name
  is 'Фамилия (Заявитель о рождении)';
comment on column XXI.BRN_BIRTH_ACT.br_act_fadmiddle_name
  is 'Отчество (Заявитель о рождении)';
comment on column XXI.BRN_BIRTH_ACT.br_act_fadlocation
  is 'Место жительства (Заявитель о рождении)';
comment on column XXI.BRN_BIRTH_ACT.br_act_fadorg_name
  is 'Наименование организации (Заявитель о рождении)';
comment on column XXI.BRN_BIRTH_ACT.br_act_fadreg_adr
  is 'Адрес регистрации (Заявитель о рождении)';
comment on column XXI.BRN_BIRTH_ACT.br_act_mercer_id
  is 'Ссылка на свидетельство о заключении брака ';
comment on column XXI.BRN_BIRTH_ACT.br_act_num
  is 'Номер (печать ЗАГСа)';
comment on column XXI.BRN_BIRTH_ACT.br_act_seria
  is 'Серия (печать ЗАГСа)';
comment on column XXI.BRN_BIRTH_ACT.br_act_patcer
  is 'Ссылка на установл. отц';
create index XXI.I_BR_ACT_BRCHCNT on XXI.BRN_BIRTH_ACT (BR_ACT_BRCHCNT)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_BR_ACT_CH on XXI.BRN_BIRTH_ACT (BR_ACT_CH)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_BR_ACT_DATE on XXI.BRN_BIRTH_ACT (BR_ACT_DATE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_BR_ACT_DBF on XXI.BRN_BIRTH_ACT (BR_ACT_DBF)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_BR_ACT_F on XXI.BRN_BIRTH_ACT (BR_ACT_F)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_BR_ACT_M on XXI.BRN_BIRTH_ACT (BR_ACT_M)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_BR_ACT_MERCER_ID on XXI.BRN_BIRTH_ACT (BR_ACT_MERCER_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_BR_ACT_PATCER on XXI.BRN_BIRTH_ACT (BR_ACT_PATCER)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_BR_ACT_TGRABF on XXI.BRN_BIRTH_ACT (BR_ACT_TGRABF)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_BR_ACT_USER on XXI.BRN_BIRTH_ACT (BR_ACT_USER)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_BR_ACT_ZGID on XXI.BRN_BIRTH_ACT (BR_ACT_ZGID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_BR_ACT_ZTP on XXI.BRN_BIRTH_ACT (BR_ACT_ZTP)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.BRN_BIRTH_ACT
  add constraint PK_BR_ACT_ID primary key (BR_ACT_ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.BRN_BIRTH_ACT
  add constraint FK_BR_ACT_MERCER_ID foreign key (BR_ACT_MERCER_ID)
  references XXI.MC_MERCER (MERCER_ID) on delete cascade
  disable
  novalidate;
alter table XXI.BRN_BIRTH_ACT
  add constraint FK_BR_ACT_PATCER foreign key (BR_ACT_PATCER)
  references XXI.PATERN_CERT (PC_ID) on delete cascade
  disable
  novalidate;
alter table XXI.BRN_BIRTH_ACT
  add constraint FK_BR_ACT_ZGID foreign key (BR_ACT_ZGID)
  references XXI.ZAGS (ZAGS_ID);
alter table XXI.BRN_BIRTH_ACT
  add constraint CH_BR_ACT_DBF
  check (BR_ACT_DBF in ('A','B'));
alter table XXI.BRN_BIRTH_ACT
  add constraint CH_BR_ACT_LD
  check (BR_ACT_LD in ('L','D'));
alter table XXI.BRN_BIRTH_ACT
  add constraint CH_BR_ACT_TGRABF
  check (BR_ACT_TGRABF in ('A','B','V'));
alter table XXI.BRN_BIRTH_ACT
  add constraint CH_BR_ACT_TYPE
  check (BR_ACT_ZTP in ('F','J'));
grant select, insert, update, delete on XXI.BRN_BIRTH_ACT to ODB;

prompt
prompt Creating table PATERN_CERT
prompt ==========================
prompt
create table XXI.PATERN_CERT
(
  pc_id        NUMBER not null,
  pc_act_id    NUMBER,
  pс_aft_lname VARCHAR2(100),
  pс_aft_fname VARCHAR2(100),
  pс_aft_mname VARCHAR2(100),
  pс_ch        NUMBER,
  pс_f         NUMBER,
  pс_m         NUMBER,
  pс_type      VARCHAR2(2),
  pс_trz       DATE,
  pс_fz        DATE,
  pс_crname    VARCHAR2(200),
  pс_crdate    DATE,
  pс_seria     VARCHAR2(100),
  pс_number    VARCHAR2(100),
  pс_date      DATE default sysdate not null,
  pс_user      VARCHAR2(100) default user not null,
  pс_zags      NUMBER,
  pc_zmname    VARCHAR2(100),
  pc_zfname    VARCHAR2(100),
  pc_zlname    VARCHAR2(100),
  pc_zplace    VARCHAR2(100)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.PATERN_CERT
  is 'Установление отцовства';
comment on column XXI.PATERN_CERT.pc_id
  is 'ID';
comment on column XXI.PATERN_CERT.pc_act_id
  is 'Ссылка на свидетельство о рождении';
comment on column XXI.PATERN_CERT.pс_aft_lname
  is 'Фамилия после уст. отц';
comment on column XXI.PATERN_CERT.pс_aft_fname
  is 'Имя после уст. отц';
comment on column XXI.PATERN_CERT.pс_aft_mname
  is 'Отчество после уст. отц';
comment on column XXI.PATERN_CERT.pс_ch
  is 'Ссылка на ребенка';
comment on column XXI.PATERN_CERT.pс_f
  is 'Ссылка на отца';
comment on column XXI.PATERN_CERT.pс_m
  is 'Ссылка на мать';
comment on column XXI.PATERN_CERT.pс_type
  is 'Тип основания для уст. отцовства';
comment on column XXI.PATERN_CERT.pс_trz
  is 'A-Совмест. заявл. род-дата';
comment on column XXI.PATERN_CERT.pс_fz
  is 'B-заявление отца ребенка -дата';
comment on column XXI.PATERN_CERT.pс_crname
  is 'V-решение суда - наименование';
comment on column XXI.PATERN_CERT.pс_crdate
  is 'V-решение суда - дата';
comment on column XXI.PATERN_CERT.pс_seria
  is 'Выдано свид. серия';
comment on column XXI.PATERN_CERT.pс_number
  is 'Выдано свид. номер';
comment on column XXI.PATERN_CERT.pс_date
  is 'Дата документа';
comment on column XXI.PATERN_CERT.pс_user
  is 'Юзер';
comment on column XXI.PATERN_CERT.pс_zags
  is 'Загс';
comment on column XXI.PATERN_CERT.pc_zmname
  is 'Отчество заявителя';
comment on column XXI.PATERN_CERT.pc_zfname
  is 'Имя заявителя';
comment on column XXI.PATERN_CERT.pc_zlname
  is 'Фамилия заявителя';
comment on column XXI.PATERN_CERT.pc_zplace
  is 'Место жительства заявителя';
create index XXI.I_PC_ACT_ID on XXI.PATERN_CERT (PC_ACT_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_PС_CH on XXI.PATERN_CERT (PС_CH)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_PС_DATE on XXI.PATERN_CERT (PС_DATE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_PС_F on XXI.PATERN_CERT (PС_F)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_PС_M on XXI.PATERN_CERT (PС_M)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_PС_TYPE on XXI.PATERN_CERT (PС_TYPE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_PС_USER on XXI.PATERN_CERT (PС_USER)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.PATERN_CERT
  add constraint PK_PC_ID primary key (PC_ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.PATERN_CERT
  add constraint FK_PC_ACTID foreign key (PC_ACT_ID)
  references XXI.BRN_BIRTH_ACT (BR_ACT_ID) on delete cascade
  disable
  novalidate;
alter table XXI.PATERN_CERT
  add constraint FK_PL_ZAGS foreign key (PС_ZAGS)
  references XXI.ZAGS (ZAGS_ID);
alter table XXI.PATERN_CERT
  add constraint CH_PL_TYPE
  check (PС_TYPE in ('A','B','V1','V2'));
grant select, insert, update, delete on XXI.PATERN_CERT to ODB;

prompt
prompt Creating table ADOPTOIN
prompt =======================
prompt
create table XXI.ADOPTOIN
(
  id                 NUMBER not null,
  zags_id            NUMBER,
  old_lastname       VARCHAR2(100),
  old_firstname      VARCHAR2(100),
  old_middlname      VARCHAR2(100),
  new_lastname       VARCHAR2(100),
  new_firstname      VARCHAR2(100),
  new_middlname      VARCHAR2(100),
  cusid_ch           NUMBER,
  doc_date           DATE default sysdate not null,
  oper               VARCHAR2(100) default user not null,
  cusid_m            NUMBER,
  cusid_f            NUMBER,
  brnact             NUMBER,
  svid_seria         VARCHAR2(100),
  svid_nomer         VARCHAR2(100),
  cusid_m_ad         NUMBER,
  cusid_f_ad         NUMBER,
  adopt_parents      VARCHAR2(1),
  zap_ispolkom_resh  VARCHAR2(100),
  zap_sovet_dep_trud VARCHAR2(100),
  zap_date           DATE,
  zap_number         VARCHAR2(100),
  new_brth           DATE,
  old_brth           DATE,
  brn_city           VARCHAR2(100),
  brn_area           VARCHAR2(100),
  brn_obl_resp       VARCHAR2(100)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.ADOPTOIN
  is 'Усыновление (удочерение)';
comment on column XXI.ADOPTOIN.id
  is 'ИД';
comment on column XXI.ADOPTOIN.zags_id
  is 'ИД загса';
comment on column XXI.ADOPTOIN.old_lastname
  is 'Фамилия до';
comment on column XXI.ADOPTOIN.old_firstname
  is 'Имя до';
comment on column XXI.ADOPTOIN.old_middlname
  is 'Отчество до';
comment on column XXI.ADOPTOIN.new_lastname
  is 'Фамилия после';
comment on column XXI.ADOPTOIN.new_firstname
  is 'Имя после';
comment on column XXI.ADOPTOIN.new_middlname
  is 'Отчество после';
comment on column XXI.ADOPTOIN.cusid_ch
  is 'ребенок, ссылка на клиента';
comment on column XXI.ADOPTOIN.doc_date
  is 'дата документа';
comment on column XXI.ADOPTOIN.oper
  is 'пользователь';
comment on column XXI.ADOPTOIN.cusid_m
  is 'мать, ссылка на клиента';
comment on column XXI.ADOPTOIN.cusid_f
  is 'отец, ссылка на клиента';
comment on column XXI.ADOPTOIN.brnact
  is 'рождение ребенка';
comment on column XXI.ADOPTOIN.svid_seria
  is 'серия';
comment on column XXI.ADOPTOIN.svid_nomer
  is 'номер';
comment on column XXI.ADOPTOIN.cusid_m_ad
  is 'мать усынов.';
comment on column XXI.ADOPTOIN.cusid_f_ad
  is 'отец усынов.';
comment on column XXI.ADOPTOIN.adopt_parents
  is 'записываются ли усыновители родителями ребенка';
comment on column XXI.ADOPTOIN.zap_ispolkom_resh
  is 'Решение исполкома (осн. записи об усыновл.)';
comment on column XXI.ADOPTOIN.zap_sovet_dep_trud
  is 'Совета депутатов трудящихся (края, республики) (осн. записи об усыновл.)';
comment on column XXI.ADOPTOIN.zap_date
  is 'дата (осн. записи об усыновл.)';
comment on column XXI.ADOPTOIN.zap_number
  is 'номер (осн. записи об усыновл.)';
comment on column XXI.ADOPTOIN.new_brth
  is 'дата рождения после';
comment on column XXI.ADOPTOIN.old_brth
  is 'дата рождения до';
comment on column XXI.ADOPTOIN.brn_city
  is 'Город (селение)';
comment on column XXI.ADOPTOIN.brn_area
  is 'Район';
comment on column XXI.ADOPTOIN.brn_obl_resp
  is 'Область, (край, республика)';
create index XXI.I_ADOPTION_BRNACT on XXI.ADOPTOIN (BRNACT)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_ADOPTION_CUSID_CH on XXI.ADOPTOIN (CUSID_CH)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_ADOPTION_CUSID_F on XXI.ADOPTOIN (CUSID_F)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_ADOPTION_CUSID_F_AD on XXI.ADOPTOIN (CUSID_F_AD)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_ADOPTION_CUSID_M on XXI.ADOPTOIN (CUSID_M)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_ADOPTION_CUSID_M_AD on XXI.ADOPTOIN (CUSID_M_AD)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_ADOPTION_DOC_DATE on XXI.ADOPTOIN (DOC_DATE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_ADOPTION_NEW_BRTH on XXI.ADOPTOIN (NEW_BRTH)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_ADOPTION_NEW_FIRSTNAME on XXI.ADOPTOIN (NEW_FIRSTNAME)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_ADOPTION_NEW_LASTNAME on XXI.ADOPTOIN (NEW_LASTNAME)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_ADOPTION_NEW_MIDDLNAME on XXI.ADOPTOIN (NEW_MIDDLNAME)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_ADOPTION_OLD_BRTH on XXI.ADOPTOIN (OLD_BRTH)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_ADOPTION_OLD_FIRSTNAME on XXI.ADOPTOIN (OLD_FIRSTNAME)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_ADOPTION_OLD_LASTNAME on XXI.ADOPTOIN (OLD_LASTNAME)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_ADOPTION_OLD_MIDDLNAME on XXI.ADOPTOIN (OLD_MIDDLNAME)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_ADOPTION_OPER on XXI.ADOPTOIN (OPER)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_ADOPTION_ZAGS_ID on XXI.ADOPTOIN (ZAGS_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.ADOPTOIN
  add constraint PK_ADOPTOIN_ID primary key (ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.ADOPTOIN
  add constraint FK_ADOPTOIN_BRNACT foreign key (BRNACT)
  references XXI.BRN_BIRTH_ACT (BR_ACT_ID) on delete cascade
  disable
  novalidate;
alter table XXI.ADOPTOIN
  add constraint FK_ADOPTOIN_ZAGS_ID foreign key (ZAGS_ID)
  references XXI.ZAGS (ZAGS_ID);
alter table XXI.ADOPTOIN
  add constraint CH_ADOPTION_ADOPT_PARENTS
  check (ADOPT_PARENTS in ('Y','N'));
grant select, insert, update, delete on XXI.ADOPTOIN to ODB;

prompt
prompt Creating table AP_CURSOR_ROLE
prompt =============================
prompt
create table XXI.AP_CURSOR_ROLE
(
  report_type_id NUMBER(12) not null,
  report_id      NUMBER(12) not null,
  cursor_num     NUMBER(12) not null,
  cursor_id      NUMBER(12) not null,
  use_sort       CHAR(1) not null,
  cursor_loop    NUMBER(12),
  use_reset      CHAR(1),
  use_resume     CHAR(1)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 1M
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.AP_CURSOR_ROLE
  is 'Alt-Print.Привязка курсоров к отчетам. 12.02.2003';
comment on column XXI.AP_CURSOR_ROLE.report_type_id
  is 'Идентификатор типа отчета';
comment on column XXI.AP_CURSOR_ROLE.report_id
  is 'Идентификатор отчета';
comment on column XXI.AP_CURSOR_ROLE.cursor_num
  is 'Номер курсора';
comment on column XXI.AP_CURSOR_ROLE.cursor_id
  is 'ID курсора';
comment on column XXI.AP_CURSOR_ROLE.use_sort
  is 'Использование сортировки
"Y" - использовать
"N" - не использовать';
comment on column XXI.AP_CURSOR_ROLE.cursor_loop
  is 'Номер курсора для цикла';
comment on column XXI.AP_CURSOR_ROLE.use_reset
  is 'Вызывать Reset подвалов и заголовков перед печатью строки';
comment on column XXI.AP_CURSOR_ROLE.use_resume
  is 'Вызывать Resume подвалов и заголовков после печати строки';
create index XXI.I_AP_CRS_ROL_1 on XXI.AP_CURSOR_ROLE (REPORT_TYPE_ID, REPORT_ID, CURSOR_LOOP)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 3M
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_AP_CRS_ROL_2 on XXI.AP_CURSOR_ROLE (CURSOR_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 2M
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_AP_CRS_ROL_3 on XXI.AP_CURSOR_ROLE (REPORT_TYPE_ID, REPORT_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 3M
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.AP_CURSOR_ROLE
  add constraint PK_AP_CRS_ROL primary key (REPORT_TYPE_ID, REPORT_ID, CURSOR_NUM)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 960K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.AP_CURSOR_ROLE
  add constraint UK_AP_CRS_COL unique (REPORT_TYPE_ID, REPORT_ID, CURSOR_NUM, CURSOR_ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 2M
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.AP_CURSOR_ROLE
  add constraint FK_AP_CRS_ROL_2 foreign key (REPORT_TYPE_ID, REPORT_ID, CURSOR_LOOP)
  references XXI.AP_CURSOR_ROLE (REPORT_TYPE_ID, REPORT_ID, CURSOR_NUM);
grant select, insert, update, delete on XXI.AP_CURSOR_ROLE to ODB;

prompt
prompt Creating table AP_CURSOR_TYPE
prompt =============================
prompt
create table XXI.AP_CURSOR_TYPE
(
  cursor_id   NUMBER(12) not null,
  cursor_name VARCHAR2(4000),
  cursor_text CLOB,
  cursor_type CHAR(1) default 'R' not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 18M
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.AP_CURSOR_TYPE
  is 'Альтернативная печать. Список курсоров (5.8.5)(07.04.2010)(Nick-3D)';
comment on column XXI.AP_CURSOR_TYPE.cursor_id
  is 'ID курсора';
comment on column XXI.AP_CURSOR_TYPE.cursor_name
  is 'Наименование курсора';
comment on column XXI.AP_CURSOR_TYPE.cursor_text
  is 'Текст курсора';
comment on column XXI.AP_CURSOR_TYPE.cursor_type
  is 'Тип курсора (R - для отчета,P - для параметров)';
alter table XXI.AP_CURSOR_TYPE
  add constraint PK_AP_CRS_TYP primary key (CURSOR_ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 512K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.AP_CURSOR_TYPE
  add constraint CK_AP_CRS_TYP
  check (cursor_type in ('R','P'));
grant select, insert, update, delete on XXI.AP_CURSOR_TYPE to ODB;

prompt
prompt Creating table AP_REPORT_CAT
prompt ============================
prompt
create table XXI.AP_REPORT_CAT
(
  report_id      NUMBER(12) not null,
  report_type_id NUMBER(12) not null,
  report_name    VARCHAR2(4000),
  report_ufs     VARCHAR2(64),
  copies         NUMBER(3) default 1 not null,
  report_viewer  INTEGER,
  report_comment VARCHAR2(4000),
  edit_param     CHAR(1),
  oem_data       CHAR(1),
  report_file    CLOB,
  available_sql  VARCHAR2(2000)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 704K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.AP_REPORT_CAT
  is 'Альтернативная печать. Список отчетов (6.0.1)(19.11.2016)(Nick-3D)';
comment on column XXI.AP_REPORT_CAT.report_id
  is 'Идентификатор отчета';
comment on column XXI.AP_REPORT_CAT.report_type_id
  is 'Идентификатор типа отчета';
comment on column XXI.AP_REPORT_CAT.report_name
  is 'Наименование отчета';
comment on column XXI.AP_REPORT_CAT.report_ufs
  is 'UFS отчета';
comment on column XXI.AP_REPORT_CAT.copies
  is 'Число копий';
comment on column XXI.AP_REPORT_CAT.report_comment
  is 'Комментарий к отчету';
comment on column XXI.AP_REPORT_CAT.edit_param
  is 'Редактирование параметров отчета';
comment on column XXI.AP_REPORT_CAT.oem_data
  is 'Выгрузка данных в кодировке RU8PC866';
comment on column XXI.AP_REPORT_CAT.report_file
  is 'Название файла, возможен запрос';
comment on column XXI.AP_REPORT_CAT.available_sql
  is 'Возможность выполнения отчета';
create index XXI.IF_AP_REP_CAT_REPORT_VIEWER on XXI.AP_REPORT_CAT (REPORT_VIEWER)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 128K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.AP_REPORT_CAT
  add constraint PK_AP_REP_CAT primary key (REPORT_TYPE_ID, REPORT_ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 256K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete on XXI.AP_REPORT_CAT to ODB;

prompt
prompt Creating table AP_REPORT_CAT_PARAM
prompt ==================================
prompt
create table XXI.AP_REPORT_CAT_PARAM
(
  report_id      NUMBER(12) not null,
  report_type_id NUMBER(12) not null,
  iparamnum      INTEGER not null,
  cparamdescr    VARCHAR2(250),
  cparamdefault  VARCHAR2(250),
  cursor_id      NUMBER(12),
  save           CHAR(1) default 'N' not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 192K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.AP_REPORT_CAT_PARAM
  is 'Альтернативная печать. Параметры отчетов (5.9.9)(06.11.2015)(Nick-3D)';
comment on column XXI.AP_REPORT_CAT_PARAM.cursor_id
  is 'Id курсора';
comment on column XXI.AP_REPORT_CAT_PARAM.save
  is 'Сохранять значение (Y/N)';
create index XXI.IF_AP_REP_CAT_PAR_2_CRS_TYP on XXI.AP_REPORT_CAT_PARAM (CURSOR_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.AP_REPORT_CAT_PARAM
  add constraint PK_AP_REPORT_CAT_PARAM primary key (REPORT_TYPE_ID, REPORT_ID, IPARAMNUM)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 128K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.AP_REPORT_CAT_PARAM
  add constraint FK_AP_REP_CAT_PAR_2_CRS_TYP foreign key (CURSOR_ID)
  references XXI.AP_CURSOR_TYPE (CURSOR_ID);
alter table XXI.AP_REPORT_CAT_PARAM
  add constraint FK_AP_REPORT_CAT_PARAM_1 foreign key (REPORT_TYPE_ID, REPORT_ID)
  references XXI.AP_REPORT_CAT (REPORT_TYPE_ID, REPORT_ID) on delete cascade;
alter table XXI.AP_REPORT_CAT_PARAM
  add constraint CK_AP_REP_CAT_PAR_4_SAV
  check (save in ('Y','N'));
grant select, insert, update, delete on XXI.AP_REPORT_CAT_PARAM to ODB;

prompt
prompt Creating table AP_REPORT_TYPE
prompt =============================
prompt
create table XXI.AP_REPORT_TYPE
(
  report_type_id   NUMBER(12) not null,
  report_type_name VARCHAR2(4000),
  report_cat_sql   CLOB
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 128K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.AP_REPORT_TYPE
  is 'Альтернативная печать. Alt-Print.Список типов отчетов.16.10.2001 (6.0.0)(30.03.2016)(Nick-3D)';
comment on column XXI.AP_REPORT_TYPE.report_type_id
  is 'Идентификатор типа отчета';
comment on column XXI.AP_REPORT_TYPE.report_type_name
  is 'Наименование типа отчета';
comment on column XXI.AP_REPORT_TYPE.report_cat_sql
  is 'PL/SQL определения Id отчета';
alter table XXI.AP_REPORT_TYPE
  add constraint PK_AP_REPORT_TYPE primary key (REPORT_TYPE_ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 128K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete on XXI.AP_REPORT_TYPE to ODB;

prompt
prompt Creating table AP_USER_REPORT_CAT_ROLE
prompt ======================================
prompt
create table XXI.AP_USER_REPORT_CAT_ROLE
(
  user_id        NUMBER(6) not null,
  report_type_id NUMBER(12) not null,
  report_id      NUMBER(12) not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 320K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.AP_USER_REPORT_CAT_ROLE
  is 'Alt-Print.Настройка отчетов на пользователей.16.10.2001';
comment on column XXI.AP_USER_REPORT_CAT_ROLE.user_id
  is 'Идентификатор пользователя';
comment on column XXI.AP_USER_REPORT_CAT_ROLE.report_type_id
  is 'Идентификатор типа отчета';
comment on column XXI.AP_USER_REPORT_CAT_ROLE.report_id
  is 'Идентификатор отчета';
create index XXI.I_AP_USR_REP_CAT_ROL_1 on XXI.AP_USER_REPORT_CAT_ROLE (REPORT_TYPE_ID, REPORT_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 512K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_AP_USR_REP_CAT_ROL_2 on XXI.AP_USER_REPORT_CAT_ROLE (USER_ID, REPORT_TYPE_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 512K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.AP_USER_REPORT_CAT_ROLE
  add constraint PK_AP_USR_RPCT_ROL primary key (USER_ID, REPORT_TYPE_ID, REPORT_ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 512K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.AP_USER_REPORT_CAT_ROLE
  add constraint FK_AP_RPCT_2_USR_RPCT_ROL foreign key (REPORT_TYPE_ID, REPORT_ID)
  references XXI.AP_REPORT_CAT (REPORT_TYPE_ID, REPORT_ID) on delete cascade;
grant select, insert, update, delete on XXI.AP_USER_REPORT_CAT_ROLE to ODB;

prompt
prompt Creating table NOTARY
prompt =====================
prompt
create table XXI.NOTARY
(
  not_id        NUMBER not null,
  not_otd       NUMBER,
  not_name      VARCHAR2(500),
  not_ruk       VARCHAR2(500),
  not_address   VARCHAR2(500),
  not_telephone VARCHAR2(50)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.NOTARY
  is 'Список нотариусов';
comment on column XXI.NOTARY.not_id
  is 'ИД';
comment on column XXI.NOTARY.not_otd
  is 'Город';
comment on column XXI.NOTARY.not_name
  is 'Наименование ЗАГСа';
comment on column XXI.NOTARY.not_ruk
  is 'Руководитель';
comment on column XXI.NOTARY.not_address
  is 'Адрес';
comment on column XXI.NOTARY.not_telephone
  is 'Телефон';
alter table XXI.NOTARY
  add constraint PK_NOTARY primary key (NOT_ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.NOTARY
  add constraint PK_NOTARY_OTD foreign key (NOT_OTD)
  references XXI.OTD (IOTDNUM);
grant select, insert, update, delete on XXI.NOTARY to ODB;

prompt
prompt Creating table USR
prompt ==================
prompt
create table XXI.USR
(
  iusrid               NUMBER(6) not null,
  cusrlogname          VARCHAR2(30) not null,
  cusrname             VARCHAR2(64) not null,
  cusrposition         VARCHAR2(150),
  dusrhire             DATE default TRUNC(SysDate) not null,
  iusrbranch           NUMBER(4),
  dusrfire             DATE,
  iusrpwd_length       NUMBER(2),
  iusrchr_quantity     NUMBER(2),
  iusrnum_quantity     NUMBER(2),
  iusrexp_days         NUMBER(3),
  cusroffphone         VARCHAR2(36),
  twrtstart            DATE,
  twrtend              DATE,
  cemail               VARCHAR2(200),
  crestrict_term       CHAR(1) default 'N' not null,
  iusrpwdreuse         NUMBER(3) default '0',
  iusrspec_quantity    NUMBER(2),
  welcome_message      VARCHAR2(4000),
  short_name           VARCHAR2(50),
  lock_date_time       DATE,
  lock_info            VARCHAR2(2000),
  must_change_password CHAR(1) default 'N',
  short_position       VARCHAR2(150),
  workday_time_end     DATE,
  workday_time_begin   DATE,
  zags_id              NUMBER,
  notary_id            NUMBER,
  access_level         VARCHAR2(3),
  fio_sh               VARCHAR2(100),
  fio_abh_sh           VARCHAR2(100),
  fio_abh              VARCHAR2(100)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 80K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.USR
  is 'Права доступа. Информация о пользователях';
comment on column XXI.USR.iusrid
  is 'Идентификатор пользователя';
comment on column XXI.USR.cusrlogname
  is 'LogName';
comment on column XXI.USR.cusrname
  is 'ФИО';
comment on column XXI.USR.cusrposition
  is 'Должность';
comment on column XXI.USR.dusrhire
  is 'Дата поступления на работу';
comment on column XXI.USR.iusrbranch
  is 'Филиал  ( ссылка на iOTDnum )';
comment on column XXI.USR.dusrfire
  is 'Дата увольнения';
comment on column XXI.USR.iusrpwd_length
  is 'Минимальная длина пароля';
comment on column XXI.USR.iusrchr_quantity
  is 'Минимальное количество буквенных символов в пароле';
comment on column XXI.USR.iusrnum_quantity
  is 'Минимальное количество цифровых символов в пароле';
comment on column XXI.USR.iusrexp_days
  is 'Срок действия пароля в днях';
comment on column XXI.USR.cusroffphone
  is 'Номер внутреннего телефона';
comment on column XXI.USR.twrtstart
  is 'Начало разрешения коннекта';
comment on column XXI.USR.twrtend
  is 'Окончание разрешения коннекта';
comment on column XXI.USR.cemail
  is 'Адрес электронной почты';
comment on column XXI.USR.crestrict_term
  is 'Y если пользователь может открывать сессии только с одного терминала';
comment on column XXI.USR.iusrpwdreuse
  is 'Использовать старый пароль после N новых';
comment on column XXI.USR.iusrspec_quantity
  is 'Минимальное количество специальных символов в пароле';
comment on column XXI.USR.welcome_message
  is 'Приветственное сообщение';
comment on column XXI.USR.short_name
  is 'Сокращенное ФИО';
comment on column XXI.USR.lock_date_time
  is 'Дата/время блокировки';
comment on column XXI.USR.lock_info
  is 'Информация блокировки';
comment on column XXI.USR.must_change_password
  is 'Пользователь должен сменить пароль при входе (Y/N)';
comment on column XXI.USR.short_position
  is 'Сокращенная должность';
comment on column XXI.USR.workday_time_end
  is 'Окончание рабочего времени';
comment on column XXI.USR.workday_time_begin
  is 'Начало рабочего времени';
comment on column XXI.USR.zags_id
  is 'ID ЗАГСА';
comment on column XXI.USR.notary_id
  is 'ID Нотариуса';
comment on column XXI.USR.access_level
  is 'Учреждение, NOT, ZAG,ADM';
comment on column XXI.USR.fio_sh
  is 'ФИО короткое';
comment on column XXI.USR.fio_abh_sh
  is 'ФИО короткое на Абх';
comment on column XXI.USR.fio_abh
  is 'ФИО на абх';
create index XXI.I_USR_ACCESS_LEVEL on XXI.USR (ACCESS_LEVEL)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_USR_IUSRBRANCH on XXI.USR (IUSRBRANCH)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_USR_NOTARY_ID on XXI.USR (NOTARY_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_USR_ZAGS_ID on XXI.USR (ZAGS_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.USR
  add constraint P_USR_ID primary key (IUSRID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.USR
  add constraint U_USR_LOGNAME unique (CUSRLOGNAME)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.USR
  add constraint FK_IUSRBRANCH foreign key (IUSRBRANCH)
  references XXI.OTD (IOTDNUM);
alter table XXI.USR
  add constraint FK_USR_NOTARY foreign key (NOTARY_ID)
  references XXI.NOTARY (NOT_ID);
alter table XXI.USR
  add constraint FK_ZAGS_ID foreign key (ZAGS_ID)
  references XXI.ZAGS (ZAGS_ID);
alter table XXI.USR
  add constraint CH_USR_ACCESS_LEVEL
  check (ACCESS_LEVEL in ('NOT','ZAG','ADM'));
alter table XXI.USR
  add constraint CK_USR_4_MST_CHN_PSW
  check (must_change_password in ('Y','N'));
grant select, insert, update, delete on XXI.USR to ODB;

prompt
prompt Creating table AP_USER_REPORT_ROLE
prompt ==================================
prompt
create table XXI.AP_USER_REPORT_ROLE
(
  user_id        NUMBER(6) not null,
  report_type_id NUMBER(12) not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 256K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.AP_USER_REPORT_ROLE
  is 'Alt-Print.Привязка типов отчетов к пользователям.16.10.2001';
comment on column XXI.AP_USER_REPORT_ROLE.user_id
  is 'Идентификатор пользователя';
comment on column XXI.AP_USER_REPORT_ROLE.report_type_id
  is 'Идентификатор типа отчета';
create index XXI.I_AP_USR_REP_ROL_1 on XXI.AP_USER_REPORT_ROLE (REPORT_TYPE_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 320K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_AP_USR_REP_ROL_2 on XXI.AP_USER_REPORT_ROLE (USER_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 320K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.AP_USER_REPORT_ROLE
  add constraint PK_AP_USR_REP_ROL primary key (USER_ID, REPORT_TYPE_ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 320K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.AP_USER_REPORT_ROLE
  add constraint FK_AP_REP_TYP_2_USR_REP_ROL foreign key (REPORT_TYPE_ID)
  references XXI.AP_REPORT_TYPE (REPORT_TYPE_ID) on delete cascade;
alter table XXI.AP_USER_REPORT_ROLE
  add constraint FK_USR_2_AP_USR_REP_ROL foreign key (USER_ID)
  references XXI.USR (IUSRID) on delete cascade;
grant select, insert, update, delete on XXI.AP_USER_REPORT_ROLE to ODB;

prompt
prompt Creating table AP_VIEWER
prompt ========================
prompt
create table XXI.AP_VIEWER
(
  id           INTEGER not null,
  cname        VARCHAR2(50) not null,
  chostcommand VARCHAR2(250)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 80K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.AP_VIEWER
  is 'AltPrint. Внешние программы. Не очищать!!!';
comment on column XXI.AP_VIEWER.id
  is 'Идентификатор. < 0 мои, > 0 -- банков';
comment on column XXI.AP_VIEWER.cname
  is 'Названия программы';
comment on column XXI.AP_VIEWER.chostcommand
  is 'Если не пусто, то команда OS, причем возможны макроподстановки:
%f - имя вых файла, %c - строка коннекта, %p1..%p10 - параметры отчета
%n - имя шаблона, %N - имя шаблона с полным путем
%s - параметр Report_Silent';
alter table XXI.AP_VIEWER
  add constraint PK_AP_VIEWER primary key (ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete on XXI.AP_VIEWER to ODB;

prompt
prompt Creating table AU_ACTION
prompt ========================
prompt
create table XXI.AU_ACTION
(
  iaction_id     NUMBER(12) not null,
  dauddate       DATE default SYSDATE,
  cauduser       VARCHAR2(32) default USER,
  ctable         VARCHAR2(64),
  caudmachine    VARCHAR2(64),
  caudprogram    VARCHAR2(64),
  caudoperation  CHAR(1),
  rrowid         ROWID,
  caudaction     VARCHAR2(48),
  caudmodule     VARCHAR2(48),
  iaudsession    INTEGER default SYS_CONTEXT ('USERENV','SESSIONID'),
  caudip_address VARCHAR2(30) default SYS_CONTEXT ('USERENV', 'IP_ADDRESS'),
  id_num         VARCHAR2(250),
  id_anum        VARCHAR2(250)
)
tablespace TS_AUDIT
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.AU_ACTION
  is 'Аудит справочников -- действия пользователя';
comment on column XXI.AU_ACTION.dauddate
  is 'Системная дата';
comment on column XXI.AU_ACTION.cauduser
  is 'Log-Name пользователя';
comment on column XXI.AU_ACTION.ctable
  is 'Имя аудирумоей таблицы';
comment on column XXI.AU_ACTION.caudmachine
  is 'Компьютер пользователя';
comment on column XXI.AU_ACTION.caudprogram
  is 'Программа пользователя';
comment on column XXI.AU_ACTION.caudoperation
  is 'I вставка, D удаление, U изменение';
comment on column XXI.AU_ACTION.rrowid
  is 'Идентификатор изменяемой записи';
comment on column XXI.AU_ACTION.caudaction
  is 'Название формы (если есть)';
comment on column XXI.AU_ACTION.caudmodule
  is 'Название меню';
create index XXI.I_AU_ACTION_TABLE on XXI.AU_ACTION (CTABLE, ID_NUM, ID_ANUM)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_AU_ACTION_TABLE_DATE on XXI.AU_ACTION (CTABLE, DAUDDATE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_AU_ACTION_USER_DATE on XXI.AU_ACTION (CAUDUSER, DAUDDATE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.AU_ACTION
  add constraint P_AU_DATA primary key (IACTION_ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete on XXI.AU_ACTION to ODB;

prompt
prompt Creating table AU_BLOB_DATA
prompt ===========================
prompt
create table XXI.AU_BLOB_DATA
(
  iaction_id NUMBER(12) not null,
  cfield     VARCHAR2(40) not null,
  bolddata   BLOB
)
tablespace TS_AUDIT
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.AU_BLOB_DATA
  add constraint PK_AU_BLOB_DATA primary key (IACTION_ID, CFIELD)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete on XXI.AU_BLOB_DATA to ODB;

prompt
prompt Creating table AU_CLOB_DATA
prompt ===========================
prompt
create table XXI.AU_CLOB_DATA
(
  iaction_id NUMBER(12) not null,
  cfield     VARCHAR2(40) not null,
  colddata   CLOB
)
tablespace TS_AUDIT
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 80K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.AU_CLOB_DATA
  add constraint PK_AU_CLOB_DATA primary key (IACTION_ID, CFIELD)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 80K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete on XXI.AU_CLOB_DATA to ODB;

prompt
prompt Creating table AU_DATA
prompt ======================
prompt
create table XXI.AU_DATA
(
  iaction_id NUMBER(12),
  cfield     VARCHAR2(40),
  cnewdata   VARCHAR2(2000),
  colddata   VARCHAR2(2000)
)
tablespace TS_AUDIT
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.AU_DATA
  is 'Содержимое аудита справочников';
comment on column XXI.AU_DATA.iaction_id
  is 'Ссылка на аудит действий';
comment on column XXI.AU_DATA.cfield
  is 'Имя аудируемого поля';
comment on column XXI.AU_DATA.cnewdata
  is 'Старые данные поля';
create index XXI.IF_AU_DATA_ACTION on XXI.AU_DATA (IACTION_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete on XXI.AU_DATA to ODB;

prompt
prompt Creating table AU_LOG
prompt =====================
prompt
create table XXI.AU_LOG
(
  iaction_id INTEGER not null,
  iorder     INTEGER not null,
  cobj       VARCHAR2(30),
  ctext      VARCHAR2(2000)
)
tablespace TS_AUDIT
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.AU_LOG
  is 'Логирование бизнес действий, поддержка в AUP_UTIL';
alter table XXI.AU_LOG
  add constraint PK_AU_LOG primary key (IACTION_ID, IORDER)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter index XXI.PK_AU_LOG nologging;
grant select, insert, update, delete on XXI.AU_LOG to ODB;

prompt
prompt Creating table AU_TABLES
prompt ========================
prompt
create table XXI.AU_TABLES
(
  cname VARCHAR2(40) not null,
  cmode CHAR(1)
)
tablespace TS_AUDIT
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 80K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.AU_TABLES
  is 'Справочники, подвергаемые аудиту';
comment on column XXI.AU_TABLES.cname
  is 'Имя таблицы справочника';
comment on column XXI.AU_TABLES.cmode
  is 'Включен (Y)/выключен';
alter table XXI.AU_TABLES
  add constraint P_AU_TABLES primary key (CNAME)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete on XXI.AU_TABLES to ODB;

prompt
prompt Creating table COUNTRIES
prompt ========================
prompt
create table XXI.COUNTRIES
(
  code          NUMBER not null,
  name          VARCHAR2(100),
  name_full     VARCHAR2(100),
  name_abh      VARCHAR2(100),
  name_full_rod VARCHAR2(100),
  ussr          VARCHAR2(100),
  abh           VARCHAR2(100)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.COUNTRIES
  is 'Справочник стран';
comment on column XXI.COUNTRIES.code
  is 'Код';
comment on column XXI.COUNTRIES.name
  is 'Название';
comment on column XXI.COUNTRIES.name_full
  is 'полное название';
comment on column XXI.COUNTRIES.name_abh
  is 'Название на абх';
comment on column XXI.COUNTRIES.name_full_rod
  is 'Название в родительном падеже';
comment on column XXI.COUNTRIES.ussr
  is 'USSR';
alter table XXI.COUNTRIES
  add constraint PK_COUNTRIES_CODE primary key (CODE)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.COUNTRIES
  add constraint UK_COUNTRIES_NAME unique (NAME)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete on XXI.COUNTRIES to ODB;

prompt
prompt Creating table GENDER
prompt =====================
prompt
create table XXI.GENDER
(
  id  NUMBER not null,
  sex VARCHAR2(20) not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.GENDER
  is 'Пол';
comment on column XXI.GENDER.id
  is 'ID';
comment on column XXI.GENDER.sex
  is 'Пол';
create index XXI.GENDER_TYPE on XXI.GENDER (SEX)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.GENDER
  add constraint PK_GENDER_ID primary key (ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.GENDER
  add constraint UK_SEX unique (SEX);
grant select, insert, update, delete on XXI.GENDER to ODB;

prompt
prompt Creating table NATIONALITY
prompt ==========================
prompt
create table XXI.NATIONALITY
(
  id     NUMBER not null,
  name   VARCHAR2(100) not null,
  he     VARCHAR2(100),
  she    VARCHAR2(100),
  he_ab  VARCHAR2(100),
  she_ab VARCHAR2(100)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.NATIONALITY
  is 'Национальности';
comment on column XXI.NATIONALITY.id
  is 'ID';
comment on column XXI.NATIONALITY.name
  is 'Название';
comment on column XXI.NATIONALITY.he
  is 'Название- М';
comment on column XXI.NATIONALITY.she
  is 'Название- Ж';
comment on column XXI.NATIONALITY.he_ab
  is 'Название- М на абх';
comment on column XXI.NATIONALITY.she_ab
  is 'Название- Ж на абх';
alter table XXI.NATIONALITY
  add constraint NATIONALITY_ID primary key (ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.NATIONALITY
  add constraint SHENAME_UNIC unique (NAME)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete on XXI.NATIONALITY to ODB;

prompt
prompt Creating table CUS
prompt ==================
prompt
create table XXI.CUS
(
  icusnum         NUMBER(12) not null,
  dcusopen        DATE default sysdate not null,
  dcusedit        DATE,
  ccusidopen      VARCHAR2(30) default user not null,
  ccusname        VARCHAR2(500) not null,
  ccuscountry1    VARCHAR2(100),
  ccusname_sh     VARCHAR2(250) not null,
  dcusbirthday    DATE not null,
  ccuslast_name   VARCHAR2(100) not null,
  ccusfirst_name  VARCHAR2(30) not null,
  ccusmiddle_name VARCHAR2(30) not null,
  ccussex         NUMBER not null,
  ccusplace_birth VARCHAR2(250) not null,
  icusotd         NUMBER not null,
  ccus_ok_sm      NUMBER not null,
  ccusnationality NUMBER not null,
  id1c            NUMBER,
  ab_first_name   VARCHAR2(100),
  ab_middle_name  VARCHAR2(100),
  ab_last_name    VARCHAR2(100),
  ab_place_birth  VARCHAR2(250)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 2M
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.CUS
  is 'Каталог граждан';
comment on column XXI.CUS.icusnum
  is 'Уникальный номер клиента';
comment on column XXI.CUS.dcusopen
  is 'Дата заведения';
comment on column XXI.CUS.dcusedit
  is 'Дата окончания срока действия документов';
comment on column XXI.CUS.ccusidopen
  is 'Кто завел';
comment on column XXI.CUS.ccusname
  is 'Название';
comment on column XXI.CUS.ccuscountry1
  is 'Страна местонахождения/Гражданство';
comment on column XXI.CUS.ccusname_sh
  is 'Краткое наименование клиента';
comment on column XXI.CUS.dcusbirthday
  is 'Дата рождения (для физ.лиц)';
comment on column XXI.CUS.ccuslast_name
  is 'Фамилия';
comment on column XXI.CUS.ccusfirst_name
  is 'Имя';
comment on column XXI.CUS.ccusmiddle_name
  is 'Отчество';
comment on column XXI.CUS.ccussex
  is 'Пол, 1=Мужской, 2=Женский';
comment on column XXI.CUS.ccusplace_birth
  is 'Место рождения';
comment on column XXI.CUS.icusotd
  is 'ссылка на номер отделения';
comment on column XXI.CUS.ccus_ok_sm
  is 'Код страны рождения';
comment on column XXI.CUS.ccusnationality
  is 'Национальность';
comment on column XXI.CUS.id1c
  is 'id 1c';
comment on column XXI.CUS.ab_first_name
  is 'Имя Абх';
comment on column XXI.CUS.ab_middle_name
  is 'Отчество Абх';
comment on column XXI.CUS.ab_last_name
  is 'Фамилия Абх';
comment on column XXI.CUS.ab_place_birth
  is 'Место рождения Абх';
create index XXI.I_CUS_BIRTHDAY on XXI.CUS (DCUSBIRTHDAY)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_CUS_COUNTRY1 on XXI.CUS (CCUSCOUNTRY1)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_CUS_FIRST_NAME on XXI.CUS (CCUSFIRST_NAME)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_CUS_IDOPEN on XXI.CUS (CCUSIDOPEN)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_CUS_ID1C on XXI.CUS (ID1C)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_CUS_LASTNAME on XXI.CUS (CCUSLAST_NAME)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_CUS_MIDDLE_NAME on XXI.CUS (CCUSMIDDLE_NAME)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_CUS_NAME on XXI.CUS (CCUSNAME)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_CUS_NAME_SH on XXI.CUS (CCUSNAME_SH)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_CUS_NATIONALITY on XXI.CUS (CCUSNATIONALITY)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_CUS_OK_SM on XXI.CUS (CCUS_OK_SM)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_CUS_OPEN on XXI.CUS (DCUSOPEN)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 320K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_CUS_OTD on XXI.CUS (ICUSOTD)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_CUS_SEX on XXI.CUS (CCUSSEX)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.CUS
  add constraint P_ICUSNUM primary key (ICUSNUM)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.CUS
  add constraint F_ICUSSEX foreign key (CCUSSEX)
  references XXI.GENDER (ID);
alter table XXI.CUS
  add constraint FK_CCUSNATIONALITY foreign key (CCUSNATIONALITY)
  references XXI.NATIONALITY (ID);
alter table XXI.CUS
  add constraint FK_CUS_COUNTRIES foreign key (CCUS_OK_SM)
  references XXI.COUNTRIES (CODE);
alter table XXI.CUS
  add constraint CH_CUS_CCUSSEX
  check (CCUSSEX in (1,2));
grant select, insert, update, delete on XXI.CUS to ODB;

prompt
prompt Creating table CUS_ADDR
prompt =======================
prompt
create table XXI.CUS_ADDR
(
  id_addr        NUMBER(12) not null,
  icusnum        NUMBER(12) not null,
  addr_type      NUMBER(1) not null,
  code           VARCHAR2(20),
  country        NUMBER,
  post_index     VARCHAR2(10),
  reg_num        NUMBER(2),
  area           VARCHAR2(256),
  reg_name       VARCHAR2(51),
  city           VARCHAR2(100),
  punct_name     VARCHAR2(100),
  city_type      VARCHAR2(10),
  area_type      VARCHAR2(10),
  infr_name      VARCHAR2(100),
  dom            VARCHAR2(20),
  punct_type     VARCHAR2(10),
  korp           VARCHAR2(20),
  stroy          VARCHAR2(10),
  infr_type      VARCHAR2(10),
  kv             VARCHAR2(10),
  office         VARCHAR2(60),
  porch          VARCHAR2(10),
  oksm_code      VARCHAR2(5),
  address_inline VARCHAR2(210),
  stroy_type     NUMBER
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 80K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.CUS_ADDR
  is 'Адреса клиентов';
comment on column XXI.CUS_ADDR.id_addr
  is 'ID адреса';
comment on column XXI.CUS_ADDR.icusnum
  is 'Идентификатор контрагента';
comment on column XXI.CUS_ADDR.addr_type
  is 'Тип адреса';
comment on column XXI.CUS_ADDR.code
  is 'Код адреса';
comment on column XXI.CUS_ADDR.country
  is 'Страна';
comment on column XXI.CUS_ADDR.post_index
  is 'Почтовый индекс';
comment on column XXI.CUS_ADDR.reg_num
  is 'Номер региона';
comment on column XXI.CUS_ADDR.area
  is 'Район';
comment on column XXI.CUS_ADDR.reg_name
  is 'Наименование региона';
comment on column XXI.CUS_ADDR.city
  is 'Город';
comment on column XXI.CUS_ADDR.punct_name
  is 'Нас. пункт';
comment on column XXI.CUS_ADDR.city_type
  is 'Тип города';
comment on column XXI.CUS_ADDR.area_type
  is 'Тип района';
comment on column XXI.CUS_ADDR.infr_name
  is 'Инфраструктура';
comment on column XXI.CUS_ADDR.dom
  is 'Дом';
comment on column XXI.CUS_ADDR.punct_type
  is 'Тип НП';
comment on column XXI.CUS_ADDR.korp
  is 'Корпус';
comment on column XXI.CUS_ADDR.stroy
  is 'Строение';
comment on column XXI.CUS_ADDR.infr_type
  is 'Тип инфраструктуры';
comment on column XXI.CUS_ADDR.kv
  is 'Квартира';
comment on column XXI.CUS_ADDR.office
  is 'Офис';
comment on column XXI.CUS_ADDR.porch
  is 'Подъезд';
comment on column XXI.CUS_ADDR.oksm_code
  is 'Код ОКСМ территории';
comment on column XXI.CUS_ADDR.address_inline
  is 'Адрес для нерезидентов(не по КЛАДР) в одну строку.';
comment on column XXI.CUS_ADDR.stroy_type
  is 'Признак строения';
create index XXI.IF_CUS_ADDR_LIST on XXI.CUS_ADDR (ICUSNUM)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.CUS_ADDR
  add constraint PK_CUS_ADDR primary key (ID_ADDR)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.CUS_ADDR
  add constraint UC_CUS_ADDR unique (ICUSNUM, ADDR_TYPE)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.CUS_ADDR
  add constraint FK_CUS_ADDR_ICUSNUM foreign key (ICUSNUM)
  references XXI.CUS (ICUSNUM) on delete cascade;
grant select, insert, update, delete on XXI.CUS_ADDR to ODB;

prompt
prompt Creating table CUS_CITIZEN
prompt ==========================
prompt
create table XXI.CUS_CITIZEN
(
  id           NUMBER not null,
  country_code NUMBER not null,
  country_name VARCHAR2(100) not null,
  icusnum      NUMBER not null,
  osn          VARCHAR2(1) not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.CUS_CITIZEN
  is 'Список гражданств';
comment on column XXI.CUS_CITIZEN.id
  is 'ID';
comment on column XXI.CUS_CITIZEN.country_code
  is 'Страна';
comment on column XXI.CUS_CITIZEN.country_name
  is 'Полное наименование';
comment on column XXI.CUS_CITIZEN.icusnum
  is 'Ссылка на гражданина';
comment on column XXI.CUS_CITIZEN.osn
  is 'Основной';
create index XXI.I_CUS_CITIZEN_ on XXI.CUS_CITIZEN (ICUSNUM)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_CUS_CITIZEN_COUNTRY_NAME on XXI.CUS_CITIZEN (COUNTRY_NAME)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_CUS_CITIZEN_OSN on XXI.CUS_CITIZEN (OSN)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.CUS_CITIZEN
  add constraint PK_CUS_CITIZEN_ID primary key (ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.CUS_CITIZEN
  add constraint UK_CUS_CITIZEN_COUNTRY_CODE unique (COUNTRY_CODE, ICUSNUM)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.CUS_CITIZEN
  add constraint FK_CUS_CITIZEN_ICUSNUM foreign key (ICUSNUM)
  references XXI.CUS (ICUSNUM) on delete cascade;
alter table XXI.CUS_CITIZEN
  add constraint CH_OSN
  check (OSN in ('Y','N'));
grant select, insert, update, delete on XXI.CUS_CITIZEN to ODB;

prompt
prompt Creating table CUS_CITIZEN_TEMP
prompt ===============================
prompt
create global temporary table XXI.CUS_CITIZEN_TEMP
(
  id           NUMBER not null,
  country_code VARCHAR2(3) not null,
  country_name VARCHAR2(100) not null,
  osn          VARCHAR2(1) default 'Y' not null
)
on commit preserve rows;
comment on table XXI.CUS_CITIZEN_TEMP
  is 'Список гражданств (врем. припервом создании)';
comment on column XXI.CUS_CITIZEN_TEMP.id
  is 'ID';
comment on column XXI.CUS_CITIZEN_TEMP.country_code
  is 'Страна';
comment on column XXI.CUS_CITIZEN_TEMP.country_name
  is 'Полное наименование';
comment on column XXI.CUS_CITIZEN_TEMP.osn
  is 'Основной';
create index XXI.UK_OSTTEMP on XXI.CUS_CITIZEN_TEMP (OSN);
alter table XXI.CUS_CITIZEN_TEMP
  add constraint PK_CUS_CITIZEN_ID_TEMP primary key (ID);
alter table XXI.CUS_CITIZEN_TEMP
  add constraint UK_COUNTRY_CODE_UNIC unique (COUNTRY_CODE);
alter table XXI.CUS_CITIZEN_TEMP
  add constraint CH_OSN_TMP
  check (OSN in ('Y','N'));
grant select, insert, update, delete on XXI.CUS_CITIZEN_TEMP to ODB;

prompt
prompt Creating table DOC_TYPES
prompt ========================
prompt
create table XXI.DOC_TYPES
(
  code NUMBER not null,
  name VARCHAR2(100)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.DOC_TYPES
  is 'Типы документов';
comment on column XXI.DOC_TYPES.code
  is 'Код';
comment on column XXI.DOC_TYPES.name
  is 'Название';
alter table XXI.DOC_TYPES
  add constraint PK_DOC_TYPES_CODE primary key (CODE)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.DOC_TYPES
  add constraint UK_DOC_TYPES_NAME unique (NAME)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete on XXI.DOC_TYPES to ODB;

prompt
prompt Creating table CUS_DOCUM
prompt ========================
prompt
create table XXI.CUS_DOCUM
(
  id_doc     NUMBER(12) not null,
  icusnum    NUMBER(12) not null,
  pref       VARCHAR2(1) default 'N' not null,
  id_doc_tp  NUMBER not null,
  doc_num    VARCHAR2(20),
  doc_ser    VARCHAR2(10),
  doc_date   DATE,
  doc_agency VARCHAR2(210),
  doc_period DATE,
  doc_subdiv VARCHAR2(15),
  sys_guid   VARCHAR2(100) not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 80K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.CUS_DOCUM
  is 'Документы';
comment on column XXI.CUS_DOCUM.id_doc
  is 'ID документа';
comment on column XXI.CUS_DOCUM.icusnum
  is 'Идентификатор контрагента';
comment on column XXI.CUS_DOCUM.pref
  is 'Признак основного документа-"Y"';
comment on column XXI.CUS_DOCUM.id_doc_tp
  is 'ID типа документа';
comment on column XXI.CUS_DOCUM.doc_num
  is 'Номер документа';
comment on column XXI.CUS_DOCUM.doc_ser
  is 'Серия документа';
comment on column XXI.CUS_DOCUM.doc_date
  is 'Дата выдачи';
comment on column XXI.CUS_DOCUM.doc_agency
  is 'Выдавший орган';
comment on column XXI.CUS_DOCUM.doc_period
  is 'Период действия';
comment on column XXI.CUS_DOCUM.doc_subdiv
  is 'Код подразделение, выдавшего документ';
comment on column XXI.CUS_DOCUM.sys_guid
  is 'sys_guid';
create index XXI.I_CUS_DOCUM_DOC_NUM on XXI.CUS_DOCUM (DOC_NUM)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_CUS_DOCUM_DOC_PERIOD on XXI.CUS_DOCUM (DOC_PERIOD)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.IF_CUS_DOCUM_DOC_TP on XXI.CUS_DOCUM (ID_DOC_TP)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.IF_CUS_DOCUM_LIST on XXI.CUS_DOCUM (ICUSNUM)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.CUS_DOCUM
  add constraint PK_CUS_DOCUM primary key (ID_DOC)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.CUS_DOCUM
  add constraint UK_CUS_DOCUM unique (SYS_GUID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.CUS_DOCUM
  add constraint FK_CUS_DOCUM_DOC_TP foreign key (ID_DOC_TP)
  references XXI.DOC_TYPES (CODE) on delete cascade;
alter table XXI.CUS_DOCUM
  add constraint FK_CUS_DOCUM_ICUSNUM foreign key (ICUSNUM)
  references XXI.CUS (ICUSNUM) on delete cascade;
alter table XXI.CUS_DOCUM
  add constraint CH_PREF
  check (PREF in ('Y','N'));
grant select, insert, update, delete on XXI.CUS_DOCUM to ODB;

prompt
prompt Creating table CUS_DOCUM_TEMP
prompt =============================
prompt
create global temporary table XXI.CUS_DOCUM_TEMP
(
  id_doc     NUMBER(12) not null,
  pref       VARCHAR2(1) default 'N' not null,
  id_doc_tp  NUMBER not null,
  doc_num    VARCHAR2(20),
  doc_ser    VARCHAR2(10),
  doc_date   DATE,
  doc_agency VARCHAR2(210),
  doc_period DATE,
  doc_subdiv VARCHAR2(15),
  guid       VARCHAR2(100)
)
on commit preserve rows;
comment on table XXI.CUS_DOCUM_TEMP
  is 'Документы';
comment on column XXI.CUS_DOCUM_TEMP.id_doc
  is 'ID документа';
comment on column XXI.CUS_DOCUM_TEMP.pref
  is 'Признак основного документа-"Y"';
comment on column XXI.CUS_DOCUM_TEMP.id_doc_tp
  is 'ID типа документа';
comment on column XXI.CUS_DOCUM_TEMP.doc_num
  is 'Номер документа';
comment on column XXI.CUS_DOCUM_TEMP.doc_ser
  is 'Серия документа';
comment on column XXI.CUS_DOCUM_TEMP.doc_date
  is 'Дата выдачи';
comment on column XXI.CUS_DOCUM_TEMP.doc_agency
  is 'Выдавший орган';
comment on column XXI.CUS_DOCUM_TEMP.doc_period
  is 'Период действия';
comment on column XXI.CUS_DOCUM_TEMP.doc_subdiv
  is 'Код подразделение, выдавшего документ';
comment on column XXI.CUS_DOCUM_TEMP.guid
  is 'GUID';
create index XXI.I_CUS_DOCUM_DOC_NUM_ on XXI.CUS_DOCUM_TEMP (DOC_NUM);
create index XXI.I_CUS_DOCUM_DOC_PERIOD_ on XXI.CUS_DOCUM_TEMP (DOC_PERIOD);
create index XXI.IF_CUS_DOCUM_DOC_TP_ on XXI.CUS_DOCUM_TEMP (ID_DOC_TP);
alter table XXI.CUS_DOCUM_TEMP
  add constraint PK_CUS_DOCUMTMP primary key (ID_DOC);
alter table XXI.CUS_DOCUM_TEMP
  add constraint CH_PREF_TEMP
  check (PREF in ('Y','N'));
grant select, insert, update, delete on XXI.CUS_DOCUM_TEMP to ODB;

prompt
prompt Creating table DMS
prompt ==================
prompt
create table XXI.DMS
(
  idmspos  NUMBER,
  idmsqnt  NUMBER,
  cdmsval  VARCHAR2(80),
  idmsrod  NUMBER,
  cdmsiso  CHAR(3),
  cdmsval1 VARCHAR2(80)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 80K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.DMS
  is 'Служебная таблица для функции N2S';
comment on column XXI.DMS.cdmsiso
  is 'ISO - код валюты';
comment on column XXI.DMS.cdmsval1
  is 'Вспомогательная единица измерения';
grant select, insert, update, delete on XXI.DMS to ODB;

prompt
prompt Creating table FR_DIALOG_SETTINGS
prompt =================================
prompt
create table XXI.FR_DIALOG_SETTINGS
(
  username    VARCHAR2(255),
  reportfile  VARCHAR2(255),
  storedate   DATE,
  controlname VARCHAR2(255),
  value       VARCHAR2(3000),
  settingname VARCHAR2(1000)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete, alter, debug on XXI.FR_DIALOG_SETTINGS to ODB;

prompt
prompt Creating table FR_LIC
prompt =====================
prompt
create table XXI.FR_LIC
(
  bik      VARCHAR2(99),
  dexpdate DATE,
  licdata  VARCHAR2(255)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  )
nologging;
comment on table XXI.FR_LIC
  is 'список имеющихся лицензий на использование подсистемы печати FR';
grant select, insert, update, delete, alter, debug on XXI.FR_LIC to ODB;
grant select, insert, update, delete, references, alter, index, debug on XXI.FR_LIC to PUBLIC;

prompt
prompt Creating table FR_QUERIES
prompt =========================
prompt
create table XXI.FR_QUERIES
(
  queryid      NUMBER(12) not null,
  reportid     NUMBER,
  reporttypeid NUMBER,
  queryname    VARCHAR2(4000),
  reportfile   VARCHAR2(255),
  querybody    CLOB
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column XXI.FR_QUERIES.queryid
  is 'Ключ';
comment on column XXI.FR_QUERIES.reportid
  is 'Отчет в АБС';
comment on column XXI.FR_QUERIES.reporttypeid
  is 'Тип отчета в АБС';
comment on column XXI.FR_QUERIES.queryname
  is 'Название запроса';
comment on column XXI.FR_QUERIES.reportfile
  is 'Имя файла отчета, если тип ID отчета не заполнен';
comment on column XXI.FR_QUERIES.querybody
  is 'Текст запроса';
create unique index XXI.FR_QUERIES_PK on XXI.FR_QUERIES (QUERYID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.FR_QUERIES
  add constraint FR_QUERIES_PK_CONSTRAINT primary key (QUERYID);
grant select, insert, update, delete, alter, debug on XXI.FR_QUERIES to ODB;

prompt
prompt Creating table FR_STORAGE
prompt =========================
prompt
create table XXI.FR_STORAGE
(
  report_file VARCHAR2(255) not null,
  value_name  VARCHAR2(255) not null,
  value       VARCHAR2(3200)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.FR_STORAGE
  is 'Хранилище значений FRREP';
create unique index XXI.PK_FR_STORAGE on XXI.FR_STORAGE (REPORT_FILE, VALUE_NAME)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete, alter, debug on XXI.FR_STORAGE to ODB;

prompt
prompt Creating table KLADR_SOCR
prompt =========================
prompt
create table XXI.KLADR_SOCR
(
  clevel   VARCHAR2(5),
  scname   VARCHAR2(10),
  socrname VARCHAR2(30),
  kod_t_st VARCHAR2(3)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 80K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete, debug on XXI.KLADR_SOCR to ODB;

prompt
prompt Creating table LOCK_ROW
prompt =======================
prompt
create table XXI.LOCK_ROW
(
  table_name VARCHAR2(100) not null,
  oper       VARCHAR2(100) not null,
  pk_key     NUMBER not null,
  lock_start DATE not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.LOCK_ROW
  is 'Таблица loks row';
comment on column XXI.LOCK_ROW.table_name
  is 'название таблицы';
comment on column XXI.LOCK_ROW.oper
  is 'логин пользователя';
comment on column XXI.LOCK_ROW.pk_key
  is 'первичный ключ';
comment on column XXI.LOCK_ROW.lock_start
  is 'время блокировки';
create index XXI.I_LOCK_ROW_TABLE_LOCK_START on XXI.LOCK_ROW (LOCK_START)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_LOCK_ROW_TABLE_NAME on XXI.LOCK_ROW (TABLE_NAME)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_LOCK_ROW_TABLE_OPER on XXI.LOCK_ROW (OPER)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_LOCK_ROW_TABLE_PK_KEY on XXI.LOCK_ROW (PK_KEY)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.LOCK_ROW
  add constraint UK_LOCK_ROW unique (TABLE_NAME, OPER, PK_KEY)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.LOCK_ROW
  add constraint DK_LOCK_ROW_OPER foreign key (OPER)
  references XXI.USR (CUSRLOGNAME);
grant select, insert, update, delete on XXI.LOCK_ROW to ODB;

prompt
prompt Creating table LOG_ERROR
prompt ========================
prompt
create global temporary table XXI.LOG_ERROR
(
  erid        NUMBER not null,
  curdate_    TIMESTAMP(6) not null,
  desc_error_ VARCHAR2(4000) not null,
  packname    VARCHAR2(100) not null
)
on commit preserve rows;
comment on table XXI.LOG_ERROR
  is 'Таблица для фиксации шибок';
comment on column XXI.LOG_ERROR.erid
  is 'ID';
comment on column XXI.LOG_ERROR.curdate_
  is 'Дата создания ';
comment on column XXI.LOG_ERROR.desc_error_
  is 'Описание ошибки';
comment on column XXI.LOG_ERROR.packname
  is 'Название программы';
create index XXI.LOG_PACKNAME on XXI.LOG_ERROR (PACKNAME);
alter table XXI.LOG_ERROR
  add constraint PK_LOG_ERID primary key (ERID);
grant select, insert, update, delete on XXI.LOG_ERROR to ODB;

prompt
prompt Creating table LOGS
prompt ===================
prompt
create table XXI.LOGS
(
  id         NUMBER not null,
  logdate    DATE default sysdate not null,
  oper       VARCHAR2(100) default user not null,
  linenumber NUMBER not null,
  classname  VARCHAR2(200) not null,
  methodname VARCHAR2(200) not null,
  error      CLOB not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.LOGS
  is 'Логирование';
comment on column XXI.LOGS.id
  is 'ИД';
comment on column XXI.LOGS.logdate
  is 'Время';
comment on column XXI.LOGS.oper
  is 'Пользователь';
comment on column XXI.LOGS.linenumber
  is 'Строка';
comment on column XXI.LOGS.classname
  is 'Класс';
comment on column XXI.LOGS.methodname
  is 'Название метода';
comment on column XXI.LOGS.error
  is 'Ошибка';
create index XXI.I_LOG_CLNM on XXI.LOGS (CLASSNAME)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_LOGS_LINENUMBER on XXI.LOGS (LINENUMBER)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_LOGS_LOGDATE on XXI.LOGS (LOGDATE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_LOGS_METHODNAME on XXI.LOGS (METHODNAME)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_LOGS_OPER on XXI.LOGS (OPER)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.LOGS
  add constraint PK_LOGS_ID primary key (ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete on XXI.LOGS to ODB;

prompt
prompt Creating table MONTHS_ABH
prompt =========================
prompt
create table XXI.MONTHS_ABH
(
  mnth     VARCHAR2(10),
  mnth_abh VARCHAR2(30)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.MONTHS_ABH
  is 'Месяцы на абх';
create index XXI.MONTHS_ABH on XXI.MONTHS_ABH (MNTH)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete on XXI.MONTHS_ABH to ODB;

prompt
prompt Creating table NAS_PUNKT
prompt ========================
prompt
create table XXI.NAS_PUNKT
(
  code NUMBER not null,
  name VARCHAR2(100) not null,
  area VARCHAR2(100) not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.NAS_PUNKT
  is 'Список нас пунктов';
comment on column XXI.NAS_PUNKT.code
  is 'Код';
comment on column XXI.NAS_PUNKT.name
  is 'Наименование';
comment on column XXI.NAS_PUNKT.area
  is 'Район';
create index XXI.I_NAS_PUNKT_AREA on XXI.NAS_PUNKT (AREA)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_NAS_PUNKT_CODE on XXI.NAS_PUNKT (CODE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_NAS_PUNKT_NAME on XXI.NAS_PUNKT (NAME)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.NAS_PUNKT
  add constraint PK_NAS_PUNKT_CODE primary key (CODE);
grant select, insert, update, delete on XXI.NAS_PUNKT to ODB;

prompt
prompt Creating table NT_DOV_TYPES
prompt ===========================
prompt
create table XXI.NT_DOV_TYPES
(
  id   NUMBER not null,
  type VARCHAR2(500)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table XXI.NT_DOV_TYPES
  is 'Типы доверенности';
comment on column XXI.NT_DOV_TYPES.id
  is 'ИД';
comment on column XXI.NT_DOV_TYPES.type
  is 'Название типа';
alter table XXI.NT_DOV_TYPES
  add constraint PK_NT_DOV_TYPES primary key (ID)
  using index 
  tablespace MJ_DATA
  pctfree 10
  initrans 2
  maxtrans 255;
grant select, insert, update, delete on XXI.NT_DOV_TYPES to ODB;

prompt
prompt Creating table NT_DOV
prompt =====================
prompt
create table XXI.NT_DOV
(
  id       NUMBER not null,
  datetime DATE default SYSDATE not null,
  oper     VARCHAR2(200) default USER not null,
  notary   NUMBER not null,
  dov_type NUMBER not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table XXI.NT_DOV
  is 'Нотариус. Доверенности по типам';
comment on column XXI.NT_DOV.id
  is 'ID';
comment on column XXI.NT_DOV.datetime
  is 'ДАТА ЗАПИСИ';
comment on column XXI.NT_DOV.oper
  is 'ПОЛЬЗОВАТЕЛЬ';
comment on column XXI.NT_DOV.notary
  is 'ССЫЛКА НА НОТАРИУС';
comment on column XXI.NT_DOV.dov_type
  is 'Ссылка на тип доверенности';
create index XXI.I_NT_DOV_DATETIME on XXI.NT_DOV (DATETIME)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
create index XXI.I_NT_DOV_NOTARY on XXI.NT_DOV (NOTARY)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
create index XXI.I_NT_DOV_OPER on XXI.NT_DOV (OPER)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table XXI.NT_DOV
  add constraint PK_NT_DOV primary key (ID)
  using index 
  tablespace MJ_DATA
  pctfree 10
  initrans 2
  maxtrans 255;
alter table XXI.NT_DOV
  add constraint FK_NT_DOV_DOV_TYPE foreign key (DOV_TYPE)
  references XXI.NT_DOV_TYPES (ID);
alter table XXI.NT_DOV
  add constraint FK_NT_DOV_OPER foreign key (NOTARY)
  references XXI.NOTARY (NOT_ID);
grant select, insert, update, delete on XXI.NT_DOV to ODB;

prompt
prompt Creating table ODB_ACTION
prompt =========================
prompt
create table XXI.ODB_ACTION
(
  act_id     NUMBER(6) not null,
  act_parent NUMBER(6),
  act_npp    NUMBER(3) default 1 not null,
  act_name   VARCHAR2(64) not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.ODB_ACTION
  is 'Иерархический список действий';
comment on column XXI.ODB_ACTION.act_id
  is 'Идентификатор действия';
comment on column XXI.ODB_ACTION.act_parent
  is 'Идентификатор родительского действия';
comment on column XXI.ODB_ACTION.act_npp
  is 'Порядковый номер внутри иерархии';
comment on column XXI.ODB_ACTION.act_name
  is 'Наименование действия';
create index XXI.F_ODB_ACTION_SAME on XXI.ODB_ACTION (ACT_PARENT)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.ODB_ACTION
  add constraint PK_ODB_ACTION primary key (ACT_ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.ODB_ACTION
  add constraint F_ODB_ACTION_SAME foreign key (ACT_PARENT)
  references XXI.ODB_ACTION (ACT_ID) on delete cascade
  disable
  novalidate;
grant select, insert, update, delete on XXI.ODB_ACTION to ODB;

prompt
prompt Creating table ODB_GROUP_USR
prompt ============================
prompt
create table XXI.ODB_GROUP_USR
(
  grp_id             NUMBER(4) not null,
  grp_name           VARCHAR2(64),
  notation_extend_id NUMBER(12)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 80K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.ODB_GROUP_USR
  is 'Права доступа. Группы доступа по действиям (5.9.8)(04.06.2015)(Nick-3D)';
comment on column XXI.ODB_GROUP_USR.grp_id
  is 'Идентификатор группы';
comment on column XXI.ODB_GROUP_USR.grp_name
  is 'Наименование группы';
comment on column XXI.ODB_GROUP_USR.notation_extend_id
  is 'Внешний Id примечаний';
create index XXI.IF_ODB_GRP_USR_2_N3EX_4_NOT on XXI.ODB_GROUP_USR (NOTATION_EXTEND_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 80K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.ODB_GROUP_USR
  add constraint PK_ODB_GROUP_USR primary key (GRP_ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete on XXI.ODB_GROUP_USR to ODB;

prompt
prompt Creating table ODB_ACTGRP
prompt =========================
prompt
create table XXI.ODB_ACTGRP
(
  grp_id    NUMBER not null,
  odbact_id NUMBER not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.ODB_ACTGRP
  is 'Группы и действия';
comment on column XXI.ODB_ACTGRP.grp_id
  is 'Ссылка на группу';
comment on column XXI.ODB_ACTGRP.odbact_id
  is 'Ссылка на действие';
create index XXI.I_GRP_ID on XXI.ODB_ACTGRP (GRP_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_ODB_ACT_GRP_ID on XXI.ODB_ACTGRP (ODBACT_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.ODB_ACTGRP
  add constraint FK_GRP_ID foreign key (GRP_ID)
  references XXI.ODB_GROUP_USR (GRP_ID) on delete cascade;
alter table XXI.ODB_ACTGRP
  add constraint FK_ODBACT_GRP_ID foreign key (ODBACT_ID)
  references XXI.ODB_ACTION (ACT_ID) on delete cascade;
grant select, insert, update, delete on XXI.ODB_ACTGRP to ODB;

prompt
prompt Creating table ODB_ACTUSR
prompt =========================
prompt
create table XXI.ODB_ACTUSR
(
  usr_id    NUMBER not null,
  odbact_id NUMBER not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.ODB_ACTUSR
  is 'Пользователи и действия';
comment on column XXI.ODB_ACTUSR.usr_id
  is 'Ссылка на пользователя';
comment on column XXI.ODB_ACTUSR.odbact_id
  is 'Ссылка на действие';
create index XXI.I_ODBACT_ID on XXI.ODB_ACTUSR (ODBACT_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_USR_ID on XXI.ODB_ACTUSR (USR_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.ODB_ACTUSR
  add constraint FK_ODBACT_ID foreign key (ODBACT_ID)
  references XXI.ODB_ACTION (ACT_ID) on delete cascade;
alter table XXI.ODB_ACTUSR
  add constraint FK_USR_ID foreign key (USR_ID)
  references XXI.USR (IUSRID) on delete cascade;
grant select, insert, update, delete on XXI.ODB_ACTUSR to ODB;

prompt
prompt Creating table ODB_GRP_MEMBER
prompt =============================
prompt
create table XXI.ODB_GRP_MEMBER
(
  grp_id NUMBER(4) not null,
  iusrid NUMBER(6) not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 80K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.ODB_GRP_MEMBER
  is 'Вхождение пользователей в группы по действиям';
comment on column XXI.ODB_GRP_MEMBER.grp_id
  is 'Идентификатор группы';
comment on column XXI.ODB_GRP_MEMBER.iusrid
  is 'Идентификатор пользователя';
create index XXI.F_ODB_GRP_MEMBER_GRP on XXI.ODB_GRP_MEMBER (GRP_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.F_ODB_GRP_MEMBER_USR on XXI.ODB_GRP_MEMBER (IUSRID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.ODB_GRP_MEMBER
  add constraint PK_ODB_GRP_MEMBER primary key (GRP_ID, IUSRID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.ODB_GRP_MEMBER
  add constraint F_GRP_MEMBER_GRP foreign key (GRP_ID)
  references XXI.ODB_GROUP_USR (GRP_ID);
alter table XXI.ODB_GRP_MEMBER
  add constraint F_ODB_GRP_MEMBER_USR foreign key (IUSRID)
  references XXI.USR (IUSRID) on delete cascade;
grant select, insert, update, delete on XXI.ODB_GRP_MEMBER to ODB;

prompt
prompt Creating table ODB_MNU
prompt ======================
prompt
create table XXI.ODB_MNU
(
  mnu_id     NUMBER(6) not null,
  mnu_parent NUMBER(6),
  mnu_npp    NUMBER(3) default 1 not null,
  mnu_name   VARCHAR2(64) not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.ODB_MNU
  is 'Иерархический список меню';
comment on column XXI.ODB_MNU.mnu_id
  is 'Идентификатор действия';
comment on column XXI.ODB_MNU.mnu_parent
  is 'Идентификатор родительского действия';
comment on column XXI.ODB_MNU.mnu_npp
  is 'Порядковый номер внутри иерархии';
comment on column XXI.ODB_MNU.mnu_name
  is 'Наименование действия';
create index XXI.F_ODB_MNU_SAME on XXI.ODB_MNU (MNU_PARENT)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.ODB_MNU
  add constraint PK_ODB_MNU primary key (MNU_ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.ODB_MNU
  add constraint FK_ODB_MNU_SAME foreign key (MNU_PARENT)
  references XXI.ODB_MNU (MNU_ID) on delete cascade;
grant select, insert, update, delete on XXI.ODB_MNU to ODB;

prompt
prompt Creating table ODB_MNUGRP
prompt =========================
prompt
create table XXI.ODB_MNUGRP
(
  grp_id    NUMBER not null,
  odbmnu_id NUMBER not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.ODB_MNUGRP
  is 'Группы и меню';
comment on column XXI.ODB_MNUGRP.grp_id
  is 'Ссылка на ГРУППУ';
comment on column XXI.ODB_MNUGRP.odbmnu_id
  is 'Ссылка на меню';
create index XXI.I_ODBMNU_GRP_ID on XXI.ODB_MNUGRP (ODBMNU_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_USRMNU_GRP_ID on XXI.ODB_MNUGRP (GRP_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.ODB_MNUGRP
  add constraint FK_ODB_MNUG_ODBMNU_ID foreign key (ODBMNU_ID)
  references XXI.ODB_MNU (MNU_ID) on delete cascade;
alter table XXI.ODB_MNUGRP
  add constraint FK_ODB_MNUGRP_GRP_ID foreign key (GRP_ID)
  references XXI.ODB_GROUP_USR (GRP_ID) on delete cascade;
grant select, insert, update, delete on XXI.ODB_MNUGRP to ODB;

prompt
prompt Creating table ODB_MNUUSR
prompt =========================
prompt
create table XXI.ODB_MNUUSR
(
  usr_id    NUMBER not null,
  odbmnu_id NUMBER not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.ODB_MNUUSR
  is 'Пользователи и меню';
comment on column XXI.ODB_MNUUSR.usr_id
  is 'Ссылка на пользователя';
comment on column XXI.ODB_MNUUSR.odbmnu_id
  is 'Ссылка на меню';
create index XXI.I_ODBMNU_ID on XXI.ODB_MNUUSR (ODBMNU_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_USRMNU_ID on XXI.ODB_MNUUSR (USR_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.ODB_MNUUSR
  add constraint FK_ODBMNU_ID foreign key (ODBMNU_ID)
  references XXI.ODB_MNU (MNU_ID) on delete cascade;
alter table XXI.ODB_MNUUSR
  add constraint FK_USR_MNU_ID foreign key (USR_ID)
  references XXI.USR (IUSRID) on delete cascade;
grant select, insert, update, delete on XXI.ODB_MNUUSR to ODB;

prompt
prompt Creating table ODB_PROFILES
prompt ===========================
prompt
create table XXI.ODB_PROFILES
(
  profile       VARCHAR2(30) not null,
  resource_name VARCHAR2(30) not null,
  limit         VARCHAR2(30) not null,
  resource_type VARCHAR2(30) not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 80K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.ODB_PROFILES
  is 'Права доступа. Дополнительные ресурсы профилей (5.8.8)(28.06.2011)(Nick-3D)';
comment on column XXI.ODB_PROFILES.profile
  is 'Профиль';
comment on column XXI.ODB_PROFILES.resource_name
  is 'Ресурс';
comment on column XXI.ODB_PROFILES.limit
  is 'Значение';
comment on column XXI.ODB_PROFILES.resource_type
  is 'Тип';
alter table XXI.ODB_PROFILES
  add constraint PK_ODB_PRF primary key (PROFILE, RESOURCE_NAME)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete on XXI.ODB_PROFILES to ODB;

prompt
prompt Creating table PROJECT
prompt ======================
prompt
create table XXI.PROJECT
(
  prj_id     NUMBER not null,
  prj_parent NUMBER,
  prj_name   VARCHAR2(64) not null,
  is_folder  VARCHAR2(1) not null,
  version    NUMBER,
  bytes      NUMBER
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.PROJECT
  is 'Иерархический файлов и папок проекта';
comment on column XXI.PROJECT.prj_id
  is 'Идентификатор';
comment on column XXI.PROJECT.prj_parent
  is 'Идентификатор родительской папки';
comment on column XXI.PROJECT.prj_name
  is 'Наименование';
comment on column XXI.PROJECT.is_folder
  is 'Папка или нет';
comment on column XXI.PROJECT.version
  is 'Версия';
comment on column XXI.PROJECT.bytes
  is 'Размер файла';
create index XXI.I_PROJECT_IS_FOLDER on XXI.PROJECT (IS_FOLDER)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_PROJECT_PRJ_NAME on XXI.PROJECT (PRJ_NAME)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_PROJECT_VERSION on XXI.PROJECT (VERSION)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create unique index XXI.UK_PROJECT on XXI.PROJECT (PRJ_PARENT, PRJ_NAME, IS_FOLDER)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.PROJECT
  add constraint PK_PROJECT_ID primary key (PRJ_ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.PROJECT
  add constraint FK_PROJECT_PRJ_PARENT foreign key (PRJ_PARENT)
  references XXI.PROJECT (PRJ_ID) on delete cascade;
alter table XXI.PROJECT
  add constraint CH_PROJECT_BYTES
  check ((IS_FOLDER = 'N' and bytes is not null) or IS_FOLDER in ('F','Y'));
alter table XXI.PROJECT
  add constraint CH_PROJECT_IS_FOLDER
  check (is_folder in ('Y','N','F'));
alter table XXI.PROJECT
  add constraint CH_PROJECT_VER
  check ((IS_FOLDER = 'N' and VERSION is not null) or IS_FOLDER in ('Y','F'));
grant select, insert, update, delete on XXI.PROJECT to ODB;
grant select on XXI.PROJECT to UPDATES;

prompt
prompt Creating table PRJ_FILE
prompt =======================
prompt
create table XXI.PRJ_FILE
(
  prj_id   NUMBER not null,
  blb_file BLOB not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.PRJ_FILE
  is 'Файлы проекта';
comment on column XXI.PRJ_FILE.prj_id
  is 'Ссылка на id файла';
comment on column XXI.PRJ_FILE.blb_file
  is 'Сам файл';
alter table XXI.PRJ_FILE
  add constraint PK_PRJ_FILE_PRJ_ID primary key (PRJ_ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.PRJ_FILE
  add constraint FK_PRJ_FILE_PRJ_ID foreign key (PRJ_ID)
  references XXI.PROJECT (PRJ_ID) on delete cascade;
grant select, insert, update, delete on XXI.PRJ_FILE to ODB;
grant select on XXI.PRJ_FILE to UPDATES;

prompt
prompt Creating table PRJ_FL_VER_HIST
prompt ==============================
prompt
create table XXI.PRJ_FL_VER_HIST
(
  prj_id   NUMBER not null,
  verision NUMBER not null,
  dt       DATE default sysdate not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.PRJ_FL_VER_HIST
  is 'История версии файлов';
comment on column XXI.PRJ_FL_VER_HIST.prj_id
  is 'Ссылка на файл';
comment on column XXI.PRJ_FL_VER_HIST.verision
  is 'Версия';
comment on column XXI.PRJ_FL_VER_HIST.dt
  is 'Дата';
create index XXI.I_PRJ_FL_VER_HIST_DT on XXI.PRJ_FL_VER_HIST (DT)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_PRJ_FL_VER_HIST_PRJ_ID on XXI.PRJ_FL_VER_HIST (PRJ_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_PRJ_FL_VER_HIST_VERISION on XXI.PRJ_FL_VER_HIST (VERISION)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.PRJ_FL_VER_HIST
  add constraint UK_PRJ_FL_VER_HIST unique (PRJ_ID, VERISION)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.PRJ_FL_VER_HIST
  add constraint FK_PRJ_ID foreign key (PRJ_ID)
  references XXI.PROJECT (PRJ_ID) on delete cascade;
grant select, insert, update, delete on XXI.PRJ_FL_VER_HIST to ODB;
grant select on XXI.PRJ_FL_VER_HIST to UPDATES;

prompt
prompt Creating table PROJECT_TEMP
prompt ===========================
prompt
create global temporary table XXI.PROJECT_TEMP
(
  prj_id     NUMBER not null,
  prj_parent NUMBER,
  bytes      NUMBER,
  prj_name   VARCHAR2(64) not null,
  is_folder  VARCHAR2(1) not null,
  version    NUMBER
)
on commit preserve rows;
comment on table XXI.PROJECT_TEMP
  is 'Иерархический файлов и папок проекта (временная)';
comment on column XXI.PROJECT_TEMP.prj_id
  is 'Идентификатор';
comment on column XXI.PROJECT_TEMP.prj_parent
  is 'Идентификатор родительской папки';
comment on column XXI.PROJECT_TEMP.bytes
  is 'Размер';
comment on column XXI.PROJECT_TEMP.prj_name
  is 'Наименование';
comment on column XXI.PROJECT_TEMP.is_folder
  is 'Папка или нет';
comment on column XXI.PROJECT_TEMP.version
  is 'Версия';
create index XXI.I_PROJECT_TEMP_IS_FOLDER on XXI.PROJECT_TEMP (IS_FOLDER);
create index XXI.I_PROJECT_TEMP_PRJ_NAME on XXI.PROJECT_TEMP (PRJ_NAME);
create index XXI.I_PROJECT_TEMP_VERSION on XXI.PROJECT_TEMP (VERSION);
create unique index XXI.UK_PROJECT_TEMP on XXI.PROJECT_TEMP (PRJ_PARENT, PRJ_NAME, IS_FOLDER);
alter table XXI.PROJECT_TEMP
  add constraint PK_PROJECT_TEMP_ID primary key (PRJ_ID);
alter table XXI.PROJECT_TEMP
  add constraint CH_PROJECT_TEMP_IS_FOLDER
  check (is_folder in ('Y','N','F'));
alter table XXI.PROJECT_TEMP
  add constraint CH_PROJECT_TEMP_VER
  check ((IS_FOLDER = 'N' and VERSION is not null) or IS_FOLDER in ('Y','F'));
grant select, insert, update, delete on XXI.PROJECT_TEMP to ODB;
grant select, insert, update, delete on XXI.PROJECT_TEMP to UPDATES;

prompt
prompt Creating table REPORTS
prompt ======================
prompt
create table XXI.REPORTS
(
  rep_id    NUMBER not null,
  rep_name  VARCHAR2(500) not null,
  rep_type  VARCHAR2(50) not null,
  rep_class CLOB not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.REPORTS
  is 'Список отчетов';
comment on column XXI.REPORTS.rep_id
  is 'ид отчета';
comment on column XXI.REPORTS.rep_name
  is 'название отчета';
comment on column XXI.REPORTS.rep_type
  is 'тип отчета';
comment on column XXI.REPORTS.rep_class
  is 'java класс отчета';
create index XXI.I_REPORTS_REP_NAME on XXI.REPORTS (REP_NAME)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_REPORTS_REP_TYPE on XXI.REPORTS (REP_TYPE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.REPORTS
  add constraint PK_REPORTS primary key (REP_ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.REPORTS
  add constraint CH_REPORTS_TYPE
  check (rep_type in ('jasper'));
grant select, insert, update, delete on XXI.REPORTS to ODB;

prompt
prompt Creating table REP_PARAMS
prompt =========================
prompt
create table XXI.REP_PARAMS
(
  rep_id        NUMBER not null,
  prm_name      VARCHAR2(500) not null,
  prm_def_value VARCHAR2(500),
  prm_id        NUMBER not null,
  is_list       VARCHAR2(1) not null,
  list_query    VARCHAR2(4000)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.REP_PARAMS
  is 'Параметры отчетов';
comment on column XXI.REP_PARAMS.rep_id
  is 'Ссылка на отчет';
comment on column XXI.REP_PARAMS.prm_name
  is 'Название параметра';
comment on column XXI.REP_PARAMS.prm_def_value
  is 'Значение по умолчанию для параметра';
comment on column XXI.REP_PARAMS.prm_id
  is 'Номер параметра';
comment on column XXI.REP_PARAMS.is_list
  is 'Список?';
comment on column XXI.REP_PARAMS.list_query
  is 'Запрос списка';
create index XXI.I_REP_PARAMS_IS_LIST on XXI.REP_PARAMS (IS_LIST)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_REP_PARAMS_PRM_ID on XXI.REP_PARAMS (PRM_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_REP_PARAMS_REP_ID on XXI.REP_PARAMS (REP_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.REP_PARAMS
  add constraint UK_REP_PARAMS primary key (REP_ID, PRM_ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.REP_PARAMS
  add constraint FK_REP_PARAMS_REP_ID foreign key (REP_ID)
  references XXI.REPORTS (REP_ID);
alter table XXI.REP_PARAMS
  add constraint CH_REP_PARAMS_IS_LIST
  check (IS_LIST in ('Y','N'));
alter table XXI.REP_PARAMS
  add constraint CH_REP_PARAMS_LIST_QUERY
  check ((IS_LIST = 'Y' and LIST_QUERY is not null)
or
IS_LIST = 'N');
grant select, insert, update, delete on XXI.REP_PARAMS to ODB;

prompt
prompt Creating table REP_PARAMS_TMP
prompt =============================
prompt
create global temporary table XXI.REP_PARAMS_TMP
(
  rep_id    NUMBER not null,
  prm_id    NUMBER not null,
  prm_value VARCHAR2(500)
)
on commit preserve rows;
comment on table XXI.REP_PARAMS_TMP
  is 'Таблица параметров для отчета (временная)';
comment on column XXI.REP_PARAMS_TMP.rep_id
  is 'Ссылка на отчет';
comment on column XXI.REP_PARAMS_TMP.prm_id
  is 'Номер параметра';
comment on column XXI.REP_PARAMS_TMP.prm_value
  is 'Значение параметра';
create index XXI.I_REP_PARAMS_TEMP_PRM_ID on XXI.REP_PARAMS_TMP (PRM_ID);
create index XXI.I_REP_PARAMS_TEMP_REP_ID on XXI.REP_PARAMS_TMP (REP_ID);
alter table XXI.REP_PARAMS_TMP
  add constraint UK_REP_PARAMS_TMP unique (PRM_ID, REP_ID);
grant select, insert, update, delete on XXI.REP_PARAMS_TMP to ODB;

prompt
prompt Creating table SMR
prompt ==================
prompt
create table XXI.SMR
(
  csmrmfo8 VARCHAR2(99) not null
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  )
nologging;
comment on table XXI.SMR
  is 'Основная настроечная таблица';
create index XXI.IF_SMR_CSMRMFO8 on XXI.SMR (CSMRMFO8)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete on XXI.SMR to ODB;

prompt
prompt Creating table STATS$USER_LOG
prompt =============================
prompt
create table XXI.STATS$USER_LOG
(
  user_id         VARCHAR2(500),
  session_id      NUMBER(8),
  host            VARCHAR2(500),
  last_program    VARCHAR2(500),
  last_action     VARCHAR2(500),
  last_module     VARCHAR2(500),
  logon_day       DATE,
  logon_time      VARCHAR2(10),
  logoff_day      DATE,
  logoff_time     VARCHAR2(10),
  elapsed_minutes NUMBER(8),
  ip              VARCHAR2(50),
  first_module    VARCHAR2(500),
  os_user         VARCHAR2(500)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 80K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.STATS$USER_LOG
  is 'Лог вхождении в программу';
create index XXI.FIRST_MODULE on XXI.STATS$USER_LOG (FIRST_MODULE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 80K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.IP on XXI.STATS$USER_LOG (IP)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.LOGOFF_DAY on XXI.STATS$USER_LOG (LOGOFF_DAY)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.LOGON_DAY on XXI.STATS$USER_LOG (LOGON_DAY)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.LOGON_HOST on XXI.STATS$USER_LOG (HOST)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.LOGON_USER_ID on XXI.STATS$USER_LOG (USER_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.OS_USER on XXI.STATS$USER_LOG (OS_USER)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete on XXI.STATS$USER_LOG to ODB;

prompt
prompt Creating table TEST
prompt ===================
prompt
create table XXI.TEST
(
  id  NUMBER,
  lob CLOB
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.TEST
  is 'Для тестирования';
grant select, insert, update, delete on XXI.TEST to ODB;

prompt
prompt Creating table UPDATE_ABH_NAME
prompt ==============================
prompt
create table XXI.UPDATE_ABH_NAME
(
  id            NUMBER not null,
  old_lastname  VARCHAR2(100),
  old_firstname VARCHAR2(100),
  old_middlname VARCHAR2(100),
  new_lastname  VARCHAR2(100),
  new_firstname VARCHAR2(100),
  new_middlname VARCHAR2(100),
  brn_act_id    NUMBER,
  doc_date      DATE default sysdate not null,
  oper          VARCHAR2(50) default user not null,
  zags_id       NUMBER,
  cusid         NUMBER,
  svid_number   VARCHAR2(100),
  svid_seria    VARCHAR2(100)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.UPDATE_ABH_NAME
  is 'Восстановление абхазской фамилии';
comment on column XXI.UPDATE_ABH_NAME.id
  is 'ИД';
comment on column XXI.UPDATE_ABH_NAME.old_lastname
  is 'Фамилия до перемены';
comment on column XXI.UPDATE_ABH_NAME.old_firstname
  is 'Имя до перемены';
comment on column XXI.UPDATE_ABH_NAME.old_middlname
  is 'Отчество до перемены';
comment on column XXI.UPDATE_ABH_NAME.new_lastname
  is 'Фамилия после перемены';
comment on column XXI.UPDATE_ABH_NAME.new_firstname
  is 'Имя после перемены';
comment on column XXI.UPDATE_ABH_NAME.new_middlname
  is 'Отчество после перемены';
comment on column XXI.UPDATE_ABH_NAME.brn_act_id
  is 'Ссылка на акт о рождении';
comment on column XXI.UPDATE_ABH_NAME.doc_date
  is 'Дата создания';
comment on column XXI.UPDATE_ABH_NAME.oper
  is 'Пользователь';
comment on column XXI.UPDATE_ABH_NAME.zags_id
  is 'ИД загса';
comment on column XXI.UPDATE_ABH_NAME.cusid
  is 'Ссылка на клиента';
comment on column XXI.UPDATE_ABH_NAME.svid_number
  is 'Выдано свидетельство номер';
comment on column XXI.UPDATE_ABH_NAME.svid_seria
  is 'Выдано свидетельство серия';
create index XXI.I_UPDATE_ABH_NAME_BRN_ACT_ID on XXI.UPDATE_ABH_NAME (BRN_ACT_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_UPDATE_ABH_NAME_CUSID on XXI.UPDATE_ABH_NAME (CUSID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_UPDATE_ABH_NAME_DOC_DATE on XXI.UPDATE_ABH_NAME (DOC_DATE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_UPDATE_ABH_NAME_OPER on XXI.UPDATE_ABH_NAME (OPER)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_UPDATE_ABH_NAME_ZAGS_ID on XXI.UPDATE_ABH_NAME (ZAGS_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.UPDATE_ABH_NAME
  add constraint PK_UPDATE_ABH_NAME_ID primary key (ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.UPDATE_ABH_NAME
  add constraint FK_UPDATE_ABH_NAME_ZAGS_ID foreign key (ZAGS_ID)
  references XXI.ZAGS (ZAGS_ID);
grant select, insert, update, delete on XXI.UPDATE_ABH_NAME to ODB;

prompt
prompt Creating table UPDATE_NAME
prompt ==========================
prompt
create table XXI.UPDATE_NAME
(
  id               NUMBER not null,
  old_lastname     VARCHAR2(100),
  old_firstname    VARCHAR2(100),
  old_middlname    VARCHAR2(100),
  new_lastname     VARCHAR2(100),
  new_firstname    VARCHAR2(100),
  new_middlname    VARCHAR2(100),
  brn_act_id       NUMBER,
  doc_date         DATE default sysdate not null,
  oper             VARCHAR2(50) default user not null,
  zags_id          NUMBER,
  cusid            NUMBER,
  svid_number      VARCHAR2(100),
  svid_seria       VARCHAR2(100),
  old_lastname_ab  VARCHAR2(100),
  old_firstname_ab VARCHAR2(100),
  old_middlname_ab VARCHAR2(100),
  new_lastname_ab  VARCHAR2(100),
  new_firstname_ab VARCHAR2(100),
  new_middlname_ab VARCHAR2(100)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.UPDATE_NAME
  is 'Перемена имени';
comment on column XXI.UPDATE_NAME.id
  is 'ИД';
comment on column XXI.UPDATE_NAME.old_lastname
  is 'Фамилия до перемены';
comment on column XXI.UPDATE_NAME.old_firstname
  is 'Имя до перемены';
comment on column XXI.UPDATE_NAME.old_middlname
  is 'Отчество до перемены';
comment on column XXI.UPDATE_NAME.new_lastname
  is 'Фамилия после перемены';
comment on column XXI.UPDATE_NAME.new_firstname
  is 'Имя после перемены';
comment on column XXI.UPDATE_NAME.new_middlname
  is 'Отчество после перемены';
comment on column XXI.UPDATE_NAME.brn_act_id
  is 'Ссылка на акт о рождении';
comment on column XXI.UPDATE_NAME.doc_date
  is 'Дата создания';
comment on column XXI.UPDATE_NAME.oper
  is 'Пользователь';
comment on column XXI.UPDATE_NAME.zags_id
  is 'ИД загса';
comment on column XXI.UPDATE_NAME.cusid
  is 'Ссылка на клиента';
comment on column XXI.UPDATE_NAME.svid_number
  is 'Выдано свидетельство номер';
comment on column XXI.UPDATE_NAME.svid_seria
  is 'Выдано свидетельство серия';
comment on column XXI.UPDATE_NAME.old_lastname_ab
  is 'Фамилия до перемены АБХ';
comment on column XXI.UPDATE_NAME.old_firstname_ab
  is 'Имя до перемены АБХ';
comment on column XXI.UPDATE_NAME.old_middlname_ab
  is 'Отчество до перемены АБХ';
comment on column XXI.UPDATE_NAME.new_lastname_ab
  is 'Фамилия после перемены АБХ';
comment on column XXI.UPDATE_NAME.new_firstname_ab
  is 'Имя после перемены АБХ';
comment on column XXI.UPDATE_NAME.new_middlname_ab
  is 'Отчество после перемены АБХ';
create index XXI.I_UPDATE_NAME_BRN_ACT_ID on XXI.UPDATE_NAME (BRN_ACT_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_UPDATE_NAME_CUSID on XXI.UPDATE_NAME (CUSID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_UPDATE_NAME_DOC_DATE on XXI.UPDATE_NAME (DOC_DATE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_UPDATE_NAME_OPER on XXI.UPDATE_NAME (OPER)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_UPDATE_NAME_ZAGS_ID on XXI.UPDATE_NAME (ZAGS_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.UPDATE_NAME
  add constraint PK_UPDATE_NAME_ID primary key (ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.UPDATE_NAME
  add constraint FK_UPDATE_NAME_ZAGS_ID foreign key (ZAGS_ID)
  references XXI.ZAGS (ZAGS_ID);
grant select, insert, update, delete on XXI.UPDATE_NAME to ODB;

prompt
prompt Creating table UPD_NAT
prompt ======================
prompt
create table XXI.UPD_NAT
(
  id          NUMBER not null,
  cusid       NUMBER,
  oper        VARCHAR2(100) default user not null,
  doc_date    DATE default sysdate not null,
  zags_id     NUMBER,
  brn_act_id  NUMBER,
  old_nat     NUMBER,
  new_nat     NUMBER,
  fio         VARCHAR2(100),
  svid_seria  VARCHAR2(100),
  svid_number VARCHAR2(100)
)
tablespace MJ_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table XXI.UPD_NAT
  is 'Перемена национальной принадлежности';
comment on column XXI.UPD_NAT.id
  is 'ИД';
comment on column XXI.UPD_NAT.cusid
  is 'Ссылка на клиента';
comment on column XXI.UPD_NAT.oper
  is 'Пользователь';
comment on column XXI.UPD_NAT.doc_date
  is 'Дата заведения';
comment on column XXI.UPD_NAT.zags_id
  is 'Ссылка на загс';
comment on column XXI.UPD_NAT.brn_act_id
  is 'Ссылка  на свидетельство о рождении';
comment on column XXI.UPD_NAT.old_nat
  is 'Старая национальность';
comment on column XXI.UPD_NAT.new_nat
  is 'Новая национальность';
comment on column XXI.UPD_NAT.fio
  is 'ФИО';
comment on column XXI.UPD_NAT.svid_seria
  is 'Серия';
comment on column XXI.UPD_NAT.svid_number
  is 'Номер';
create index XXI.I_UPD_NAT_BRN_ACT_ID on XXI.UPD_NAT (BRN_ACT_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_UPD_NAT_DOC_DATE on XXI.UPD_NAT (DOC_DATE)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_UPD_NAT_NEW_NAT on XXI.UPD_NAT (NEW_NAT)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_UPD_NAT_OLD_NAT on XXI.UPD_NAT (OLD_NAT)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_UPD_NAT_OPER on XXI.UPD_NAT (OPER)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index XXI.I_UPD_NAT_ZAGS_ID on XXI.UPD_NAT (ZAGS_ID)
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.UPD_NAT
  add constraint PK_UPD_NAT_ID primary key (ID)
  using index 
  tablespace INDEXES
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table XXI.UPD_NAT
  add constraint FK_UPD_NAT_BRN_ACT_ID foreign key (BRN_ACT_ID)
  references XXI.BRN_BIRTH_ACT (BR_ACT_ID) on delete cascade
  disable
  novalidate;
alter table XXI.UPD_NAT
  add constraint FK_UPD_NAT_NEW_NAT foreign key (NEW_NAT)
  references XXI.NATIONALITY (ID);
alter table XXI.UPD_NAT
  add constraint FK_UPD_NAT_OLD_NAT foreign key (OLD_NAT)
  references XXI.NATIONALITY (ID);
alter table XXI.UPD_NAT
  add constraint FK_UPD_NAT_ZAGS_ID foreign key (ZAGS_ID)
  references XXI.ZAGS (ZAGS_ID);
grant select, insert, update, delete on XXI.UPD_NAT to ODB;

prompt
prompt Creating sequence FR_QUERIES_SEQ
prompt ================================
prompt
create sequence XXI.FR_QUERIES_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence S_ACCESS_LIST
prompt ===============================
prompt
create sequence XXI.S_ACCESS_LIST
minvalue 0
maxvalue 9999999999999999999999999999
start with 65
increment by 1
cache 20;

prompt
prompt Creating sequence S_ADOPTOIN
prompt ============================
prompt
create sequence XXI.S_ADOPTOIN
minvalue 0
maxvalue 9999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence S_AREA
prompt ========================
prompt
create sequence XXI.S_AREA
minvalue 0
maxvalue 9999999999999999999999999999
start with 8
increment by 1
cache 20;

prompt
prompt Creating sequence S_AU_ACTION
prompt =============================
prompt
create sequence XXI.S_AU_ACTION
minvalue 1
maxvalue 9999999999999999999999999999
start with 1122
increment by 1
cache 20;

prompt
prompt Creating sequence S_AU_SESSION
prompt ==============================
prompt
create sequence XXI.S_AU_SESSION
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence S_BRN_BIRTH_ACT
prompt =================================
prompt
create sequence XXI.S_BRN_BIRTH_ACT
minvalue 0
maxvalue 9999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence S_CITY
prompt ========================
prompt
create sequence XXI.S_CITY
minvalue 0
maxvalue 9999999999999999999999999999
start with 10
increment by 1
cache 20;

prompt
prompt Creating sequence S_CITYANDAREA
prompt ===============================
prompt
create sequence XXI.S_CITYANDAREA
minvalue 0
maxvalue 9999999999999999999999999999
start with 10
increment by 1
cache 20;

prompt
prompt Creating sequence S_CUS
prompt =======================
prompt
create sequence XXI.S_CUS
minvalue 0
maxvalue 9999999999999999999999999999
start with 121
increment by 1
cache 20;

prompt
prompt Creating sequence S_CUS_ADDR
prompt ============================
prompt
create sequence XXI.S_CUS_ADDR
minvalue 0
maxvalue 9999999999999999999999999999
start with 121
increment by 1
cache 20;

prompt
prompt Creating sequence S_CUS_CITIZEN
prompt ===============================
prompt
create sequence XXI.S_CUS_CITIZEN
minvalue 0
maxvalue 9999999999999999999999999999
start with 121
increment by 1
cache 20;

prompt
prompt Creating sequence S_CUS_DOCUM
prompt =============================
prompt
create sequence XXI.S_CUS_DOCUM
minvalue 0
maxvalue 9999999999999999999999999999
start with 121
increment by 1
cache 20;

prompt
prompt Creating sequence S_DEATH_CERT
prompt ==============================
prompt
create sequence XXI.S_DEATH_CERT
minvalue 0
maxvalue 9999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence S_DIVORCE_CERT
prompt ================================
prompt
create sequence XXI.S_DIVORCE_CERT
minvalue 0
maxvalue 9999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence S_INF
prompt =======================
prompt
create sequence XXI.S_INF
minvalue 0
maxvalue 9999999999999999999999999999
start with 110
increment by 1
cache 20;

prompt
prompt Creating sequence S_INFANDAREA
prompt ==============================
prompt
create sequence XXI.S_INFANDAREA
minvalue 0
maxvalue 9999999999999999999999999999
start with 110
increment by 1
cache 20;

prompt
prompt Creating sequence S_LOGS
prompt ========================
prompt
create sequence XXI.S_LOGS
minvalue 0
maxvalue 9999999999999999999999999999
start with 321
increment by 1
cache 20;

prompt
prompt Creating sequence S_MC_MERCER
prompt =============================
prompt
create sequence XXI.S_MC_MERCER
minvalue 0
maxvalue 9999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence S_MENU_LIST
prompt =============================
prompt
create sequence XXI.S_MENU_LIST
minvalue 0
maxvalue 9999999999999999999999999999
start with 92
increment by 1
cache 20;

prompt
prompt Creating sequence S_NATIONALITY
prompt ===============================
prompt
create sequence XXI.S_NATIONALITY
minvalue 0
maxvalue 9999999999999999999999999999
start with 124
increment by 1
cache 20;

prompt
prompt Creating sequence S_NT_DOV
prompt ==========================
prompt
create sequence XXI.S_NT_DOV
minvalue 0
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence S_ODB_ACTION
prompt ==============================
prompt
create sequence XXI.S_ODB_ACTION
minvalue 0
maxvalue 9999999999999999999999999999
start with 201
increment by 1
cache 20;

prompt
prompt Creating sequence S_ODB_GROUP_USR
prompt =================================
prompt
create sequence XXI.S_ODB_GROUP_USR
minvalue 0
maxvalue 9999999999999999999999999999
start with 41
increment by 1
cache 20;

prompt
prompt Creating sequence S_ODB_MNU
prompt ===========================
prompt
create sequence XXI.S_ODB_MNU
minvalue 0
maxvalue 9999999999999999999999999999
start with 143
increment by 1
cache 20;

prompt
prompt Creating sequence S_PATERN_CERT
prompt ===============================
prompt
create sequence XXI.S_PATERN_CERT
minvalue 0
maxvalue 9999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence S_PROJECT
prompt ===========================
prompt
create sequence XXI.S_PROJECT
minvalue 1
maxvalue 9999999999999999999999999999
start with 142
increment by 1
cache 20;

prompt
prompt Creating sequence S_REPORTS
prompt ===========================
prompt
create sequence XXI.S_REPORTS
minvalue 0
maxvalue 9999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence S_UPDATE_ABH_NAME
prompt ===================================
prompt
create sequence XXI.S_UPDATE_ABH_NAME
minvalue 0
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence S_UPDATE_NAME
prompt ===============================
prompt
create sequence XXI.S_UPDATE_NAME
minvalue 0
maxvalue 9999999999999999999999999999
start with 41
increment by 1
cache 20;

prompt
prompt Creating sequence S_UPD_NAT
prompt ===========================
prompt
create sequence XXI.S_UPD_NAT
minvalue 0
maxvalue 9999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence S_USR
prompt =======================
prompt
create sequence XXI.S_USR
minvalue 0
maxvalue 9999999999999999999999999999
start with 203
increment by 1
cache 20;

prompt
prompt Creating sequence S_ZAGS
prompt ========================
prompt
create sequence XXI.S_ZAGS
minvalue 0
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating view ACTFORLIST
prompt ========================
prompt
create or replace force view xxi.actforlist as
select BR_ACT_ID, cus.ccusname, BR_ACT_DATE, cus.dcusbirthday
  from BRN_BIRTH_ACT t, cus
 where t.br_act_ch = cus.icusnum(+);
grant select on XXI.ACTFORLIST to ODB;


prompt
prompt Creating view ALL_DOCS
prompt ======================
prompt
create or replace force view xxi.all_docs as
with item as
 (select 89 Id from dual),
unins as
 (select 'brn_birth_act' table_name,
         'Свидетельство о рождении' DocName,
         brn_birth_act.br_act_id doc_ID,
         brn_birth_act.br_act_date doc_date
    from brn_birth_act, item
   where brn_birth_act.br_act_ch = item.Id
      or brn_birth_act.br_act_f = item.Id
      or brn_birth_act.br_act_m = item.Id
  union all
  select 'PATERN_CERT' table_name,
         'Установление отцовства' DocName,
         PATERN_CERT.pc_id doc_ID,
         PATERN_CERT.PС_DATE doc_date
    from PATERN_CERT, item
   where PATERN_CERT.pс_ch = item.Id
      or PATERN_CERT.pс_f = item.Id
      or PATERN_CERT.pс_m = item.Id
  union all
  select 'mc_mercer' table_name,
         'Заключение брака' DocName,
         mc_mercer.mercer_id doc_ID,
         mc_mercer.mercer_date doc_date
    from mc_mercer, item
   where mc_mercer.MERCER_HE = item.Id
      or mc_mercer.MERCER_SHE = item.Id
  union all
  select 'DIVORCE_CERT' table_name,
         'Расторжение брака' DocName,
         DIVORCE_CERT.Divc_Id doc_ID,
         DIVORCE_CERT.DIVC_DATE doc_date
    from DIVORCE_CERT, item
   where DIVORCE_CERT.DIVC_HE = item.Id
      or DIVORCE_CERT.DIVC_SHE = item.Id
  union all
  select 'DEATH_CERT' table_name,
         'Установление акта о смерти' DocName,
         DEATH_CERT.DC_ID doc_ID,
         DEATH_CERT.DC_OPEN doc_date
    from DEATH_CERT, item
   where DEATH_CERT.DC_CUS = item.Id
  union all
  select 'UPD_NAME' table_name,
         'Перемена имени' DocName,
         update_name.id doc_ID,
         update_name.doc_date doc_date
    from update_name, item
   where update_name.CUSID = item.Id
  union all
  select 'UPDATE_ABH_NAME' table_name,
         'Восстановление абхазской фамилии' DocName,
         UPDATE_ABH_NAME.ID doc_ID,
         UPDATE_ABH_NAME.DOC_DATE doc_date
    from UPDATE_ABH_NAME, item
   where UPDATE_ABH_NAME.CUSID = item.Id
  union all
  select 'UPD_NAT' table_name,
         'Перемена национальной принадлежности' DocName,
         UPD_NAT.ID doc_ID,
         UPD_NAT.DOC_DATE doc_date
    from UPD_NAT, item
   where UPD_NAT.CUSID = item.Id
  union all
  select 'ADOPT' table_name,
         'Усыновление (удочерение)' DocName,
         ADOPTOIN.ID doc_ID,
         ADOPTOIN.DOC_DATE doc_date
    from ADOPTOIN, item
   where ADOPTOIN.CUSID_CH = item.Id
      or ADOPTOIN.CUSID_F = item.Id
      or ADOPTOIN.CUSID_F_AD = item.Id
      or ADOPTOIN.CUSID_M = item.Id
      or ADOPTOIN.CUSID_M_AD = item.Id)
select upper(table_name) table_name, DocName, doc_ID, doc_date tm$doc_date
  from unins
 order by DOCNAME;
grant select on XXI.ALL_DOCS to ODB;


prompt
prompt Creating package AUP_UTIL
prompt =========================
prompt
CREATE OR REPLACE PACKAGE XXI.AUP_UTIL IS
  --
  -- утилиты для "аудита справочников"
  --
  Version CONSTANT VARCHAR2(250) := '$Id: aup_util.sql,v 2.12 2017/03/24 09:25:52 psh500 Exp $';
  --
  --
  --T_TabAu
  TYPE T_RecAu IS RECORD(
    cField   VARCHAR2(30),
    cNewData VARCHAR2(2000),
    cOldData VARCHAR2(2000));

  TYPE T_TabAu IS VARRAY(1000) OF T_RecAu;
  --
  --
  --
  FUNCTION PK(CNAME varchar2) return varchar2;
  dNullDate CONSTANT DATE := Date '1970-01-01';
  /*---*
  * возвращает текущий "режим аудита" для заданой таблицы
  *---*/
  FUNCTION Get_Mode(Tbl_Name IN VARCHAR2) RETURN CHAR;

  PROCEDURE Put_Data(Action_ID  IN INTEGER,
                     Field_Name IN VARCHAR2,
                     NewData    IN VARCHAR2,
                     OldData    IN VARCHAR2);

  PROCEDURE Put_CLOB_Data(Action_ID  IN INTEGER,
                          Field_Name IN VARCHAR2,
                          OldData    IN CLOB);

  PROCEDURE Put_BLOB_Data(Action_ID  IN INTEGER,
                          Field_Name IN VARCHAR2,
                          OldData    IN BLOB);

  /*---*
  * возвращает комментарий к таблице (для форм просмотра и настройки аудита)
  *---*/
  FUNCTION Get_Tab_Comment(Tab_Name IN VARCHAR2, Tab_Owner IN VARCHAR2)
    RETURN VARCHAR2;

  /*---*
  * возращает комментарий к полю таблицы (для формы просмотра аудита)
  *---*/
  FUNCTION Get_Col_Comment(Tab_Name   IN VARCHAR2,
                           Tab_Owner  IN VARCHAR2,
                           Field_Name IN VARCHAR2) RETURN VARCHAR2;

  /*---*
  * ищет аудиторский триггер и возвращает его статус
  * или NULL
  *---*/
  FUNCTION Trigger_Exists(Tab_Name IN VARCHAR2, Tab_Owner IN VARCHAR2)
    RETURN VARCHAR2;

  FUNCTION Obj_Status(Obj_Owner IN VARCHAR2,
                      Obj_Name  IN VARCHAR2,
                      Obj_Type  IN VARCHAR2) RETURN VARCHAR2;

  FUNCTION Is_Table(Tab_Owner IN VARCHAR2, Tab_Name IN VARCHAR2)
    RETURN BOOLEAN;
  --
  --
  --
  FUNCTION Trigger_Name(Tab_Name IN VARCHAR2) RETURN VARCHAR2;
  --
  -- Создание нового действа
  --
  FUNCTION NewAction(cTable   IN VARCHAR2,
                     cOp_Code IN VARCHAR2 DEFAULT NULL,
                     ID_Num   IN VARCHAR2 DEFAULT NULL,
                     ID_ANum  IN VARCHAR2 DEFAULT NULL,
                     ID_Row   IN ROWID DEFAULT NULL) RETURN INTEGER;
  --
  -- Создание нового действа в автономной транзакции
  --
  FUNCTION NewAction_AT(cTable   IN VARCHAR2,
                        cOp_Code IN VARCHAR2 DEFAULT NULL,
                        ID_Num   IN VARCHAR2 DEFAULT NULL,
                        ID_ANum  IN VARCHAR2 DEFAULT NULL,
                        ID_Row   IN ROWID DEFAULT NULL) RETURN INTEGER;
  --
  -- Запись лога
  --
  PROCEDURE Put_Log(ID_Action IN INTEGER,
                    i_Order   IN OUT INTEGER,
                    cObj      IN VARCHAR2,
                    cText     IN VARCHAR2);

  PROCEDURE Put_Log(ID_Action IN INTEGER,
                    i_Order   IN OUT INTEGER,
                    cText     IN VARCHAR2);
  --
  -- Запись лога в автономной транзакции
  --
  PROCEDURE Put_Log_AT(ID_Action IN INTEGER,
                       i_Order   IN OUT INTEGER,
                       cObj      IN VARCHAR2,
                       cText     IN VARCHAR2);

  PROCEDURE Put_Log_AT(ID_Action IN INTEGER,
                       i_Order   IN OUT INTEGER,
                       cText     IN VARCHAR2);
  --
  -- Аудит поля в авт тран
  --
  PROCEDURE Put_Data_AT(Action_ID  IN INTEGER,
                        Field_Name IN VARCHAR2,
                        NewData    IN VARCHAR2,
                        OldData    IN VARCHAR2 DEFAULT NULL);
  --
  -- Заполнение таблицы в памяти
  --
  PROCEDURE Put_Data(TabAu      IN OUT NOCOPY T_TabAu,
                     Field_Name IN VARCHAR2,
                     NewData    IN VARCHAR2,
                     OldData    IN VARCHAR2);
  --
  -- Запись аудита
  --
  PROCEDURE Put_Data(Action_ID IN INTEGER, TabAu IN T_TabAu);
  --
  -- Предыдущее значение
  --
  FUNCTION PreviousValue(cTable  IN VARCHAR2,
                         cField  IN VARCHAR2,
                         ID_Num  IN VARCHAR2 DEFAULT NULL,
                         ID_ANum IN VARCHAR2 DEFAULT NULL,
                         ID_Row  IN ROWID DEFAULT NULL) RETURN VARCHAR2;
  --
  -- Очистка лога
  --
  PROCEDURE DeleteLogAction(cTable  IN VARCHAR2,
                            ID_Num  IN VARCHAR2 DEFAULT NULL,
                            ID_ANum IN VARCHAR2 DEFAULT NULL);
  --
-- Удалить аудит, 0 - успех
--
--
--
--
END AUP_UTIL;
/
grant execute on XXI.AUP_UTIL to ODB;


prompt
prompt Creating view ALL_TABLE
prompt =======================
prompt
create or replace force view xxi.all_table as
select TABLE_NAME,
       AUP_UTIL.Get_Tab_Comment(upper(TABLE_NAME), 'XXI') TableComment
  from all_tables
 where OWNER = 'XXI'
 order by TABLE_NAME;
grant select on XXI.ALL_TABLE to ODB;


prompt
prompt Creating view AU_TABLE
prompt ======================
prompt
create or replace force view xxi.au_table as
select CNAME,
       AUP_UTIL.Get_Tab_Comment(upper(CNAME), 'XXI') tableName,
       AUP_UTIL.Trigger_Exists(Tab_Name  => upper(CNAME),
                               Tab_Owner => 'XXI') status,
       CMODE
  from AU_TABLES t;
grant select on XXI.AU_TABLE to ODB;


prompt
prompt Creating type LOB_COLUMN
prompt ========================
prompt
create or replace type xxi.lob_column force as object(
  offset number(5),
  width  number(5)
)
/

prompt
prompt Creating type LOB_COLUMNS
prompt =========================
prompt
create or replace type xxi.lob_columns is varray(100) of lob_column
/

prompt
prompt Creating type LOB_ROW
prompt =====================
prompt
create or replace type xxi.lob_row force as object(
  row_no    number(28),
  column1   varchar2(4000 byte),
  column2   varchar2(4000 byte),
  column3   varchar2(4000 byte),
  column4   varchar2(4000 byte),
  column5   varchar2(4000 byte),
  column6   varchar2(4000 byte),
  column7   varchar2(4000 byte),
  column8   varchar2(4000 byte),
  column9   varchar2(4000 byte),
  column10  varchar2(4000 byte),
  column11  varchar2(4000 byte),
  column12  varchar2(4000 byte),
  column13  varchar2(4000 byte),
  column14  varchar2(4000 byte),
  column15  varchar2(4000 byte),
  column16  varchar2(4000 byte),
  column17  varchar2(4000 byte),
  column18  varchar2(4000 byte),
  column19  varchar2(4000 byte),
  column20  varchar2(4000 byte),
  column21  varchar2(4000 byte),
  column22  varchar2(4000 byte),
  column23  varchar2(4000 byte),
  column24  varchar2(4000 byte),
  column25  varchar2(4000 byte),
  column26  varchar2(4000 byte),
  column27  varchar2(4000 byte),
  column28  varchar2(4000 byte),
  column29  varchar2(4000 byte),
  column30  varchar2(4000 byte),
  column31  varchar2(4000 byte),
  column32  varchar2(4000 byte),
  column33  varchar2(4000 byte),
  column34  varchar2(4000 byte),
  column35  varchar2(4000 byte),
  column36  varchar2(4000 byte),
  column37  varchar2(4000 byte),
  column38  varchar2(4000 byte),
  column39  varchar2(4000 byte),
  column40  varchar2(4000 byte),
  column41  varchar2(4000 byte),
  column42  varchar2(4000 byte),
  column43  varchar2(4000 byte),
  column44  varchar2(4000 byte),
  column45  varchar2(4000 byte),
  column46  varchar2(4000 byte),
  column47  varchar2(4000 byte),
  column48  varchar2(4000 byte),
  column49  varchar2(4000 byte),
  column50  varchar2(4000 byte),
  column51  varchar2(4000 byte),
  column52  varchar2(4000 byte),
  column53  varchar2(4000 byte),
  column54  varchar2(4000 byte),
  column55  varchar2(4000 byte),
  column56  varchar2(4000 byte),
  column57  varchar2(4000 byte),
  column58  varchar2(4000 byte),
  column59  varchar2(4000 byte),
  column60  varchar2(4000 byte),
  column61  varchar2(4000 byte),
  column62  varchar2(4000 byte),
  column63  varchar2(4000 byte),
  column64  varchar2(4000 byte),
  column65  varchar2(4000 byte),
  column66  varchar2(4000 byte),
  column67  varchar2(4000 byte),
  column68  varchar2(4000 byte),
  column69  varchar2(4000 byte),
  column70  varchar2(4000 byte),
  column71  varchar2(4000 byte),
  column72  varchar2(4000 byte),
  column73  varchar2(4000 byte),
  column74  varchar2(4000 byte),
  column75  varchar2(4000 byte),
  column76  varchar2(4000 byte),
  column77  varchar2(4000 byte),
  column78  varchar2(4000 byte),
  column79  varchar2(4000 byte),
  column80  varchar2(4000 byte),
  column81  varchar2(4000 byte),
  column82  varchar2(4000 byte),
  column83  varchar2(4000 byte),
  column84  varchar2(4000 byte),
  column85  varchar2(4000 byte),
  column86  varchar2(4000 byte),
  column87  varchar2(4000 byte),
  column88  varchar2(4000 byte),
  column89  varchar2(4000 byte),
  column90  varchar2(4000 byte),
  column91  varchar2(4000 byte),
  column92  varchar2(4000 byte),
  column93  varchar2(4000 byte),
  column94  varchar2(4000 byte),
  column95  varchar2(4000 byte),
  column96  varchar2(4000 byte),
  column97  varchar2(4000 byte),
  column98  varchar2(4000 byte),
  column99  varchar2(4000 byte),
  column100 varchar2(4000 byte),
  column101 varchar2(4000 byte),
  column102 varchar2(4000 byte),
  column103 varchar2(4000 byte),
  column104 varchar2(4000 byte),
  column105 varchar2(4000 byte),
  column106 varchar2(4000 byte),
  column107 varchar2(4000 byte),
  column108 varchar2(4000 byte),
  column109 varchar2(4000 byte),
  column110 varchar2(4000 byte),
  column111 varchar2(4000 byte),
  column112 varchar2(4000 byte),
  column113 varchar2(4000 byte),
  column114 varchar2(4000 byte),
  column115 varchar2(4000 byte),
  column116 varchar2(4000 byte),
  column117 varchar2(4000 byte),
  column118 varchar2(4000 byte),
  column119 varchar2(4000 byte),
  column120 varchar2(4000 byte),
  column121 varchar2(4000 byte),
  column122 varchar2(4000 byte),
  column123 varchar2(4000 byte),
  column124 varchar2(4000 byte),
  column125 varchar2(4000 byte),
  column126 varchar2(4000 byte),
  column127 varchar2(4000 byte),
  column128 varchar2(4000 byte),
  column129 varchar2(4000 byte),
  column130 varchar2(4000 byte),
  column131 varchar2(4000 byte),
  column132 varchar2(4000 byte),
  column133 varchar2(4000 byte),
  column134 varchar2(4000 byte),
  column135 varchar2(4000 byte),
  column136 varchar2(4000 byte),
  column137 varchar2(4000 byte),
  column138 varchar2(4000 byte),
  column139 varchar2(4000 byte),
  column140 varchar2(4000 byte),
  column141 varchar2(4000 byte),
  column142 varchar2(4000 byte),
  column143 varchar2(4000 byte),
  column144 varchar2(4000 byte),
  column145 varchar2(4000 byte),
  column146 varchar2(4000 byte),
  column147 varchar2(4000 byte),
  column148 varchar2(4000 byte),
  column149 varchar2(4000 byte),
  column150 varchar2(4000 byte),
  column151 varchar2(4000 byte),
  column152 varchar2(4000 byte),
  column153 varchar2(4000 byte),
  column154 varchar2(4000 byte),
  column155 varchar2(4000 byte),
  column156 varchar2(4000 byte),
  column157 varchar2(4000 byte),
  column158 varchar2(4000 byte),
  column159 varchar2(4000 byte),
  column160 varchar2(4000 byte),
  column161 varchar2(4000 byte),
  column162 varchar2(4000 byte),
  column163 varchar2(4000 byte),
  column164 varchar2(4000 byte),
  column165 varchar2(4000 byte),
  column166 varchar2(4000 byte),
  column167 varchar2(4000 byte),
  column168 varchar2(4000 byte),
  column169 varchar2(4000 byte),
  column170 varchar2(4000 byte),
  column171 varchar2(4000 byte),
  column172 varchar2(4000 byte),
  column173 varchar2(4000 byte),
  column174 varchar2(4000 byte),
  column175 varchar2(4000 byte),
  column176 varchar2(4000 byte),
  column177 varchar2(4000 byte),
  column178 varchar2(4000 byte),
  column179 varchar2(4000 byte),
  column180 varchar2(4000 byte),
  column181 varchar2(4000 byte),
  column182 varchar2(4000 byte),
  column183 varchar2(4000 byte),
  column184 varchar2(4000 byte),
  column185 varchar2(4000 byte),
  column186 varchar2(4000 byte),
  column187 varchar2(4000 byte),
  column188 varchar2(4000 byte),
  column189 varchar2(4000 byte),
  column190 varchar2(4000 byte),
  column191 varchar2(4000 byte),
  column192 varchar2(4000 byte),
  column193 varchar2(4000 byte),
  column194 varchar2(4000 byte),
  column195 varchar2(4000 byte),
  column196 varchar2(4000 byte),
  column197 varchar2(4000 byte),
  column198 varchar2(4000 byte),
  column199 varchar2(4000 byte),
  column200 varchar2(4000 byte),
  constructor function lob_row(p_row_no number) return self as result,
  member function get_column(p_column_no pls_integer) return varchar2,
  member function get_column_count return number
)
/

prompt
prompt Creating type LOB_ROWS
prompt ======================
prompt
create or replace type xxi.lob_rows is table of lob_row
/

prompt
prompt Creating package LOB2TABLE
prompt ==========================
prompt
create or replace package xxi.lob2table
is
  invalid_charset exception;
  pragma exception_init(invalid_charset, -20000);
  --
  row_error exception;
  pragma exception_init(row_error, -20001);
  --
  column_error exception;
  pragma exception_init(column_error, -20002);
  --
  function separatedcolumns(p_blob             blob,
                            p_row_separator    varchar2,
                            p_column_separator varchar2 := null,
                            p_character_set    varchar2 := null,
                            p_delimiter        varchar2 := null,
                            p_truncate_columns number := 0)
    return lob_rows pipelined;
  function separatedcolumns2(p_blob             blob,
                             p_row_separator    varchar2,
                             p_column_separator varchar2 := null,
                             p_character_set    varchar2 := null,
                             p_delimiter        varchar2 := null,
                             p_truncate_columns number := 0)
    return lob_rows;
  --
  function fixedcolumns(p_blob             blob,
                        p_row_separator    varchar2,
                        p_fixed_columns    lob_columns := null,
                        p_character_set    varchar2 := null,
                        p_truncate_columns number := 0)
    return lob_rows pipelined;
  function fixedcolumns2(p_blob             blob,
                         p_row_separator    varchar2,
                         p_fixed_columns    lob_columns := null,
                         p_character_set    varchar2 := null,
                         p_truncate_columns number := 0)
    return lob_rows;
  --
  function fixedcolumns(p_blob             blob,
                        p_row_width        number,
                        p_fixed_columns    lob_columns := null,
                        p_character_set    varchar2 := null,
                        p_truncate_columns number := 0)
    return lob_rows pipelined;
  function fixedcolumns2(p_blob             blob,
                         p_row_width        number,
                         p_fixed_columns    lob_columns := null,
                         p_character_set    varchar2 := null,
                         p_truncate_columns number := 0)
    return lob_rows;
  --
  function separatedcolumns(p_bfile bfile,
                            p_row_separator    varchar2,
                            p_column_separator varchar2 := null,
                            p_character_set    varchar2 := null,
                            p_delimiter        varchar2 := null,
                            p_truncate_columns number := 0)
    return lob_rows pipelined;
  function separatedcolumns2(p_bfile bfile,
                             p_row_separator    varchar2,
                             p_column_separator varchar2 := null,
                             p_character_set    varchar2 := null,
                             p_delimiter        varchar2 := null,
                             p_truncate_columns number := 0)
    return lob_rows;
  --
  function fixedcolumns(p_bfile            bfile,
                        p_row_separator    varchar2,
                        p_fixed_columns    lob_columns := null,
                        p_character_set    varchar2 := null,
                        p_truncate_columns number := 0)
    return lob_rows pipelined;
  function fixedcolumns2(p_bfile            bfile,
                         p_row_separator    varchar2,
                         p_fixed_columns    lob_columns := null,
                         p_character_set    varchar2 := null,
                         p_truncate_columns number := 0)
    return lob_rows;
  --
  function fixedcolumns(p_bfile            bfile,
                        p_row_width        number,
                        p_fixed_columns    lob_columns := null,
                        p_character_set    varchar2 := null,
                        p_truncate_columns number := 0)
    return lob_rows pipelined;
  function fixedcolumns2(p_bfile            bfile,
                         p_row_width        number,
                         p_fixed_columns    lob_columns := null,
                         p_character_set    varchar2 := null,
                         p_truncate_columns number := 0)
    return lob_rows;
  --
  function separatedcolumns(p_clob             clob,
                            p_row_separator    varchar2,
                            p_column_separator varchar2 := null,
                            p_delimiter        varchar2 := null,
                            p_truncate_columns number := 0)
    return lob_rows pipelined;
  function separatedcolumns2(p_clob             clob,
                             p_row_separator    varchar2,
                             p_column_separator varchar2 := null,
                             p_delimiter        varchar2 := null,
                             p_truncate_columns number := 0)
    return lob_rows;
  --
  function fixedcolumns(p_clob             clob,
                        p_row_separator    varchar2,
                        p_fixed_columns    lob_columns := null,
                        p_truncate_columns number := 0)
    return lob_rows pipelined;
  function fixedcolumns2(p_clob             clob,
                         p_row_separator    varchar2,
                         p_fixed_columns    lob_columns := null,
                         p_truncate_columns number := 0)
    return lob_rows;
  --
  function fixedcolumns(p_clob             clob,
                        p_row_width        number,
                        p_fixed_columns    lob_columns := null,
                        p_truncate_columns number := 0)
    return lob_rows pipelined;
  function fixedcolumns2(p_clob             clob,
                         p_row_width        number,
                         p_fixed_columns    lob_columns := null,
                         p_truncate_columns number := 0)
    return lob_rows;
  --
  function separatedcolumns(p_string           varchar2,
                            p_row_separator    varchar2,
                            p_column_separator varchar2 := null,
                            p_delimiter        varchar2 := null,
                            p_truncate_columns number := 0)
    return lob_rows pipelined;
  function separatedcolumns2(p_string           varchar2,
                             p_row_separator    varchar2,
                             p_column_separator varchar2 := null,
                             p_delimiter        varchar2 := null,
                             p_truncate_columns number := 0)
    return lob_rows;
  --
  function fixedcolumns(p_string           varchar2,
                        p_row_separator    varchar2,
                        p_fixed_columns    lob_columns := null,
                        p_truncate_columns number := 0)
    return lob_rows pipelined;
  function fixedcolumns2(p_string           varchar2,
                         p_row_separator    varchar2,
                         p_fixed_columns    lob_columns := null,
                         p_truncate_columns number := 0)
    return lob_rows;
  --
  function fixedcolumns(p_string           varchar2,
                        p_row_width        number,
                        p_fixed_columns    lob_columns := null,
                        p_truncate_columns number := 0)
    return lob_rows pipelined;
  function fixedcolumns2(p_string           varchar2,
                         p_row_width        number,
                         p_fixed_columns    lob_columns := null,
                         p_truncate_columns number := 0)
    return lob_rows;
  --
  function get_number(p_string   varchar2,
                      p_on_error number := null,
                      p_format   varchar2 := null,
                      p_nls      varchar2 := null,
                      p_format2  varchar2 := null,
                      p_nls2     varchar2 := null,
                      p_format3  varchar2 := null,
                      p_nls3     varchar2 := null)
    return number;
  --
  function get_date(p_string   varchar2,
                    p_on_error date := null,
                    p_format   varchar2 := null,
                    p_nls      varchar2 := null,
                    p_format2  varchar2 := null,
                    p_nls2     varchar2 := null,
                    p_format3  varchar2 := null,
                    p_nls3     varchar2 := null)
    return date;
  --
  function get_timestamp(p_string   varchar2,
                         p_on_error timestamp_unconstrained := null,
                         p_format   varchar2 := null,
                         p_nls      varchar2 := null,
                         p_format2  varchar2 := null,
                         p_nls2     varchar2 := null,
                         p_format3  varchar2 := null,
                         p_nls3     varchar2 := null)
    return timestamp_unconstrained;
  --
  function get_timestamp_tz(p_string   varchar2,
                            p_on_error timestamp_tz_unconstrained := null,
                            p_format   varchar2 := null,
                            p_nls      varchar2 := null,
                            p_format2  varchar2 := null,
                            p_nls2     varchar2 := null,
                            p_format3  varchar2 := null,
                            p_nls3     varchar2 := null)
    return timestamp_tz_unconstrained;
  --
  function get_dsinterval(p_string   varchar2,
                          p_on_error dsinterval_unconstrained := null)
    return dsinterval_unconstrained;
  --
  function get_yminterval(p_string   varchar2,
                          p_on_error yminterval_unconstrained := null)
    return yminterval_unconstrained;
  --
  function string_to_hex(p_string varchar2) return varchar2;
  --
  function hex_to_string(p_hex varchar2) return varchar2;
end lob2table;
/
grant execute on XXI.LOB2TABLE to ODB;


prompt
prompt Creating type LISTAGG_CLOB_T
prompt ============================
prompt
CREATE OR REPLACE TYPE XXI."LISTAGG_CLOB_T"                                          as object
( t_varchar2 varchar2(32767)
, t_clob clob
, static function odciaggregateinitialize( sctx in out listagg_clob_t )
  return number
, member function odciaggregateiterate
    ( self in out listagg_clob_t
    , a_val varchar2
    )
  return number
, member function odciaggregateterminate
    ( self in out listagg_clob_t
    , returnvalue out clob
    , flags in number
    )
  return number
, member function odciaggregatemerge
    ( self in out listagg_clob_t
    , ctx2 in out listagg_clob_t
    )
  return number
)
/

prompt
prompt Creating function DATE2STRABH
prompt =============================
prompt
CREATE OR REPLACE FUNCTION XXI.DATE2STRABH(DDAY IN DATE) RETURN VARCHAR2 IS
  RUS_MONTH VARCHAR2(4000);
BEGIN
  BEGIN
    SELECT TO_CHAR(DDAY, 'DD') || ' ' || T.MNTH_ABH || ' ' ||
           TO_CHAR(DDAY, 'YYYY') || ' ш.'
      INTO RUS_MONTH
      FROM MONTHS_ABH T
     WHERE T.MNTH = TO_CHAR(DDAY, 'MM');
  EXCEPTION
    WHEN OTHERS THEN
      RUS_MONTH := SQLERRM;
  END;

  RETURN RUS_MONTH;

END DATE2STRABH;
/
grant execute on XXI.DATE2STRABH to ODB;


prompt
prompt Creating function DATE2STRABH2
prompt ==============================
prompt
CREATE OR REPLACE FUNCTION XXI.DATE2STRABH2(DDAY IN DATE) RETURN VARCHAR2 IS
  RUS_MONTH VARCHAR2(4000);
BEGIN
  BEGIN
    SELECT T.MNTH_ABH
      INTO RUS_MONTH
      FROM MONTHS_ABH T
     WHERE T.MNTH = TO_CHAR(DDAY, 'MM');
  EXCEPTION
    WHEN OTHERS THEN
      RUS_MONTH := SQLERRM;
  END;

  RETURN RUS_MONTH;

END DATE2STRABH2;
/
grant execute on XXI.DATE2STRABH2 to ODB;


prompt
prompt Creating function NUM2STR
prompt =========================
prompt
create or replace function xxi.num2str(p_Num Number, p_Cur Char)
  return varchar2 IS
  /*************** Функция строкового представления числа ***************/
  /*                                                                    */
  /* Автор: Щербаков И.Б. Дата создания - 17.03.98 г.                   */
  /*                                                                    */
  /* Входные параметры:                                                 */
  /* p_Num - сумма, которую необходимо представить в строковом виде     */
  /* p_Cur - код валюты ISO                                             */
  /*                                                                    */
  /* Формат результата:                                                 */
  /* 1. Строковое представление целой части суммы                       */
  /* 2. Наименование валюты                                             */
  /* 3. Численное представление двух разрядов дробной части суммы       */
  /* 4. Наименование вспомогательной валютной единицы                   */
  /*                                                                    */
  /* Если p_Num is null, то выводится "ноль"                            */
  /* Если p_Cur is null, то выводится только строковое представление    */
  /*   целой части суммы                                                */
  /*                                                                    */
  /* Наименование валюты и вспомогательной валютной единицы берется из  */
  /* таблицы DMS:                                                       */
  /*   iDMSpos = 6 - наименование валюты,                               */
  /*   iDMSpos = 7 - наименование вспомогательной валютной единицы      */
  /*                                                                    */
  /* pako 25.05.06, использование CDMSVAL%TYPE вместо VARCHAR2(40)      */
  /* в русских словах латинское p заменено на русское р                 */
  /*                                                                    */
  type string_tabletype is table of varchar2(500) index by binary_integer;
  unt1         string_tabletype; /* единицы мужского рода */
  unt2         string_tabletype; /* единицы женского рода */
  hnd          string_tabletype; /* сотни */
  dcs          string_tabletype; /* десятки */
  dc1          string_tabletype; /* десять..девятнадцать */
  dms1         string_tabletype; /* 'один': тысячи, миллионы... */
  dms2         string_tabletype; /* 'два, три, четыре': тысячи, миллионы... */
  dms3         string_tabletype; /* 'пять, шесть...': тысячи, миллионы... */
  v_string     varchar2(500) := null;
  v_OutStr     varchar2(500) := null;
  v_triple_str varchar2(500);
  v_tmp        varchar2(500) := null;
  v_negative   varchar2(500) := null;
  v_triple_ok  boolean;
  v_triple_num number;
  v_digit1     varchar2(500);
  v_digit2     varchar2(500);
  v_digit3     varchar2(500);
  v_length     number;
  v_variant    number;
  --
  procedure set_unt1 is
  begin
    unt1(1) := 'один';
    unt1(2) := 'два';
    unt1(3) := 'три';
    unt1(4) := 'четыре';
    unt1(5) := 'пять';
    unt1(6) := 'шесть';
    unt1(7) := 'семь';
    unt1(8) := 'восемь';
    unt1(9) := 'девять';
  end set_unt1;
  --
  procedure set_unt2 is
  begin
    unt2(1) := 'одна';
    unt2(2) := 'две';
    unt2(3) := 'три';
    unt2(4) := 'четыре';
    unt2(5) := 'пять';
    unt2(6) := 'шесть';
    unt2(7) := 'семь';
    unt2(8) := 'восемь';
    unt2(9) := 'девять';
  end set_unt2;
  --
  procedure set_hnd is
  begin
    hnd(1) := 'сто';
    hnd(2) := 'двести';
    hnd(3) := 'триста';
    hnd(4) := 'четыреста';
    hnd(5) := 'пятьсот';
    hnd(6) := 'шестьсот';
    hnd(7) := 'семьсот';
    hnd(8) := 'восемьсот';
    hnd(9) := 'девятьсот';
  end set_hnd;
  --
  procedure set_dcs is
  begin
    dcs(2) := 'двадцать';
    dcs(3) := 'тридцать';
    dcs(4) := 'сорок';
    dcs(5) := 'пятьдесят';
    dcs(6) := 'шестьдесят';
    dcs(7) := 'семьдесят';
    dcs(8) := 'восемьдесят';
    dcs(9) := 'девяносто';
  end set_dcs;
  --
  procedure set_dc1 is
  begin
    dc1(0) := 'десять';
    dc1(1) := 'одиннадцать';
    dc1(2) := 'двенадцать';
    dc1(3) := 'тринадцать';
    dc1(4) := 'четырнадцать';
    dc1(5) := 'пятнадцать';
    dc1(6) := 'шестнадцать';
    dc1(7) := 'семнадцать';
    dc1(8) := 'восемнадцать';
    dc1(9) := 'девятнадцать';
  end set_dc1;
  --
  procedure set_dms1 is
  begin
    dms1(6) := 'квадриллион';
    dms1(5) := 'триллион';
    dms1(4) := 'миллиард';
    dms1(3) := 'миллион';
    dms1(2) := 'тысяча';
  end set_dms1;
  --
  procedure set_dms2 is
  begin
    dms2(6) := 'квадриллиона';
    dms2(5) := 'триллиона';
    dms2(4) := 'миллиарда';
    dms2(3) := 'миллиона';
    dms2(2) := 'тысячи';
  end set_dms2;
  --
  procedure set_dms3 is
  begin
    dms3(6) := 'квадриллионов';
    dms3(5) := 'триллионов';
    dms3(4) := 'миллиардов';
    dms3(3) := 'миллионов';
    dms3(2) := 'тысяч';
  end set_dms3;
  --
  procedure set_all is
  begin
    set_unt1;
    set_unt2;
    set_hnd;
    set_dcs;
    set_dc1;
    set_dms1;
    set_dms2;
    set_dms3;
  end;
  --
  function SetDigit(p_pos number) return varchar2 is
  begin
    if p_pos > 0 then
      return(nvl(SubStr(v_string, p_pos, 1), '0'));
    else
      return('0');
    end if;
  end SetDigit;
  --
  Function Upper1(p_str varchar2) return varchar2 is
    v_char varchar2(500);
  begin
    if p_str = 'н' then
      v_char := 'Н';
    elsif p_str = 'о' then
      v_char := 'О';
    elsif p_str = 'д' then
      v_char := 'Д';
    elsif p_str = 'т' then
      v_char := 'Т';
    elsif p_str = 'ч' then
      v_char := 'Ч';
    elsif p_str = 'п' then
      v_char := 'П';
    elsif p_str = 'ш' then
      v_char := 'Ш';
    elsif p_str = 'с' then
      v_char := 'С';
    elsif p_str = 'в' then
      v_char := 'В';
    end if;
    return(v_char);
  end Upper1;
  --
  Function Cur_Rod return number is
    v_rod number;
  begin
    if (p_Cur is null) then
      return(1);
    end if;
    select iDMSrod
      into v_rod
      from DMS
     where iDMSpos = 6
       and cDMSiso = p_Cur
       and rownum = 1;
    return(v_rod);
  exception
    when others then
      return(1);
  end Cur_Rod;
  --
  Function Choose_Cur(p_variant number) return varchar2 is
    -- v_str varchar2(40);
    v_str DMS.CDMSVAL%TYPE;
  begin
    if (p_Cur is null) then
      return(null);
    end if;
    select cDMSval
      into v_str
      from DMS
     where iDMSpos = 6
       and cDMSiso = p_cur
       and iDMSqnt = p_variant;
    return(v_str);
  exception
    when others then
      return(null);
  end Choose_Cur;
  --
  Function Cop return varchar2 is
    v_result  varchar2(500);
    v_num_str varchar2(500);
    --v_cur_str varchar2(40);
    v_cur_str DMS.CDMSVAL%TYPE;
    v_val1    varchar2(1) := null;
    v_val2    varchar2(1) := null;
    v_var     number;
  begin
    if (p_Cur is null) then
      return(null);
    end if;
    if (p_num is null) then
      v_num_str := '00';
    else
      v_num_str := to_char(trunc(abs(p_Num) * 100), '99999999999999999900');
    end if;
    v_val1   := substr(v_num_str, length(v_num_str) - 1, 1);
    v_val2   := substr(v_num_str, length(v_num_str), 1);
    v_result := v_val1 || v_val2;
    v_var    := 3; /* много */
    if v_val1 <> '1' and v_val2 <> '0' then
      if v_val2 = '1' then
        v_var := 1 /* один */
         ;
      elsif v_val2 in ('2', '3', '4') then
        v_var := 2 /* два, три, четыре */
         ;
      end if;
    end if;
    begin
      select cDMSval
        into v_cur_str
        from DMS
       where iDMSpos = 7
         and iDMSqnt = v_var
         and cDMSiso = p_Cur;
    exception
      when others then
        v_cur_str := null;
    end;
    if v_cur_str is not null then
      v_result := v_result || ' ' || v_cur_str;
    end if;
    return(v_result);
  end Cop;
  --
  function if_zero_null return varchar2 is
    v_result varchar2(500);
    -- v_str    varchar2(40);
    v_str DMS.CDMSVAL%TYPE;
  begin
    begin
      if p_cur is not null then
        select cDMSval
          into v_str
          from DMS
         where iDMSpos = 6
           and cDMSiso = p_cur
           and iDMSqnt = 3;
      end if;
    exception
      when others then
        v_str := null;
    end;
    v_result := 'ноль ';
    if v_str is not null then
      v_result := v_result || v_str || ' ';
    end if;
    return(v_result);
  end if_zero_null;
  --
begin
  set_all;
  if p_Num < 0 then
    v_negative := 'Минус ';
  end if;
  v_triple_ok := true;
  if (trunc(p_Num) = 0 or p_Num is null) then
    v_OutStr    := if_zero_null;
    v_triple_ok := false;
  end if;
  v_string := to_char(trunc(Abs(p_Num)));
  if length(v_string) > 18 then
    v_string := SubStr(v_string, length(v_string) - 17, 18);
  end if;
  v_length     := length(v_string);
  v_triple_num := 1;
  while v_triple_ok loop
    v_triple_str := '';
    v_digit1     := SetDigit(v_length - (2 + 3 * (v_triple_num - 1)));
    v_digit2     := SetDigit(v_length - (1 + 3 * (v_triple_num - 1)));
    v_digit3     := SetDigit(v_length - (0 + 3 * (v_triple_num - 1)));
    if v_digit1 = '0' and v_digit2 = '0' and v_digit3 = '0' then
      if (v_triple_num = 1 and trunc(Abs(p_Num)) > 0) then
        v_triple_str := choose_cur(3) || ' ';
      else
        v_triple_str := null;
      end if;
      if (3 * (v_triple_num - 1) + 1) > v_length then
        v_triple_ok := false;
      end if;
    else
      v_variant := 3; /* много */
      if v_digit1 <> '0' then
        v_tmp        := hnd(to_number(v_digit1)) || ' ';
        v_triple_str := v_tmp;
      end if;
      if v_digit2 = '1' then
        v_tmp        := dc1(to_number(v_digit3)) || ' ';
        v_triple_str := v_triple_str || v_tmp;
      else
        if v_digit2 <> '0' then
          v_tmp        := dcs(to_number(v_digit2)) || ' ';
          v_triple_str := v_triple_str || v_tmp;
        end if;
        if v_digit3 <> '0' then
          if v_triple_num = 2 /* тысячи */
             or (v_triple_num = 1 and Cur_Rod = 2) then
            v_tmp := unt2(to_number(v_digit3)) || ' ';
          else
            v_tmp := unt1(to_number(v_digit3)) || ' ';
          end if;
          v_triple_str := v_triple_str || v_tmp;
          if v_digit3 = '1' then
            v_variant := 1 /* один */
             ;
          elsif v_digit3 in ('2', '3', '4') then
            v_variant := 2 /* два, три, четыре */
             ;
          end if;
        end if;
      end if;
      if v_triple_num <> 1 then
        if v_variant = 1 then
          v_tmp := dms1(v_triple_num);
        elsif v_variant = 2 then
          v_tmp := dms2(v_triple_num);
        elsif v_variant = 3 then
          v_tmp := dms3(v_triple_num);
        end if;
      else
        v_tmp := choose_cur(v_variant);
      end if;
      if v_tmp is not null then
        v_triple_str := v_triple_str || v_tmp || ' ';
      end if;
    end if;
    if v_triple_str is not null then
      v_OutStr := v_triple_str || v_OutStr;
    end if;
    if v_triple_ok then
      v_triple_num := v_triple_num + 1;
    end if;
  end loop;
  if v_negative is null then
    v_OutStr := Upper1(SubStr(v_OutStr, 1, 1)) || SubStr(v_OutStr, 2) || cop;
  else
    v_OutStr := v_negative || v_OutStr || cop;
  end if;
  return(v_OutStr);
end num2str;
/
grant execute on XXI.NUM2STR to ODB;


prompt
prompt Creating function DATE2STR_F
prompt ============================
prompt
CREATE OR REPLACE FUNCTION XXI.DATE2STR_F(d IN DATE) RETURN VARCHAR2 IS
-- *****************************************************************************
-- перевод даты в строку
-- 14.07.2006 = Четырнадцатое июля две тысячи шестого года

-- Автор: Полников Игорь
-- Дата создания: 14.07.2006
-- *****************************************************************************
  result VARCHAR2(500) := NULL;

-- исходные числа
  num VARCHAR2(2);
  month VARCHAR2(2);
  year VARCHAR2(4);

-- результирующие строки
  num_s VARCHAR2(100);
  month_s VARCHAR2(100);
  year_s VARCHAR2(100);

  TYPE string_tabletype is
    table of varchar2(60) index by binary_integer;
  unt string_tabletype;
  unt2 string_tabletype;
BEGIN
  IF d IS NULL THEN
    RETURN NULL;
  END IF;

-- константы для числа
  unt(1) := 'первое';
  unt(2) := 'второе';
  unt(3) := 'третье';
  unt(4) := 'четвертое';
  unt(5) := 'пятое';
  unt(6) := 'шестое';
  unt(7) := 'седьмое';
  unt(8) := 'восьмое';
  unt(9) := 'девятое';
  unt(10) := 'десятое';
  unt(11) := 'одиннадцатое';
  unt(12) := 'двенадцатое';
  unt(13) := 'тринадцатое';
  unt(14) := 'четырнадцатое';
  unt(15) := 'пятнадцатое';
  unt(16) := 'шестнадцатое';
  unt(17) := 'семнадцатое';
  unt(18) := 'восемнадцатое';
  unt(19) := 'девятнадцатое';
  unt(20) := 'двадцатое';
  unt(21) := 'тридцатое';
  unt(22) := 'двадцать';
  unt(23) := 'тридцать';

-- константы для года
  -- единицы
  unt2(1) := 'первого';
  unt2(2) := 'второго';
  unt2(3) := 'третьего';
  unt2(4) := 'четвертого';
  unt2(5) := 'пятого';
  unt2(6) := 'шестого';
  unt2(7) := 'седьмого';
  unt2(8) := 'восьмого';
  unt2(9) := 'девятого';
  unt2(10) := 'десятого';
  unt2(11) := 'одиннадцатого';
  unt2(12) := 'двенадцатого';
  unt2(13) := 'тринадцатого';
  unt2(14) := 'четырнадцатого';
  unt2(15) := 'пятнадцатого';
  unt2(16) := 'шестнадцатого';
  unt2(17) := 'семнадцатого';
  unt2(18) := 'восемнадцатого';
  unt2(19) := 'девятнадцатого';

  -- десятки
  unt2(20) := 'двадцатого';
  unt2(21) := 'тридцатого';
  unt2(22) := 'сорокового';
  unt2(23) := 'пятидесятого';
  unt2(24) := 'шестидесятого';
  unt2(25) := 'семидесятого';
  unt2(26) := 'восьмидесятого';
  unt2(27) := 'девяностого';

  unt2(28) := 'двадцать';
  unt2(29) := 'тридцать';
  unt2(30) := 'сорок';
  unt2(31) := 'пятьдесят';
  unt2(32) := 'шестьдесят';
  unt2(33) := 'семьдесят';
  unt2(34) := 'восемьдесят';
  unt2(35) := 'девяносто';

  -- сотни
  unt2(36) := 'сотого';
  unt2(37) := 'двухсотого';
  unt2(38) := 'трехсотого';
  unt2(39) := 'четырехсотого';
  unt2(40) := 'пятисотого';
  unt2(41) := 'шестисотого';
  unt2(42) := 'семисотого';
  unt2(43) := 'восьмисотого';
  unt2(44) := 'девятисотого';

  -- тысячи
  unt2(45) := 'тысячного';
  unt2(46) := 'двухтысячного';
  unt2(47) := 'трехтысячного';
  unt2(48) := 'четырехтысячного';
  unt2(49) := 'пятитысячного';
  unt2(50) := 'шеститысячного';
  unt2(51) := 'семитысячного';
  unt2(52) := 'восьмитысячного';
  unt2(53) := 'девятитысячного';

  num := TO_CHAR(d, 'DD');
  month := TO_CHAR(d, 'MM');
  year := TO_CHAR(d, 'YYYY');

-- обработка числа
  IF TO_NUMBER(num) BETWEEN 1 AND 20 THEN
    num_s := unt(TO_NUMBER(num));
  ELSIF TO_NUMBER(num) = 30 THEN
    num_s := unt(21);
  ELSE
    IF SUBSTR(num, 1, 1) = '2' THEN
      num_s := unt(22);
    ELSE
      num_s := unt(23);
    END IF;
    num_s := num_s || ' ' || unt(TO_NUMBER(SUBSTR(num, 2)));
  END IF;

-- обработка месяца
SELECT DECODE(month,
              '01',
              'января',
              '02',
              'февраля',
              '03',
              'марта',
              '04',
              'апреля',
              '05',
              'мая',
              '06',
              'июня',
              '07',
              'июля',
              '08',
              'августа',
              '09',
              'сентября',
              '10',
              'октября',
              '11',
              'ноября',
              '12',
              'декабря')
  INTO month_s
  FROM dual;

-- обработка года
IF SUBSTR(year, 2) = '000' THEN
  -- тысячный год
  year_s := unt2(44 + TO_NUMBER(SUBSTR(year, 1, 1)));
ELSIF SUBSTR(year, 3) = '00' THEN
  -- сотый год
  year_s := num2str(TRUNC(TO_NUMBER(year), -3), NULL) || ' ' ||
            unt2(35 + TO_NUMBER(SUBSTR(year, 2, 1)));
ELSIF SUBSTR(year, 4) = '0' THEN
  -- десятый год
  year_s := num2str(TRUNC(TO_NUMBER(year), -2), NULL) || ' ';
  IF SUBSTR(year, 3, 1) = '1' THEN
    year_s := year_s || unt2(10);
  ELSE
    year_s := year_s || unt2(18 + TO_NUMBER(SUBSTR(year, 3, 1)));
  END IF;
ELSE
  -- нет нулей на конце
  year_s := num2str(TRUNC(TO_NUMBER(year), -2), NULL) || ' ';
  IF TO_NUMBER(SUBSTR(year, 3, 2)) BETWEEN 1 AND 19 THEN
    year_s := year_s || unt2(TO_NUMBER(SUBSTR(year, 3, 2)));
  ELSE
    year_s := year_s || unt2(26 + TO_NUMBER(SUBSTR(year, 3, 1))) || ' ' ||
              unt2(TO_NUMBER(SUBSTR(year, 4, 1)));
  END IF;
END IF;

result := LOWER(num_s || ' ' || month_s || ' ' || year_s || ' года');
result := UPPER(SUBSTR(result, 1, 1)) || SUBSTR(result, 2);
result := REPLACE(result, '   ', ' '); -- лишние пробелы
result := REPLACE(result, '  ', ' '); -- лишние пробелы

RETURN result;
END;
/
grant execute on XXI.DATE2STR_F to ODB;


prompt
prompt Creating function DATE2STR2
prompt ===========================
prompt
CREATE OR REPLACE FUNCTION XXI.Date2Str2(dDay IN DATE) RETURN VARCHAR2 IS
  Rus_Month VARCHAR2(4000);
BEGIN
  BEGIN
    SELECT DECODE(TO_CHAR(dDay, 'MM'),
                  '01',
                  'января ',
                  '02',
                  'февраля ',
                  '03',
                  'марта ',
                  '04',
                  'апреля ',
                  '05',
                  'мая ',
                  '06',
                  'июня ',
                  '07',
                  'июля ',
                  '08',
                  'августа ',
                  '09',
                  'сентября ',
                  '10',
                  'октября ',
                  '11',
                  'ноября ',
                  '12',
                  'декабря ')
      INTO Rus_Month
      FROM SYS.DUAL;
  EXCEPTION
    WHEN OTHERS THEN
      Rus_Month := sqlerrm;
  END;

  RETURN Rus_Month;

END Date2Str2;
/
grant execute on XXI.DATE2STR2 to ODB;


prompt
prompt Creating function LISTAGG_CLOB
prompt ==============================
prompt
create or replace function xxi.listagg_clob(agg varchar2) return clob
  parallel_enable
  aggregate using listagg_clob_t;
/
grant execute on XXI.LISTAGG_CLOB to ODB;


prompt
prompt Creating view BLANK_BRN_BIRTH_ACT
prompt =================================
prompt
CREATE OR REPLACE FORCE VIEW XXI.BLANK_BRN_BIRTH_ACT AS
SELECT CUS.ICUSNUM,
       BR_ACT.BR_ACT_ID,
       CUS.AB_LAST_NAME F72,
       CUS.AB_FIRST_NAME || ' ' || CUS.AB_MIDDLE_NAME F73,
       DATE2STRABH(CUS.DCUSBIRTHDAY) F74,
       CUS.AB_PLACE_BIRTH F76,
       CASE
         WHEN CUS.DCUSBIRTHDAY < '26.12.1991' AND CNTR.NAME = 'Абхазия' THEN
          'Абхазской АССР'
         WHEN CNTR.NAME = 'Абхазия' THEN
          CNTR.ABH
       END F77,
       TO_CHAR(BR_ACT.BR_ACT_DATE, 'yyyy') F79,
       DATE2STRABH2(BR_ACT.BR_ACT_DATE) TEXT2,
       TO_CHAR(BR_ACT.BR_ACT_DATE, 'dd') F80,
       F.AB_LAST_NAME F81,
       F.AB_FIRST_NAME || ' ' || F.AB_MIDDLE_NAME F82,
       CASE
         WHEN F.DCUSBIRTHDAY < '26.12.1991' AND CNTR_F.NAME = 'Абхазия' THEN
          'Абхазской АССР'
         WHEN CNTR_F.NAME = 'Абхазия' THEN
          CNTR_F.ABH
       END F83,
       LOWER(NAT_F.HE_AB) F84,
       M.AB_LAST_NAME F85,
       M.AB_FIRST_NAME || ' ' || M.AB_MIDDLE_NAME F86,
       CASE
         WHEN F.DCUSBIRTHDAY < '26.12.1991' AND CNTR_F.NAME = 'Абхазия' THEN
          'Абхазской АССР'
         WHEN CNTR_F.NAME = 'Абхазия' THEN
          CNTR_F.ABH
       END F87,
       LOWER(NAT_F.HE_AB) F88,
       ZAGS.ZAGS_CITY_ABH F89,
       ZAGS.ZAGS_ADR_ABH F90,
       USR.FIO_ABH_SH F3,
       USR.FIO_SH F4,
       CUS.CCUSLAST_NAME F93,
       CUS.CCUSFIRST_NAME || ' ' || CUS.CCUSMIDDLE_NAME F94,
       TO_CHAR(CUS.DCUSBIRTHDAY, 'dd.mm.yyyy') || ' г.' F95,
       (SELECT REPLACE(CAST(LISTAGG_CLOB(COLUMN1) AS VARCHAR2(100)),
                       ',',
                       ' ')
          FROM TABLE(LOB2TABLE.SEPARATEDCOLUMNS(LOWER(DATE2STR_F(BR_ACT.BR_ACT_DATE)), /* THE DATA LOB */
                                                ' ', /* ROW SEPARATOR */
                                                ',', /* COLUMN SEPARATOR */
                                                '"' /* DELIMITER (OPTIONAL) */))
         WHERE ROW_NO <= 2) F96,
       (SELECT REPLACE(CAST(LISTAGG_CLOB(COLUMN1) AS VARCHAR2(100)),
                       ',',
                       ' ')
          FROM TABLE(LOB2TABLE.SEPARATEDCOLUMNS(LOWER(DATE2STR_F(BR_ACT.BR_ACT_DATE)), /* THE DATA LOB */
                                                ' ', /* ROW SEPARATOR */
                                                ',', /* COLUMN SEPARATOR */
                                                '"' /* DELIMITER (OPTIONAL) */))
         WHERE ROW_NO > 2) F97,
       CUS.CCUSPLACE_BIRTH F98,
       CASE
         WHEN CUS.DCUSBIRTHDAY < '26.12.1991' AND CNTR.NAME = 'Абхазия' THEN
          'Абхазской АССР'
         WHEN CNTR.NAME = 'Абхазия' THEN
          CNTR.NAME_FULL
       END F99,
       DATE2STR2(BR_ACT.BR_ACT_DATE) F101,
       F.CCUSLAST_NAME F104,
       F.CCUSFIRST_NAME || ' ' || F.CCUSMIDDLE_NAME F105,
       'гражданин ' || CNTR_F.NAME_FULL_ROD F106,
       LOWER(NAT_F.HE) F107,
       M.CCUSLAST_NAME F108,
       m.CCUSFIRST_NAME || ' ' || M.CCUSMIDDLE_NAME F109,
       'гражданка ' || CNTR_F.NAME_FULL_ROD F110,
       LOWER(NAT_F.SHE) F111,
       zags.ADDR || ' Республики Абхазия' F113
  FROM CUS,
       BRN_BIRTH_ACT BR_ACT,
       COUNTRIES     CNTR,
       COUNTRIES     CNTR_F,
       COUNTRIES     CNTR_M,
       NATIONALITY   NAT,
       NATIONALITY   NAT_F,
       NATIONALITY   NAT_M,
       ZAGS,
       USR,
       CUS           F,
       CUS           M
 WHERE BR_ACT.BR_ACT_CH = CUS.ICUSNUM(+)
   AND CNTR.CODE = CUS.CCUS_OK_SM
   AND CNTR_F.CODE = F.CCUS_OK_SM
   AND CNTR_M.CODE = M.CCUS_OK_SM
   AND USR.CUSRLOGNAME = /*UPD.OPER*/
       USER
   AND NAT_F.ID = F.CCUSNATIONALITY
   AND NAT_M.ID = M.CCUSNATIONALITY
   AND NAT.ID = CUS.CCUSNATIONALITY
   AND ZAGS.ZAGS_ID = BR_ACT.BR_ACT_ZGID
   AND F.ICUSNUM(+) = BR_ACT.BR_ACT_F
   AND M.ICUSNUM(+) = BR_ACT.BR_ACT_M;
grant select on XXI.BLANK_BRN_BIRTH_ACT to ODB;


prompt
prompt Creating view BLANK_DEATH_CERT
prompt ==============================
prompt
CREATE OR REPLACE FORCE VIEW XXI.BLANK_DEATH_CERT AS
SELECT CUS.ICUSNUM,
       DETH.DC_ID,
       CUS.AB_LAST_NAME,
       CUS.AB_FIRST_NAME || ' ' || CUS.AB_MIDDLE_NAME ABH_FMNAME,
       CASE
         WHEN CNTR.NAME = 'Абхазия' THEN
          CNTR.NAME_ABH
       END AS ABH_COUNTRY,
       TO_CHAR(CUS.DCUSBIRTHDAY, 'DD') BR_DD,
       DATE2STRABH2(CUS.DCUSBIRTHDAY) ABH_MM,
       TO_CHAR(CUS.DCUSBIRTHDAY, 'YYYY') BR_YYYY,
       CASE
         WHEN CUS.DCUSBIRTHDAY < '26.12.1991' AND CNTR.NAME = 'Абхазия' THEN
          CUS.AB_PLACE_BIRTH || ' ' || CNTR.USSR
         ELSE
          CUS.AB_PLACE_BIRTH || ' ' || CNTR.NAME_FULL_ROD
       END ABH_BRTH_PLC,
       USR.FIO_ABH_SH,
       DATE2STRABH(DETH.DC_DD) DETH_DATE,
       TO_CHAR(DETH.DC_OPEN, 'yyyy') DETH_OPEN_YYYY,
       TO_CHAR(DETH.DC_OPEN, 'mm') DETH_OPEN_MM,
       TO_CHAR(DETH.DC_OPEN, 'dd') DETH_OPEN_DD,
       ZAGS.ADDR_ABH,
       DATE2STRABH2(DETH.DC_OPEN) ABH_DC_OPEN,
       ZAGS.ZAGS_CITY_ABH,
       (SELECT COLUMN1
          FROM ZAGS,
               TABLE(LOB2TABLE.SEPARATEDCOLUMNS(ZAGS.ZAGS_ADR_ABH, /* THE DATA LOB */
                                                ' ', /* ROW SEPARATOR */
                                                ',', /* COLUMN SEPARATOR */
                                                '"' /* DELIMITER (OPTIONAL) */))
         WHERE ROW_NO = 1
           AND ZAGS.ZAGS_ID = DETH.DC_ZAGS) F23,
       (SELECT REPLACE(CAST(LISTAGG_CLOB(COLUMN1) AS VARCHAR2(100)),
                       ',',
                       ' ')
          FROM ZAGS,
               TABLE(LOB2TABLE.SEPARATEDCOLUMNS(ZAGS.ZAGS_ADR_ABH, /* THE DATA LOB */
                                                ' ', /* ROW SEPARATOR */
                                                ',', /* COLUMN SEPARATOR */
                                                '"' /* DELIMITER (OPTIONAL) */))
         WHERE ROW_NO > 1
           AND ZAGS.ZAGS_ID = DETH.DC_ZAGS) F24,
       CUS.CCUSLAST_NAME F25,
       CUS.CCUSFIRST_NAME || ' ' || CUS.CCUSMIDDLE_NAME F26,
       CASE
         WHEN CUS.CCUSSEX = 2 THEN
          'гражданка ' || CNTR.NAME_FULL_ROD
         WHEN CUS.CCUSSEX = 1 THEN
          'гражданин ' || CNTR.NAME_FULL_ROD
       END AS F27,
       DATE2STR2(CUS.DCUSBIRTHDAY) F29,
       CUS.CCUSPLACE_BIRTH F31,
       CASE
         WHEN CUS.DCUSBIRTHDAY < '26.12.1991' AND CNTR.NAME = 'Абхазия' THEN
          'Абхазской АССР'
       END F32,
       TO_CHAR(DETH.DC_DD, 'dd.mm.yyyy') || ' г.' F33,
       (SELECT REPLACE(CAST(LISTAGG_CLOB(COLUMN1) AS VARCHAR2(100)),
                       ',',
                       ' ')
          FROM TABLE(LOB2TABLE.SEPARATEDCOLUMNS(LOWER(DATE2STR_F(DETH.DC_DD)), /* THE DATA LOB */
                                                ' ', /* ROW SEPARATOR */
                                                ',', /* COLUMN SEPARATOR */
                                                '"' /* DELIMITER (OPTIONAL) */))
         WHERE ROW_NO <= 3) F34,
       (SELECT REPLACE(CAST(LISTAGG_CLOB(COLUMN1) AS VARCHAR2(100)),
                       ',',
                       ' ')
          FROM TABLE(LOB2TABLE.SEPARATEDCOLUMNS(LOWER(DATE2STR_F(DETH.DC_DD)), /* THE DATA LOB */
                                                ' ', /* ROW SEPARATOR */
                                                ',', /* COLUMN SEPARATOR */
                                                '"' /* DELIMITER (OPTIONAL) */))
         WHERE ROW_NO > 3) F35,
       DATE2STR2(DETH.DC_OPEN) F37,
       TO_CHAR(DETH.DC_OPEN, 'dd') F38,
       ZAGS.ZAGS_ADR F40,
       ZAGS.ADDR F44,
       USR.FIO_SH F46
  FROM CUS, DEATH_CERT DETH, COUNTRIES CNTR, NATIONALITY NAT, ZAGS, USR
 WHERE DETH.DC_CUS = CUS.ICUSNUM
   AND CNTR.CODE = CUS.CCUS_OK_SM
   AND USR.CUSRLOGNAME = /*UPD.OPER*/
       USER
   AND NAT.ID = CUS.CCUSNATIONALITY
   AND ZAGS.ZAGS_ID = DETH.DC_ZAGS;
grant select on XXI.BLANK_DEATH_CERT to ODB;


prompt
prompt Creating view BLANK_DIVORCE_CERT
prompt ================================
prompt
CREATE OR REPLACE FORCE VIEW XXI.BLANK_DIVORCE_CERT AS
SELECT HE.ICUSNUM, DV.DIVC_ID
  FROM CUS          HE,
       DIVORCE_CERT DV,
       COUNTRIES    CNTR_HE,
       COUNTRIES    CNTR_SHE,
       NATIONALITY  NAT_HE,
       NATIONALITY  NAT_SHE,
       ZAGS,
       USR,
       CUS          SHE
 WHERE DV.DIVC_HE = HE.ICUSNUM(+)
   AND DV.DIVC_SHE = SHE.ICUSNUM(+)
   AND CNTR_HE.CODE(+) = HE.CCUS_OK_SM
   AND CNTR_SHE.CODE(+) = SHE.CCUS_OK_SM
   AND USR.CUSRLOGNAME = /*UPD.OPER*/
       USER
   AND NAT_HE.ID(+) = HE.CCUSNATIONALITY
   AND NAT_SHE.ID(+) = SHE.CCUSNATIONALITY
   AND ZAGS.ZAGS_ID(+) = DV.DIVC_ZAGS;
grant select on XXI.BLANK_DIVORCE_CERT to ODB;


prompt
prompt Creating view BLANK_MC_MERCER
prompt =============================
prompt
CREATE OR REPLACE FORCE VIEW XXI.BLANK_MC_MERCER AS
SELECT HE.ICUSNUM,
       MC.MERCER_ID,
       HE.AB_LAST_NAME F6,
       HE.AB_FIRST_NAME || ' ' || HE.AB_MIDDLE_NAME F7,
       CNTR_HE.NAME_ABH F9,
       LOWER(NAT_HE.HE_AB) F10,
       TO_CHAR(HE.DCUSBIRTHDAY, 'dd') F11,
       DATE2STRABH2(HE.DCUSBIRTHDAY) F12,
       TO_CHAR(HE.DCUSBIRTHDAY, 'yyyy') F64,
       HE.AB_PLACE_BIRTH || ' ' || CNTR_HE.ABH F13,
       SHE.AB_LAST_NAME F14,
       SHE.AB_FIRST_NAME || ' ' || SHE.AB_MIDDLE_NAME F15,
       CNTR_SHE.NAME_ABH F16,
       LOWER(NAT_SHE.SHE_AB) F17,
       TO_CHAR(SHE.DCUSBIRTHDAY, 'dd') F18,
       DATE2STRABH2(SHE.DCUSBIRTHDAY) F19,
       TO_CHAR(SHE.DCUSBIRTHDAY, 'yyyy') F67,
       SHE.AB_PLACE_BIRTH || ' ' || CNTR_SHE.ABH F20,
       DATE2STRABH(MC.MC_DATE) F21,
       TO_CHAR(MC.MERCER_DATE, 'yyyy') F25,
       DATE2STRABH2(MC.MERCER_DATE) F26,
       TO_CHAR(MC.MERCER_DATE,'DD') F1,
       ZAGS.ZAGS_CITY_ABH F30,
       ZAGS.ZAGS_ADR_ABH F31,
       USR.FIO_ABH_SH F2,
       USR.FIO_SH F3,
       HE.CCUSLAST_NAME F35,
       HE.CCUSFIRST_NAME || ' ' || HE.CCUSMIDDLE_NAME F36,
       'гражданин ' || CNTR_HE.NAME_FULL_ROD F37,
       NAT_HE.HE F38,
       DATE2STR2(HE.DCUSBIRTHDAY) F39,
       HE.CCUSPLACE_BIRTH || ' ' || CNTR_HE.NAME_FULL F42,
       SHE.CCUSLAST_NAME F43,
       SHE.CCUSFIRST_NAME || ' ' || SHE.CCUSMIDDLE_NAME F44,
       'гражданка ' || CNTR_SHE.NAME_FULL_ROD F45,
       LOWER(NAT_SHE.SHE) F46,
       DATE2STR2(SHE.DCUSBIRTHDAY) F48,
       SHE.CCUSPLACE_BIRTH || ' ' || CNTR_SHE.NAME_FULL F49,
       TO_CHAR(MC.MC_DATE, 'dd.mm.yyyy') || ' г. ' ||
       (SELECT REPLACE(CAST(LISTAGG_CLOB(COLUMN1) AS VARCHAR2(100)),
                       ',',
                       ' ')
          FROM TABLE(LOB2TABLE.SEPARATEDCOLUMNS(LOWER(DATE2STR_F(MC.MC_DATE)), /* THE DATA LOB */
                                                ' ', /* ROW SEPARATOR */
                                                ',', /* COLUMN SEPARATOR */
                                                '"' /* DELIMITER (OPTIONAL) */))
         WHERE ROW_NO <= 2) F51,
       (SELECT REPLACE(CAST(LISTAGG_CLOB(COLUMN1) AS VARCHAR2(100)),
                       ',',
                       ' ')
          FROM TABLE(LOB2TABLE.SEPARATEDCOLUMNS(LOWER(DATE2STR_F(MC.MC_DATE)), /* THE DATA LOB */
                                                ' ', /* ROW SEPARATOR */
                                                ',', /* COLUMN SEPARATOR */
                                                '"' /* DELIMITER (OPTIONAL) */))
         WHERE ROW_NO > 2) F52,
       date2str2(MC.MC_DATE) F54,
       zags.ADDR || ' Республики Абхазия' F61
  FROM CUS         HE,
       MC_MERCER   MC,
       COUNTRIES   CNTR_HE,
       COUNTRIES   CNTR_SHE,
       NATIONALITY NAT_HE,
       NATIONALITY NAT_SHE,
       ZAGS,
       USR,
       CUS         SHE
 WHERE MC.MERCER_HE = HE.ICUSNUM(+)
   AND MC.MERCER_SHE = SHE.ICUSNUM(+)
   AND CNTR_HE.CODE(+) = HE.CCUS_OK_SM
   AND CNTR_SHE.CODE(+) = SHE.CCUS_OK_SM
   AND USR.CUSRLOGNAME = /*UPD.OPER*/
       USER
   AND NAT_HE.ID(+) = HE.CCUSNATIONALITY
   AND NAT_SHE.ID(+) = SHE.CCUSNATIONALITY
   AND ZAGS.ZAGS_ID(+) = MC.MERCER_ZAGS;
grant select on XXI.BLANK_MC_MERCER to ODB;


prompt
prompt Creating view BLANK_UPDATE_NAME
prompt ===============================
prompt
CREATE OR REPLACE FORCE VIEW XXI.BLANK_UPDATE_NAME AS
SELECT CUS.ICUSNUM,
       UPD.ID,
       UPD.OLD_LASTNAME AS RU_LNAME,
       UPD.OLD_FIRSTNAME || ' ' || UPD.OLD_MIDDLNAME AS RU_FMNAME,
       CASE
         WHEN CUS.CCUSSEX = 2 THEN
          'гражданка ' || CNTR.NAME_FULL_ROD
         WHEN CUS.CCUSSEX = 1 THEN
          'гражданин ' || CNTR.NAME_FULL_ROD
       END AS RU_COUNTRY,
       CASE
         WHEN CUS.CCUSSEX = 1 THEN
          LOWER(NAT.HE)
         WHEN CUS.CCUSSEX = 2 THEN
          LOWER(NAT.SHE)
       END AS RU_NAT,
       TO_CHAR(CUS.DCUSBIRTHDAY, 'DD') AS DD_BIRTH,
       DATE2STR2(CUS.DCUSBIRTHDAY) AS RU_MM_BIRTH,
       (SELECT T.MNTH_ABH
          FROM MONTHS_ABH T
         WHERE T.MNTH = TO_CHAR(CUS.DCUSBIRTHDAY, 'MM')) AS AB_MM_BIRTH,
       TO_CHAR(CUS.DCUSBIRTHDAY, 'YYYY') YYYY_BIRTH,
       CUS.CCUSPLACE_BIRTH AS RU_BRTH_PLC,
       CASE
         WHEN CUS.DCUSBIRTHDAY < '26.12.1991' AND CNTR.NAME = 'Абхазия' THEN
          'Абхазской АССР'
       END RU_CNTR_BRTH,
       UPD.NEW_LASTNAME,
       UPD.NEW_FIRSTNAME || ' ' || UPD.NEW_MIDDLNAME AS RU_FMNAME_NEW,
       TO_CHAR(UPD.DOC_DATE, 'YYYY') RU_YYYY,
       DATE2STR2(UPD.DOC_DATE) RU_MM,
       TO_CHAR(UPD.DOC_DATE, 'DD') RU_DD,
       ZAGS.ZAGS_ADR,
       /*ZAGS.ZAGS_RUK*/
       USR.FIO_SH ZAGS_RUK,
       --ABH
       /*ZAGS.ZAGS_RUK_ABH*/
       USR.FIO_ABH_SH ZAGS_RUK_ABH,
       UPD.OLD_LASTNAME_AB AS ABH_LNAME,
       UPD.OLD_FIRSTNAME_AB || ' ' || UPD.OLD_MIDDLNAME_AB AS ABH_FMNAME,
       CASE
         WHEN CNTR.NAME = 'Абхазия' THEN
          CNTR.NAME_ABH
       END AS ABH_COUNTRY,
       NAT.HE_AB AS ABH_NAT,
       DATE2STRABH2(CUS.DCUSBIRTHDAY) ABH_MM_BIRTH,
       CASE
         WHEN CUS.DCUSBIRTHDAY < '26.12.1991' AND CNTR.NAME = 'Абхазия' THEN
          CUS.AB_PLACE_BIRTH || ' ' || CNTR.USSR
         ELSE
          CUS.AB_PLACE_BIRTH || ' ' || CNTR.NAME_FULL_ROD
       END ABH_BRTH_PLC,
       UPD.NEW_LASTNAME_AB,
       UPD.NEW_FIRSTNAME_AB || ' ' || UPD.NEW_MIDDLNAME_AB AS ABH_FMNAME_NEW,
       DATE2STRABH2(UPD.DOC_DATE) ABH_MM,
       ZAGS.ZAGS_CITY_ABH,
       ZAGS.ZAGS_ADR_ABH
  FROM CUS, UPDATE_NAME UPD, COUNTRIES CNTR, NATIONALITY NAT, ZAGS, USR
 WHERE UPD.CUSID = CUS.ICUSNUM
   AND USR.CUSRLOGNAME = /*UPD.OPER*/
       USER
   AND CNTR.CODE = CUS.CCUS_OK_SM
   AND NAT.ID = CUS.CCUSNATIONALITY
   AND ZAGS.ZAGS_ID = UPD.ZAGS_ID
;
grant select on XXI.BLANK_UPDATE_NAME to ODB;


prompt
prompt Creating view BLANK_UPD_NAT
prompt ===========================
prompt
CREATE OR REPLACE FORCE VIEW XXI.BLANK_UPD_NAT AS
SELECT CUS.ICUSNUM,
       UPD.ID,
       CUS.CCUSLAST_NAME AS RU_LNAME,
       CUS.CCUSFIRST_NAME || ' ' || CUS.CCUSMIDDLE_NAME AS RU_FMNAME,
       CASE
         WHEN CUS.CCUSSEX = 2 THEN
          'гражданка ' || CNTR.NAME_FULL_ROD
         WHEN CUS.CCUSSEX = 1 THEN
          'гражданин ' || CNTR.NAME_FULL_ROD
       END AS RU_COUNTRY,
       CASE
         WHEN CUS.CCUSSEX = 1 THEN
          LOWER(NAT.HE)
         WHEN CUS.CCUSSEX = 2 THEN
          LOWER(NAT.SHE)
       END AS RU_NAT,
       TO_CHAR(CUS.DCUSBIRTHDAY, 'DD') AS DD_BIRTH,
       DATE2STR2(CUS.DCUSBIRTHDAY) AS RU_MM_BIRTH,
       (SELECT T.MNTH_ABH
          FROM MONTHS_ABH T
         WHERE T.MNTH = TO_CHAR(CUS.DCUSBIRTHDAY, 'MM')) AS AB_MM_BIRTH,
       TO_CHAR(CUS.DCUSBIRTHDAY, 'YYYY') YYYY_BIRTH,
       CUS.CCUSPLACE_BIRTH AS RU_BRTH_PLC,
       CASE
         WHEN CUS.DCUSBIRTHDAY < '26.12.1991' AND CNTR.NAME = 'Абхазия' THEN
          'Абхазской АССР'
       END RU_CNTR_BRTH,
       TO_CHAR(UPD.DOC_DATE, 'YYYY') RU_YYYY,
       DATE2STR2(UPD.DOC_DATE) RU_MM,
       TO_CHAR(UPD.DOC_DATE, 'DD') RU_DD,
       ZAGS.ZAGS_ADR,
       /*ZAGS.ZAGS_RUK*/
       USR.FIO_SH ZAGS_RUK,
       --ABH
       /*ZAGS.ZAGS_RUK_ABH*/
       USR.FIO_ABH_SH ZAGS_RUK_ABH,
       CUS.AB_LAST_NAME AS ABH_LNAME,
       CUS.AB_FIRST_NAME || ' ' || CUS.AB_MIDDLE_NAME AS ABH_FMNAME,
       CASE
         WHEN CNTR.NAME = 'Абхазия' THEN
          CNTR.NAME_ABH
       END AS ABH_COUNTRY,
       NAT.HE_AB AS ABH_NAT,
       DATE2STRABH2(CUS.DCUSBIRTHDAY) ABH_MM_BIRTH,
       CASE
         WHEN CUS.DCUSBIRTHDAY < '26.12.1991' AND CNTR.NAME = 'Абхазия' THEN
          CUS.AB_PLACE_BIRTH || '' || CNTR.USSR
         ELSE
          CUS.AB_PLACE_BIRTH || ' ' || CNTR.NAME_FULL_ROD
       END ABH_BRTH_PLC,
       DATE2STRABH2(UPD.DOC_DATE) ABH_MM,
       ZAGS.ZAGS_CITY_ABH,
       ZAGS.ZAGS_ADR_ABH,
       LOWER(NAT2.HE_AB) ABH_OLD_NAT,
       CUS.AB_PLACE_BIRTH,
       LOWER(NAT.HE_AB) ABH_NEW_NAT,
       TO_CHAR(UPD.DOC_DATE, 'MM') MM,
       TO_CHAR(UPD.DOC_DATE, 'DD') DD,
       ZAGS.ZAGS_CITY_ABH AS ZAGS_CITY_ABH2,
       CNTR.ABH,
       DATE2STRABH2(UPD.DOC_DATE) DOC_DATE,
       LOWER(NAT2.HE) HE,
       CNTR.NAME_FULL,
       ZAGS.ADDR
  FROM CUS,
       UPD_NAT     UPD,
       COUNTRIES   CNTR,
       NATIONALITY NAT,
       ZAGS,
       NATIONALITY NAT2,
       USR
 WHERE UPD.CUSID = CUS.ICUSNUM
   AND CNTR.CODE = CUS.CCUS_OK_SM
   AND NAT2.ID = UPD.OLD_NAT
   AND USR.CUSRLOGNAME = /*UPD.OPER*/
       USER
   AND NAT.ID = CUS.CCUSNATIONALITY
   AND ZAGS.ZAGS_ID = UPD.ZAGS_ID
;
grant select on XXI.BLANK_UPD_NAT to ODB;


prompt
prompt Creating package MJCUS
prompt ======================
prompt
create or replace package xxi.MJCUS is

  -- Author  : SAID
  -- Created : 08.10.2020 21:32:05
  -- Purpose : Клиенты, и иные документы
  function ADD_CUS_CITIZEN_TEMP(COUNTRY_CODE_ VARCHAR2, /*Страна*/
                                COUNTRY_NAME_ VARCHAR2, /*Полное наименование*/
                                osn_          VARCHAR2) return varchar2;
  function EDIT_CUS_CITIZEN_TEMP(COUNTRY_CODE_ VARCHAR2, /*Страна*/
                                 COUNTRY_NAME_ VARCHAR2 /*Полное наименование*/,
                                 ID_           number, /*ID*/
                                 osn_          VARCHAR2) return varchar2;
  function ADD_CUS_DOCUM_TEMP(PREF_       VARCHAR2, /*  Признак основного документа-"Y" */
                              ID_DOC_TP_  NUMBER, /*  ID типа документа */
                              DOC_NUM_    VARCHAR2, /*  Номер документа */
                              DOC_SER_    VARCHAR2, /*  Серия документа */
                              DOC_DATE_   DATE, /*  Дата выдачи */
                              DOC_AGENCY_ VARCHAR2, /*  Выдавший орган  */
                              DOC_PERIOD_ DATE, /*  Период действия */
                              DOC_SUBDIV_ VARCHAR2 /*  Код подразделение, выдавшего документ */)
    return varchar2;
  function ADD_CUS_DOCUM(PREF_       VARCHAR2, /*  Признак основного документа-"Y" */
                         ID_DOC_TP_  NUMBER, /*  ID типа документа */
                         DOC_NUM_    VARCHAR2, /*  Номер документа */
                         DOC_SER_    VARCHAR2, /*  Серия документа */
                         DOC_DATE_   DATE, /*  Дата выдачи */
                         DOC_AGENCY_ VARCHAR2, /*  Выдавший орган  */
                         DOC_PERIOD_ DATE, /*  Период действия */
                         DOC_SUBDIV_ VARCHAR2, /*  Код подразделение, выдавшего документ */
                         CUSID       number) return varchar2;
  function EDIT_CUS_DOCUM_TEMP(PREF_       VARCHAR2, /*  Признак основного документа-"Y" */
                               ID_DOC_TP_  NUMBER, /*  ID типа документа */
                               DOC_NUM_    VARCHAR2, /*  Номер документа */
                               DOC_SER_    VARCHAR2, /*  Серия документа */
                               DOC_DATE_   DATE, /*  Дата выдачи */
                               DOC_AGENCY_ VARCHAR2, /*  Выдавший орган  */
                               DOC_PERIOD_ DATE, /*  Период действия */
                               DOC_SUBDIV_ VARCHAR2, /*  Код подразделение, выдавшего документ */
                               ID_DOC_     number /*ID документа*/)
    return varchar2;
  function EDIT_CUS_DOCUM(PREF_       VARCHAR2, /*  Признак основного документа-"Y" */
                          ID_DOC_TP_  NUMBER, /*  ID типа документа */
                          DOC_NUM_    VARCHAR2, /*  Номер документа */
                          DOC_SER_    VARCHAR2, /*  Серия документа */
                          DOC_DATE_   DATE, /*  Дата выдачи */
                          DOC_AGENCY_ VARCHAR2, /*  Выдавший орган  */
                          DOC_PERIOD_ DATE, /*  Период действия */
                          DOC_SUBDIV_ VARCHAR2, /*  Код подразделение, выдавшего документ */
                          ID_DOC_     number /*ID документа*/)
    return varchar2;
  function ADD_CUS(DCUSBIRTHDAY_    DATE, /*  Дата рождения (для физ.лиц) */
                   CCUSLAST_NAME_   VARCHAR2, /*  Фамилия */
                   CCUSFIRST_NAME_  VARCHAR2, /*  Имя */
                   CCUSMIDDLE_NAME_ VARCHAR2, /*  Отчество  */
                   CCUSNATIONALITY_ VARCHAR2, /*  Национальность  */
                   CCUSSEX_         VARCHAR2, /*  Пол */
                   CCUSPLACE_BIRTH_ VARCHAR2, /*  Место рождения  */
                   ICUSOTD_         VARCHAR2, /*  ссылка на номер отделения */
                   CCUS_OK_SM_      number, /*  Код страны рождения */
                   COUNTRY_         number, /*  Страна  */
                   AREA_            VARCHAR2, /*  Район */
                   CITY_            VARCHAR2, /*  Город */
                   PUNCT_NAME_      VARCHAR2, /*  Нас. пункт  */
                   INFR_NAME_       VARCHAR2, /*  Инфраструктура  */
                   DOM_             VARCHAR2, /*  Дом */
                   KORP_            VARCHAR2, /*  Корпус  */
                   KV_              VARCHAR2, /*  Квартира  */
                   ID               out number,
                   AB_FIRST_NAME_   VARCHAR2,
                   AB_MIDDLE_NAME_  VARCHAR2,
                   AB_LAST_NAME_    VARCHAR2,
                   AB_PLACE_BIRTH_  VARCHAR2) return varchar2;
  function UPDATE_CUS(DCUSBIRTHDAY_    DATE, /*  Дата рождения (для физ.лиц) */
                      CCUSLAST_NAME_   VARCHAR2, /*  Фамилия */
                      CCUSFIRST_NAME_  VARCHAR2, /*  Имя */
                      CCUSMIDDLE_NAME_ VARCHAR2, /*  Отчество  */
                      CCUSNATIONALITY_ VARCHAR2, /*  Национальность  */
                      CCUSSEX_         VARCHAR2, /*  Пол */
                      CCUSPLACE_BIRTH_ VARCHAR2, /*  Место рождения  */
                      ICUSOTD_         VARCHAR2, /*  ссылка на номер отделения */
                      CCUS_OK_SM_      number, /*  Код страны рождения */
                      COUNTRY_         number, /*  Страна  */
                      AREA_            VARCHAR2, /*  Район */
                      CITY_            VARCHAR2, /*  Город */
                      PUNCT_NAME_      VARCHAR2, /*  Нас. пункт  */
                      INFR_NAME_       VARCHAR2, /*  Инфраструктура  */
                      DOM_             VARCHAR2, /*  Дом */
                      KORP_            VARCHAR2, /*  Корпус  */
                      KV_              VARCHAR2, /*  Квартира  */
                      CUSID            number,
                      AB_FIRST_NAME_   VARCHAR2,
                      AB_MIDDLE_NAME_  VARCHAR2,
                      AB_LAST_NAME_    VARCHAR2,
                      AB_PLACE_BIRTH_  VARCHAR2) return varchar2;
  procedure DelLog(PACKNAME VARCHAR2 default null);
  function ADD_CUS_CITIZEN(COUNTRY_CODE_ VARCHAR2, /*Страна*/
                           COUNTRY_NAME_ VARCHAR2, /*Полное наименование*/
                           CUSID         number,
                           osn_          VARCHAR2) return varchar2;
  function EDIT_CUS_CITIZEN(COUNTRY_CODE_ VARCHAR2, /*Страна*/
                            COUNTRY_NAME_ VARCHAR2 /*Полное наименование*/,
                            ID_           number, /*ID*/
                            osn_          VARCHAR2) return varchar2;
  procedure DelTempDocCit;
  procedure DelTempDocCit(CUS_CITIZEN_TEMP_ out number,
                          CUS_DOCUM_TEMP_   out number);

  procedure RetXmls(cusid       number,
                    error       out varchar2,
                    cus         out clob,
                    cus_addr    out clob,
                    cus_citizen out clob,
                    cus_docum   out clob);
  procedure CompareXmls(cusid       in number,
                        cus         in clob,
                        cus_addr    in clob,
                        cus_citizen in clob,
                        cus_docum   in clob,
                        error       out varchar2,
                        res         out number);
  function Address(ID number) return varchar2;

  TYPE T_RecCusList IS RECORD(
    CCUSNATIONALITY NUMBER, /*Национальность*/
    CCUS_OK_SM      CHAR(3), /*Код страны рождения*/
    ICUSOTD         NUMBER, /*ссылка на номер отделения*/
    CCUSPLACE_BIRTH VARCHAR2(250), /*Место рождения*/
    CCUSSEX         NUMBER, /*Пол*/
    CCUSMIDDLE_NAME VARCHAR2(30), /*Отчество*/
    CCUSFIRST_NAME  VARCHAR2(30), /*Имя*/
    CCUSLAST_NAME   VARCHAR2(100), /*Фамилия*/
    DCUSBIRTHDAY    DATE, /*Дата рождения (для физ.лиц)*/
    CCUSNAME_SH     VARCHAR2(250), /*Краткое наименование клиента*/
    CCUSCOUNTRY1    VARCHAR2(3), /*Страна местонахождения/Гражданство*/
    CCUSNAME        VARCHAR2(500), /*Название*/
    CCUSIDOPEN      VARCHAR2(30), /*Кто завел*/
    DCUSEDIT        DATE, /*Дата окончания срока действия документов*/
    DCUSOPEN        DATE, /*Дата заведения*/
    ICUSNUM         NUMBER /*Уникальный номер клиента*/);
  TYPE T_TabCusList IS TABLE OF T_RecCusList;
  FUNCTION CheckBeforeAdd(last_name   in varchar2,
                          first_name  in varchar2,
                          middle_name in varchar2,
                          birth_date  in date) RETURN T_TabCusList
    PIPELINED;
  --удаление гражданина и связанных документов
  procedure DelCus(error out VARCHAR2, cusid_n in number);
end MJCUS;
/
grant execute on XXI.MJCUS to ODB;


prompt
prompt Creating view CHECKBEFOREADD
prompt ============================
prompt
create or replace force view xxi.checkbeforeadd as
select "CCUSNATIONALITY",
       "CCUS_OK_SM",
       "ICUSOTD",
       "CCUSPLACE_BIRTH",
       "CCUSSEX",
       "CCUSMIDDLE_NAME",
       "CCUSFIRST_NAME",
       "CCUSLAST_NAME",
       "DCUSBIRTHDAY",
       "CCUSNAME_SH",
       "CCUSCOUNTRY1",
       "CCUSNAME",
       "CCUSIDOPEN",
       "DCUSEDIT",
       "DCUSOPEN",
       "ICUSNUM"
  from table(MJCUS.CheckBeforeAdd(last_name   => 'пачулияsdsd',
                                  first_name  => 'саидsdsd',
                                  middle_name => 'викторовичdd',
                                  birth_date  => to_date('30.04.1996',
                                                         'dd.mm.yyyy')));
grant select on XXI.CHECKBEFOREADD to ODB;


prompt
prompt Creating view COMPARE_UPDATE
prompt ============================
prompt
create or replace force view xxi.compare_update as
select count(*) CNT
  from (select prj_id, prj_parent, BYTES, prj_name, is_folder, version
          from project
        minus
        select prj_id, prj_parent, BYTES, prj_name, is_folder, version
          from project_temp);
grant select on XXI.COMPARE_UPDATE to ODB;
grant select on XXI.COMPARE_UPDATE to UPDATES;


prompt
prompt Creating view COMPARE_UPDATE_LIST
prompt =================================
prompt
create or replace force view xxi.compare_update_list as
select prj_id, prj_parent, BYTES, prj_name, is_folder, version
  from (select prj_id, prj_parent, BYTES, prj_name, is_folder, version
          from project
        minus
        select prj_id, prj_parent, BYTES, prj_name, is_folder, version
          from project_temp);
grant select on XXI.COMPARE_UPDATE_LIST to ODB;
grant select on XXI.COMPARE_UPDATE_LIST to UPDATES;


prompt
prompt Creating view DOCS
prompt ==================
prompt
create or replace force view xxi.docs as
with item as
 (select 1 Id from dual),
unins as
 (select 'brn_birth_act' table_name,
         'Свидетельство о рождении' DocName,
         (select count(*)
            from brn_birth_act g, item
           where (g.br_act_ch = item.Id or g.br_act_f = item.Id or
                 g.br_act_m = item.Id)) DocCnt
    from dual
  union all
  select 'PATERN_CERT' table_name,
         'Установление отцовства' DocName,
         (select count(*)
            from PATERN_CERT j, item
           where (j.pс_ch = item.Id or j.pс_f = item.Id or j.pс_m = item.Id)) DocCnt
    from dual
  union all
  select 'mc_mercer' table_name,
         'Заключение брака' DocName,
         (select count(*)
            from mc_mercer g, item
           where (g.MERCER_HE = item.Id or g.MERCER_SHE = item.Id)) DocCnt
    from dual
  union all
  select 'DIVORCE_CERT' table_name,
         'Расторжение брака' DocName,
         (select count(*)
            from DIVORCE_CERT g, item
           where (g.DIVC_HE = item.Id or g.DIVC_SHE = item.Id)) DocCnt
    from dual
  union all
  select 'DEATH_CERT' table_name,
         'Установление акта о смерти' DocName,
         (select count(*) from DEATH_CERT g, item where (g.DC_CUS = item.Id)) DocCnt
    from dual
  union all
  select 'UPD_NAME' table_name,
         'Перемена имени' DocName,
         (select count(*) from update_name g, item where (g.CUSID = item.Id)) DocCnt
    from dual
  union all
  select 'UPDATE_ABH_NAME' table_name,
         'Восстановление абхазской фамилии' DocName,
         (select count(*)
            from UPDATE_ABH_NAME g, item
           where (g.CUSID = item.Id)) DocCnt
    from dual
  union all
  select 'UPD_NAT' table_name,
         'Перемена национальной принадлежности' DocName,
         (select count(*) from UPD_NAT g, item where (g.CUSID = item.Id)) DocCnt
    from dual
  union all
  select 'ADOPT' table_name,
         'Усыновление (удочерение)' DocName,
         (select count(*)
            from ADOPTOIN g, item
           where (g.CUSID_CH = item.Id)
              or (g.CUSID_F = item.Id)
              or (g.CUSID_F_AD = item.Id)
              or (g.CUSID_M = item.Id)
              or (g.CUSID_M_AD = item.Id)) DocCnt
    from dual)
select upper(table_name) table_name, DocName, DocCnt from unins;
grant select on XXI.DOCS to ODB;


prompt
prompt Creating view LOCKS_OBJ
prompt =======================
prompt
CREATE OR REPLACE FORCE VIEW XXI.LOCKS_OBJ AS
SELECT B.Owner, B.Object_Name, A.Oracle_Username, A.OS_User_Name
  FROM V$Locked_Object A, All_Objects B
 WHERE A.Object_ID = B.Object_ID;

prompt
prompt Creating view MJ_ALL_OBJ
prompt ========================
prompt
CREATE OR REPLACE FORCE VIEW XXI.MJ_ALL_OBJ AS
SELECT owner, object_type, COUNT(*) "Object Count"
  FROM sys.all_objects
 WHERE substr(object_name, 1, 4) != 'BIN$'
   AND substr(object_name, 1, 3) != 'DR$'
   AND ('XXI' IS NULL OR instr(UPPER(owner), UPPER('XXI')) > 0)
 GROUP BY owner, object_type
 ORDER BY 3 DESC;

prompt
prompt Creating view PRJ_FILES
prompt =======================
prompt
CREATE OR REPLACE FORCE VIEW XXI.PRJ_FILES AS
with Files as
 (SELECT level level_,
         substr(SYS_CONNECT_BY_PATH(PRJ_NAME, '/'), 2) AS path,
         max(LEVEL) over(partition by PRJ_ID) max_,
         prj_id,
         prj_parent,
         BYTES,
         prj_name,
         is_folder,
         version
    FROM PROJECT g
   where is_folder = 'N'
   START WITH PRJ_ID > 0
  CONNECT BY PRIOR PRJ_ID = PRJ_PARENT
   order by PRJ_ID asc)
select "LEVEL_",
       "PATH",
       "MAX_",
       "PRJ_ID",
       "PRJ_PARENT",
       "BYTES",
       "PRJ_NAME",
       "IS_FOLDER",
       "VERSION"
  from Files
 where max_ = level_;
grant select on XXI.PRJ_FILES to ODB;
grant select on XXI.PRJ_FILES to UPDATES;


prompt
prompt Creating view PRJ_FLS
prompt =====================
prompt
create or replace force view xxi.prj_fls as
select prj."PRJ_ID",
       prj."PRJ_PARENT",
       prj."BYTES",
       prj."PRJ_NAME",
       prj."IS_FOLDER",
       prj."PATH",
       prj."VERSION",
       prf_fl.blb_file
  from PRJ_FILE prf_fl, PRJ_FILES prj
 where prj.prj_id = prf_fl.prj_id;
grant select on XXI.PRJ_FLS to ODB;
grant select on XXI.PRJ_FLS to UPDATES;


prompt
prompt Creating view PRJ_FOLDERS
prompt =========================
prompt
CREATE OR REPLACE FORCE VIEW XXI.PRJ_FOLDERS AS
with Folders as
 (SELECT level level_,
         substr(SYS_CONNECT_BY_PATH(PRJ_NAME, '/'), 2) AS path,
         max(LEVEL) over(partition by PRJ_ID) max_,
         prj_id,
         prj_parent,
         BYTES,
         prj_name,
         is_folder,
         version
    FROM PROJECT g
   where is_folder = 'Y'
   START WITH PRJ_ID > 0
  CONNECT BY PRIOR PRJ_ID = PRJ_PARENT
   order by PRJ_ID asc)
select "LEVEL_",
       "PATH",
       "MAX_",
       "PRJ_ID",
       "PRJ_PARENT",
       "BYTES",
       "PRJ_NAME",
       "IS_FOLDER",
       "VERSION"
  from Folders
 where max_ = level_;
grant select on XXI.PRJ_FOLDERS to ODB;
grant select on XXI.PRJ_FOLDERS to UPDATES;


prompt
prompt Creating view SELECTBIRTH
prompt =========================
prompt
CREATE OR REPLACE FORCE VIEW XXI.SELECTBIRTH AS
select brn.br_act_id BRN_AC_ID,
       decode(brn.br_act_ld,
              'L',
              'Живорожденный',
              'D',
              'Мертворожденный') LIVE_DEAD,
       ch.ccusname CHILDREN_FIO,
       zg.zags_name,
       brn.br_act_date TM$_DOC_DATE,
       f.ccusname FFIO,
       m.ccusname MFIO,
       br_act_ch,
       br_act_f,
       br_act_m,
       to_date(brn.br_act_date,'DD.MM.RRRR') CR_DATE,
       to_char(brn.br_act_date,'HH24:MI:SS') CR_TIME,
       BR_ACT_USER
  from BRN_BIRTH_ACT brn, cus ch, cus f, cus m, zags zg
 where brn.br_act_ch = ch.icusnum(+)
   and brn.br_act_m = m.icusnum(+)
   and brn.br_act_f = f.icusnum(+)
   and zg.zags_id(+) = brn.br_act_zgid
      --где документ из загса пользователя
   and (exists (select null
                         from zags zg_list, usr usr_d
                        where usr_d.zags_id = zg_list.zags_id
                          and usr_d.cusrlogname = user
                          and zg_list.zags_id = brn.br_act_zgid)
        -- или может есть группа
        or exists (select null
  from ODB_GRP_MEMBER mem, usr, ODB_GROUP_USR grp
 where mem.iusrid = usr.iusrid
   and mem.grp_id = grp.grp_id
   and grp.grp_name = 'ZAGS_BRN_ALL'
   and usr.cusrlogname = user))
;
grant select on XXI.SELECTBIRTH to ODB;


prompt
prompt Creating function FINDZAGS
prompt ==========================
prompt
create or replace function xxi.FindZags(ID number) return varchar2 is
  ret varchar2(500);
begin
  select nvl((select t.zags_name from ZAGS t where t.zags_id = ID), ' ')
    into ret
    from dual;
  return(ret);
end FindZags;
/
grant execute on XXI.FINDZAGS to ODB;


prompt
prompt Creating view VADOPTOIN
prompt =======================
prompt
create or replace force view xxi.vadoptoin as
select to_date(DOC_DATE, 'DD.MM.RRRR') CR_DATE,
       to_char(DOC_DATE, 'HH24:MI:SS') CR_TIME,
       F.CCUSNAME FATHERFIO,
       M.CCUSNAME MOTHERFIO,
       CH.CCUSNAME CHILDRENFIO,
       ad_m.CCUSNAME ADMOTHERFIO,
       ad_f.CCUSNAME ADFATHERFIO,
       findzags(adopt.zags_id) ZAGS_NAME,
       ADOPT."ID",
       ADOPT."ZAGS_ID",
       ADOPT."OLD_LASTNAME",
       ADOPT."OLD_FIRSTNAME",
       ADOPT."OLD_MIDDLNAME",
       ADOPT."NEW_LASTNAME",
       ADOPT."NEW_FIRSTNAME",
       ADOPT."NEW_MIDDLNAME",
       ADOPT."CUSID_CH",
       ADOPT."DOC_DATE",
       ADOPT."OPER",
       ADOPT."CUSID_M",
       ADOPT."CUSID_F",
       ADOPT."BRNACT",
       ADOPT."SVID_SERIA",
       ADOPT."SVID_NOMER",
       ADOPT."CUSID_M_AD",
       ADOPT."CUSID_F_AD",
       ADOPT."ADOPT_PARENTS",
       ADOPT."ZAP_ISPOLKOM_RESH",
       ADOPT."ZAP_SOVET_DEP_TRUD",
       ADOPT."ZAP_DATE",
       ADOPT."ZAP_NUMBER",
       ADOPT."NEW_BRTH",
       ADOPT."OLD_BRTH",
       ADOPT."BRN_CITY",
       ADOPT."BRN_AREA",
       ADOPT."BRN_OBL_RESP"
  from ADOPTOIN adopt, cus f, cus m, cus ch, cus ad_m, cus ad_f
 where f.icusnum(+) = adopt.cusid_f
   and ch.icusnum(+) = adopt.cusid_ch
   and m.icusnum(+) = adopt.cusid_m
   and ad_m.icusnum(+) = adopt.cusid_m_ad
   and ad_f.icusnum(+) = adopt.cusid_f_ad
      --где документ из загса пользователя
   and (exists (select null
           from zags zg_list, usr usr_d
          where usr_d.zags_id = zg_list.zags_id
            and usr_d.cusrlogname = user
            and zg_list.zags_id = adopt.zags_id)
        -- или может есть группа
        or exists (select null
           from ODB_GRP_MEMBER mem, usr, ODB_GROUP_USR grp
          where mem.iusrid = usr.iusrid
            and mem.grp_id = grp.grp_id
            and grp.grp_name = 'ZAGS_ADOPT_ALL'
            and usr.cusrlogname = user))
;
grant select on XXI.VADOPTOIN to ODB;


prompt
prompt Creating view V_AP_REP_CAT
prompt ==========================
prompt
create or replace force view xxi.v_ap_rep_cat as
select "REPORT_ID",
       "REPORT_TYPE_ID",
       "REPORT_NAME",
       "REPORT_UFS",
       "COPIES",
       "REPORT_VIEWER",
       "REPORT_COMMENT",
       "EDIT_PARAM",
       "OEM_DATA",
       "REPORT_FILE",
       "AVAILABLE_SQL"
  from AP_REPORT_CAT rep
 where exists (select null
          from AP_USER_REPORT_CAT_ROLE access_, usr
         where access_.USER_ID = usr.iusrid
           and access_.report_type_id = rep.report_type_id
           and access_.report_id = rep.report_id
           and usr.cusrlogname = user);
grant select on XXI.V_AP_REP_CAT to ODB;


prompt
prompt Creating view VAU_ACTION
prompt ========================
prompt
create or replace force view xxi.vau_action as
select "IACTION_ID",
       "DAUDDATE",
       "CAUDUSER",
       "CTABLE",
       "CAUDMACHINE",
       "CAUDPROGRAM",
       "CAUDOPERATION",
       "RROWID",
       "CAUDACTION",
       "CAUDMODULE",
       "IAUDSESSION",
       "CAUDIP_ADDRESS",
       "ID_NUM",
       "ID_ANUM"
  from AU_ACTION
 where ((CAUDOPERATION = 'U' and exists
        (select null
            from AU_DATA
           where AU_DATA.IACTION_ID = AU_ACTION.IACTION_ID
          union all
          select null
            from au_clob_data
           where au_clob_data.iaction_id = AU_ACTION.IACTION_ID
          union all
          select null
            from au_blob_data
           where au_blob_data.iaction_id = AU_ACTION.IACTION_ID)
       ) or CAUDOPERATION != 'U');
grant select on XXI.VAU_ACTION to ODB;


prompt
prompt Creating function IN_LONG_FIELD
prompt ===============================
prompt
CREATE OR REPLACE FUNCTION XXI.in_long_field
  ( p_table    IN VARCHAR2,
    p_field    IN VARCHAR2,
    p_where    IN VARCHAR2,
    p_value    IN VARCHAR2,
    p_parnum   IN INTEGER DEFAULT 0,
    p_Param1   IN  VARCHAR2 DEFAULT NULL,
    p_Param2   IN  VARCHAR2 DEFAULT NULL,
    p_Param3   IN  VARCHAR2 DEFAULT NULL )
  RETURN NUMBER IS
  TYPE ref_type_name IS REF CURSOR;
  v_cur ref_type_name;
  ret  NUMBER := 0;
  buf  VARCHAR2(32767);
  sel  VARCHAR2(2000);
BEGIN
  sel := 'SELECT '||p_field||' FROM '||p_table||' WHERE '||p_where;
  IF p_parnum = 0 THEN
    EXECUTE IMMEDIATE sel INTO buf;
  ELSIF p_parnum = 1 THEN
    EXECUTE IMMEDIATE sel INTO buf USING p_Param1;
  ELSIF p_parnum = 2 THEN
    EXECUTE IMMEDIATE sel INTO buf USING p_Param1, p_Param2;
  ELSIF p_parnum = 3 THEN
    EXECUTE IMMEDIATE sel INTO buf USING p_Param1, p_Param2, p_Param3;
  END IF;
  IF buf is not null THEN
    ret := INSTR(buf, p_value, 1, 1);
  ELSE
    ret := 0;
  END IF;
  RETURN ret ;
EXCEPTION
   WHEN NO_DATA_FOUND THEN
     RETURN -1;
   WHEN OTHERS THEN
     RETURN sqlcode ;
END; -- Function IN_LONG_FIELD
/
grant execute on XXI.IN_LONG_FIELD to ODB;


prompt
prompt Creating view V_AU_CUR_TRIGGERS
prompt ===============================
prompt
CREATE OR REPLACE FORCE VIEW XXI.V_AU_CUR_TRIGGERS AS
SELECT
  OWNER,
  TRIGGER_NAME,
  TABLE_NAME,
  TABLE_OWNER,
  STATUS
FROM ALL_TRIGGERS
WHERE TRIGGER_NAME LIKE 'T_A_IDU%'
  AND In_Long_Field('ALL_TRIGGERS',
                   'TRIGGER_BODY',
                   '(TRIGGER_NAME='''|| TRIGGER_NAME ||
                   ''' AND OWNER='''||OWNER||''')',
                   'шаблон аудиторского триггера для') > 0;
grant select on XXI.V_AU_CUR_TRIGGERS to ODB;


prompt
prompt Creating view V_AU_DATA
prompt =======================
prompt
CREATE OR REPLACE FORCE VIEW XXI.V_AU_DATA AS
SELECT IACTION_ID, CFIELD, CNEWDATA, COLDDATA
  FROM AU_DATA
UNION ALL
SELECT D.IACTION_ID,
       CFIELD,
       DECODE(CAUDOPERATION, 'I', '(CLOB)', NULL),
       DECODE(CAUDOPERATION, 'I', NULL, '(CLOB)')
  FROM AU_CLOB_DATA D, AU_ACTION A
 WHERE A.IACTION_ID = D.IACTION_ID
UNION ALL
SELECT D.IACTION_ID,
       CFIELD,
       DECODE(CAUDOPERATION, 'I', '(BLOB)', NULL),
       DECODE(CAUDOPERATION, 'I', NULL, '(BLOB)')
  FROM AU_BLOB_DATA D, AU_ACTION A
 WHERE A.IACTION_ID = D.IACTION_ID
UNION ALL
SELECT IACTION_ID, COBJ, CTEXT, NULL
  FROM AU_LOG;
comment on table XXI.V_AU_DATA is 'Для формы просмотра аудита AUVIEW.FMB';
grant select on XXI.V_AU_DATA to ODB;


prompt
prompt Creating view V_AU_TRIGGER
prompt ==========================
prompt
CREATE OR REPLACE FORCE VIEW XXI.V_AU_TRIGGER
(ctable, nline, ctext)
AS
SELECT CNAME, 1, 'CREATE TRIGGER ' || AUP_UTIL.Trigger_Name(CNAME)
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 2, 'AFTER INSERT OR UPDATE OR DELETE '
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 3, 'ON "' || CNAME || '"'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 4, 'REFERENCING NEW AS NEW OLD AS OLD'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 5, 'FOR EACH ROW'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 6, 'DECLARE'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 7, '  OP_CODE CHAR(1);'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 8, '  New_ID AU_ACTION.IACTION_ID%TYPE;'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 9, '  tabData AUP_UTIL.T_TabAu;'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 10, '  V_SESSION           NUMBER;'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 11, '  V_MACHINE           VARCHAR2(64);'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 12, '  V_PROGRAM           VARCHAR2(64);'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 13, '  V_ACTION           VARCHAR2(64);'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 14, '  V_MODULE           VARCHAR2(64);'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 15, '  V_OSUSER           VARCHAR2(64);'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 16, '  V_USER           VARCHAR2(64);'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 17, '  V_CLIENT_IDENTIFIER           VARCHAR2(64);'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 18, 'BEGIN'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 19, '  /*---*'
  FROM AU_TABLES
UNION ALL
SELECT CNAME,
       20,
       '   * шаблон аудиторского триггера для "аудита справочников"'
  FROM AU_TABLES
UNION ALL
SELECT CNAME,
       21,
       '   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 22, '   *---*/'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 23, ''
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 24, 'V_SESSION := UserEnv(''SessionID'');'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 25, 'dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 26, 'Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 27, 'into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 28, 'from sys.v_$session'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 29, 'where audsid = V_SESSION;'
  FROM AU_TABLES
UNION ALL
SELECT CNAME,
       30,
       '  IF AUP_UTIL.Get_Mode(''' || CNAME ||
       ''') != ''Y'' THEN RETURN; END IF;'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 31, ''
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 31, '  IF INSERTING THEN'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 32, '    OP_CODE := ''I'';'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 33, '  ELSIF UPDATING THEN'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 34, '    OP_CODE := ''U'';'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 35, '  ELSIF DELETING THEN'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 36, '    OP_CODE := ''D'';'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 37, '  ELSE'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 38, '    OP_CODE := ''J'';'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 39, '  END IF;'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 40, ''
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 41, '  INSERT INTO AU_ACTION'
  FROM AU_TABLES
UNION ALL
SELECT CNAME,
       42,
       '     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 43, '     VALUES'
  FROM AU_TABLES
UNION ALL
SELECT CNAME,
       44,
       '     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, ''' ||
       CNAME || ''', ' || DECODE(IOT_TYPE,
                                 NULL,
                                 'NVL(:NEW.ROWID, :OLD.ROWID)',
                                 'NULL /*UROWID*/') ||
       ', V_ACTION, V_MODULE, Decode(OP_Code, ''D'', :OLD.'||nvl(AUP_UTIL.PK(CNAME),'ROWID') ||', :NEW.'||nvl(AUP_UTIL.PK(CNAME),'ROWID') ||'), Decode(OP_Code, ''D'',null, null))'
  FROM USER_TABLES, AU_TABLES
 WHERE TABLE_NAME = CNAME
UNION ALL
SELECT CNAME, 45, '     RETURNING IACTION_ID INTO New_ID;'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 46, ''
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 47, '  tabData := AUP_UTIL.T_TabAu ();'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 48, ''
  FROM AU_TABLES
UNION ALL
SELECT CNAME,
       49 + COLUMN_ID,
       Decode(DATA_TYPE,
              'CLOB',
              'IF UPDATING(''' || COLUMN_NAME || ''') OR DELETING THEN ' ||
              'AUP_UTIL.Put_CLOB_Data(New_ID, ''' || COLUMN_NAME ||
              ''', :OLD.' || COLUMN_NAME || '); END IF;',
              'BLOB',
              'IF UPDATING(''' || COLUMN_NAME || ''') OR DELETING THEN ' ||
              'AUP_UTIL.Put_BLOB_Data(New_ID, ''' || COLUMN_NAME ||
              ''', :OLD.' || COLUMN_NAME || '); END IF;',
              --       'IF (UPDATING AND '||
              --       'NVL(:NEW.'||COLUMN_NAME||', '||Decode(DATA_TYPE, 'DATE', 'AUP_UTIL.dNullDate', 'NUMBER', '0', '''x''')||
              --        ') != '||
              --        'NVL(:OLD.'||COLUMN_NAME||', '||Decode(DATA_TYPE, 'DATE', 'AUP_UTIL.dNullDate', 'NUMBER', '0', '''x''')||
              --          ')) OR INSERTING OR DELETING THEN  '||
              --             'AUP_UTIL.Put_Data(New_ID, '''||COLUMN_NAME||
              'IF UPDATING (''' || COLUMN_NAME ||
              ''') OR INSERTING OR DELETING THEN ' ||
              'AUP_UTIL.Put_Data(tabData, ''' || COLUMN_NAME || ''', :NEW.' ||
              COLUMN_NAME || ', :OLD.' || COLUMN_NAME || '); END IF;') CTEXT
  FROM USER_TAB_COLUMNS, AU_TABLES
 WHERE TABLE_NAME = CNAME
   AND DATA_TYPE IN
       ('CLOB', 'BLOB', 'CHAR', 'DATE', 'NUMBER', 'VARCHAR2', 'ROWID')
UNION ALL
SELECT CNAME, 50, ''
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 51, '  AUP_UTIL.Put_Data (New_ID, tabData);'
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 52, ''
  FROM AU_TABLES
UNION ALL
SELECT CNAME, 53, 'END ' || AUP_UTIL.Trigger_Name(CNAME) || ';'
  FROM AU_TABLES
;
grant select on XXI.V_AU_TRIGGER to ODB;


prompt
prompt Creating view V_AU_TRIGGER2
prompt ===========================
prompt
CREATE OR REPLACE FORCE VIEW XXI.V_AU_TRIGGER2
(ctable, nline, ctext)
AS
SELECT CNAME, 2, 'CREATE TRIGGER ' || AUP_UTIL.Trigger_Name (CNAME) FROM AU_TABLES
UNION ALL
SELECT CNAME, 3, 'AFTER INSERT OR UPDATE OR DELETE ' FROM AU_TABLES
UNION ALL
SELECT CNAME, 4, 'ON "'||CNAME ||'"' FROM AU_TABLES
UNION ALL
SELECT CNAME, 5, 'REFERENCING NEW AS NEW OLD AS OLD' FROM AU_TABLES
UNION ALL
SELECT CNAME, 6, 'FOR EACH ROW' FROM AU_TABLES
UNION ALL
SELECT CNAME, 7, 'DECLARE' FROM AU_TABLES
UNION ALL
SELECT CNAME, 8, '  OP_CODE CHAR(1);' FROM AU_TABLES
UNION ALL
SELECT CNAME, 9, '  New_ID AU_ACTION.IACTION_ID%TYPE;' FROM AU_TABLES
UNION ALL
SELECT CNAME, 10, '  tabData AUP_UTIL.T_TabAu;' FROM AU_TABLES
UNION ALL
SELECT CNAME, 12, 'BEGIN' FROM AU_TABLES
UNION ALL
SELECT CNAME, 13, '  /*---*' FROM AU_TABLES
UNION ALL
SELECT CNAME, 14, '   * шаблон аудиторского триггера для "аудита справочников"' FROM AU_TABLES
UNION ALL
SELECT CNAME, 15, '   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $' FROM AU_TABLES
UNION ALL
SELECT CNAME, 16, '   *---*/' FROM AU_TABLES
UNION ALL
SELECT CNAME, 17, '' FROM AU_TABLES
UNION ALL
SELECT CNAME, 18, '  IF AUP_UTIL.Get_Mode('''||CNAME||''') != ''Y'' THEN RETURN; END IF;' FROM AU_TABLES
UNION ALL
SELECT CNAME, 19, '' FROM AU_TABLES
UNION ALL
SELECT CNAME, 20, '  IF INSERTING THEN' FROM AU_TABLES
UNION ALL
SELECT CNAME, 21, '    OP_CODE := ''I'';' FROM AU_TABLES
UNION ALL
SELECT CNAME, 22, '  ELSIF UPDATING THEN' FROM AU_TABLES
UNION ALL
SELECT CNAME, 23, '    OP_CODE := ''U'';' FROM AU_TABLES
UNION ALL
SELECT CNAME, 24, '  ELSIF DELETING THEN' FROM AU_TABLES
UNION ALL
SELECT CNAME, 25, '    OP_CODE := ''D'';' FROM AU_TABLES
UNION ALL
SELECT CNAME, 26, '  ELSE' FROM AU_TABLES
UNION ALL
SELECT CNAME, 27, '    OP_CODE := ''J'';' FROM AU_TABLES
UNION ALL
SELECT CNAME, 28, '  END IF;' FROM AU_TABLES
UNION ALL
SELECT CNAME, 29, '' FROM AU_TABLES
UNION ALL
SELECT CNAME, 30, '  INSERT INTO AU_ACTION' FROM AU_TABLES
UNION ALL
SELECT CNAME, 31, '     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)' FROM AU_TABLES
UNION ALL
SELECT CNAME, 32, '     VALUES' FROM AU_TABLES
UNION ALL
SELECT CNAME, 33, '     (S_AU_ACTION.NextVal, AUDITING.V_MACHINE, AUDITING.V_PROGRAM, OP_CODE, '''||CNAME||''', ' || DECODE (IOT_TYPE, NULL, 'NVL(:NEW.ROWID, :OLD.ROWID)', 'NULL /*UROWID*/' ) || ', AUDITING.V_ACTION, AUDITING.V_MODULE, Decode(OP_Code, ''D'', :OLD.$P_ID_NUM$, :NEW.$P_ID_NUM$), Decode(OP_Code, ''D'',:OLD.$P_ID_ANUM$, :NEW.$P_ID_ANUM$))' FROM USER_TABLES, AU_TABLES WHERE TABLE_NAME = CNAME
UNION ALL
SELECT CNAME, 34, '     RETURNING IACTION_ID INTO New_ID;' FROM AU_TABLES
UNION ALL
SELECT CNAME, 35, '' FROM AU_TABLES
UNION ALL
SELECT CNAME, 40, '  tabData := AUP_UTIL.T_TabAu ();' FROM AU_TABLES
UNION ALL
SELECT CNAME, 41, '' FROM AU_TABLES
UNION ALL
SELECT CNAME, 100+COLUMN_ID, Decode(DATA_TYPE, 'CLOB', 'IF UPDATING('''||COLUMN_NAME||''') OR DELETING THEN ' ||
                                                       'AUP_UTIL.Put_CLOB_Data(New_ID, '''||COLUMN_NAME||
                                                                          ''', :OLD.' || COLUMN_NAME || '); END IF;',
                                               'BLOB', 'IF UPDATING('''||COLUMN_NAME||''') OR DELETING THEN ' ||
                                                       'AUP_UTIL.Put_BLOB_Data(New_ID, '''||COLUMN_NAME||
                                                                          ''', :OLD.' || COLUMN_NAME || '); END IF;',
--       'IF (UPDATING AND '||
--       'NVL(:NEW.'||COLUMN_NAME||', '||Decode(DATA_TYPE, 'DATE', 'AUP_UTIL.dNullDate', 'NUMBER', '0', '''x''')||
--        ') != '||
--        'NVL(:OLD.'||COLUMN_NAME||', '||Decode(DATA_TYPE, 'DATE', 'AUP_UTIL.dNullDate', 'NUMBER', '0', '''x''')||
--          ')) OR INSERTING OR DELETING THEN  '||
--             'AUP_UTIL.Put_Data(New_ID, '''||COLUMN_NAME||
         'IF UPDATING (''' || COLUMN_NAME || ''') OR INSERTING OR DELETING THEN ' ||
             'AUP_UTIL.Put_Data(tabData, '''||COLUMN_NAME||
                                ''', :NEW.'||COLUMN_NAME||
                                  ', :OLD.'||COLUMN_NAME||'); END IF;') CTEXT
        FROM USER_TAB_COLUMNS, AU_TABLES WHERE TABLE_NAME = CNAME AND DATA_TYPE IN('CLOB', 'BLOB', 'CHAR', 'DATE', 'NUMBER', 'VARCHAR2', 'ROWID')
UNION ALL
SELECT CNAME, 2000, '' FROM AU_TABLES
UNION ALL
SELECT CNAME, 2001, '  AUP_UTIL.Put_Data (New_ID, tabData);' FROM AU_TABLES
UNION ALL
SELECT CNAME, 2002, '' FROM AU_TABLES
UNION ALL
SELECT CNAME, 2003, 'END ' || AUP_UTIL.Trigger_Name (CNAME) || ';' FROM AU_TABLES
;
grant select on XXI.V_AU_TRIGGER2 to ODB;


prompt
prompt Creating view VBRN
prompt ==================
prompt
create or replace force view xxi.vbrn as
select br_act_id,
       br_act_date,
       decode(brn.br_act_ztp,
              'J',
              'Организация',
              'F',
              'Физ. лицо') br_act_ztp,
       br_act_brchcnt,
       decode(brn.br_act_ld,
              'L',
              'Живорожденный',
              'D',
              'Мертворожденный') br_act_ld,
       br_act_zgid,
       br_act_user,
       decode(brn.br_act_tgrabf,
              'A',
              'Свидетельства о заключении брака',
              'B',
              'Свидетельства об установлении отцовства',
              'V',
              'Заявления матери') br_act_tgrabf,
       br_act_mzdate,
       decode(brn.br_act_dbf,
              'A',
              'Документ установленной формы о рождении',
              'B',
              'Заявление') br_act_dbf,
       br_act_ch,
       br_act_f,
       br_act_m,
       br_act_medorga,
       br_act_datedoca,
       br_act_ndoca,
       br_act_fiob,
       br_act_datedocb,
       br_act_namecourt,
       br_act_desccourt,
       br_act_dcourt,
       br_act_fadfirst_name,
       br_act_fadlast_name,
       br_act_fadmiddle_name,
       br_act_fadlocation,
       br_act_fadorg_name,
       br_act_fadreg_adr,
       br_act_mercer_id,
       br_act_num,
       br_act_seria,
       br_act_patcer,
       ch.ccusname ChildrenName,
       f.ccusname FatherName,
       m.ccusname MotherName
  from BRN_BIRTH_ACT brn, cus ch, cus f, cus m, zags zg
 where brn.br_act_ch = ch.icusnum(+)
   and brn.br_act_m = m.icusnum(+)
   and brn.br_act_f = f.icusnum(+)
   and brn.br_act_zgid = zg.zags_id(+);
grant select on XXI.VBRN to ODB;


prompt
prompt Creating view VCUS
prompt ==================
prompt
create or replace force view xxi.vcus as
select cus.ICUSNUM,
       cus.DCUSOPEN TM$DCUSOPEN,
       cus.CCUSIDOPEN,
       cus.DCUSBIRTHDAY,
       to_date(cus.DCUSOPEN, 'DD.MM.RRRR') CR_DATE,
       to_char(cus.DCUSOPEN, 'HH24:MI:SS') CR_TIME,
       cus.CCUSLAST_NAME,
       cus.CCUSFIRST_NAME,
       cus.CCUSMIDDLE_NAME,
       decode(cus.CCUSSEX, 1, 'Мужской', 2, 'Женский') CCUSSEX,
       (select t.name from doc_types t where t.code = ID_DOC_TP) ID_DOC_TP,
       DOC_SER,
       DOC_NUM,
       DOC_DATE,
       DOC_PERIOD,
       DOC_SUBDIV,
       (select countries.name from countries where countries.code = COUNTRY) COUNTRY,
       AREA,
       CITY,
       INFR_NAME,
       PUNCT_NAME,
       DOM,
       KV,
       cit.country_name,
       nat.name
  from cus, cus_addr adr, cus_citizen cit, cus_docum doc, nationality nat
 where cus.icusnum = adr.icusnum(+)
   and cus.icusnum = cit.icusnum(+)
   and cus.icusnum = doc.icusnum(+)
   and cit.osn(+) = 'Y'
   and doc.pref(+) = 'Y'
   and nat.id(+) = cus.ccusnationality;
comment on column XXI.VCUS.ICUSNUM is 'ID документа';
comment on column XXI.VCUS.TM$DCUSOPEN is 'Дата заведения';
comment on column XXI.VCUS.CCUSIDOPEN is 'Пользователь';
comment on column XXI.VCUS.DCUSBIRTHDAY is 'Дата рождения';
comment on column XXI.VCUS.CCUSLAST_NAME is 'Фамилия';
comment on column XXI.VCUS.CCUSFIRST_NAME is 'Имя';
comment on column XXI.VCUS.CCUSMIDDLE_NAME is 'Отчество';
comment on column XXI.VCUS.CCUSSEX is 'Пол';
comment on column XXI.VCUS.ID_DOC_TP is 'Тип документа';
comment on column XXI.VCUS.DOC_SER is 'Серия';
comment on column XXI.VCUS.NAME is 'Наименование национальности';
grant select on XXI.VCUS to ODB;


prompt
prompt Creating view VDEATH_CERT
prompt =========================
prompt
create or replace force view xxi.vdeath_cert as
select cus.ccusname DFIO,
       cus.dcusbirthday DBDATE,
       dc_id,
       dc_cus,
       dc_dd,
       dc_dpl,
       dc_cd,
       dc_fnum,
       dc_fd,
       dc_ftype,
       dc_fmon,
       dc_rcname,
       dc_nrname,
       dc_lloc,
       dc_ztp,
       dc_fadfirst_name,
       dc_fadlast_name,
       dc_fadmiddle_name,
       dc_fadlocation,
       dc_fadorg_name,
       dc_fadreg_adr,
       dc_seria,
       dc_number,
       dc_usr,
       dc_open tm$dc_open,
       dc_zags,
       to_date(DC_OPEN, 'DD.MM.RRRR') CR_DATE,
       to_char(DC_OPEN, 'HH24:MI:SS') CR_TIME
  from DEATH_CERT dc_cert, cus
 where cus.icusnum(+) = dc_cert.dc_cus
      --где документ из загса пользователя
   and (exists (select null
           from zags zg_list, usr usr_d
          where usr_d.zags_id = zg_list.zags_id
            and usr_d.cusrlogname = user
            and zg_list.zags_id = dc_cert.dc_zags)
        -- или может есть группа
        or exists (select null
           from ODB_GRP_MEMBER mem, usr, ODB_GROUP_USR grp
          where mem.iusrid = usr.iusrid
            and mem.grp_id = grp.grp_id
            and grp.grp_name = 'ZAGS_DEATH_ALL'
            and usr.cusrlogname = user))
;
grant select on XXI.VDEATH_CERT to ODB;


prompt
prompt Creating view VDIVORCE_CERT
prompt ===========================
prompt
create or replace force view xxi.vdivorce_cert as
select he.ccusname HeFio,
       she.ccusname SheFio,
       divorce."DIVC_ID",
       divorce."DIVC_HE",
       divorce."DIVC_SHE",
       divorce."DIVC_HE_LNBEF",
       divorce."DIVC_HE_LNAFT",
       divorce."DIVC_SHE_LNBEF",
       divorce."DIVC_SHE_LNAFT",
       divorce."DIVC_DATE" TM$DIVC_DATE,
       divorce."DIVC_DT",
       divorce."DIVC_USR",
       divorce."DIVC_TYPE",
       divorce."DIVC_TCHD",
       divorce."DIVC_TCHNUM",
       divorce."DIVC_CAN",
       divorce."DIVC_CAD",
       divorce."DIVC_ZOSCN",
       divorce."DIVC_ZOSCD",
       divorce."DIVC_ZOSFIO",
       divorce."DIVC_ZOSCN2",
       divorce."DIVC_ZOSCD2",
       divorce."DIVC_ZOSFIO2",
       divorce."DIVC_ZOSPRISON",
       divorce."DIVC_MC_MERCER",
       divorce."DIVC_NUM",
       divorce."DIVC_SERIA",
       divorce."DIVC_ZAGS",
       divorce."DIVC_ZLNAME",
       divorce."DIVC_ZАNAME",
       divorce."DIVC_ZMNAME",
       divorce."DIVC_ZPLACE",
       to_date(DIVC_DATE, 'DD.MM.RRRR') CR_DATE,
       to_char(DIVC_DATE, 'HH24:MI:SS') CR_TIME
  from DIVORCE_CERT divorce, cus he, cus she
 where he.icusnum(+) = divorce.divc_he
   and she.icusnum(+) = divorce.divc_she
      --где документ из загса пользователя
   and (exists (select null
           from zags zg_list, usr usr_d
          where usr_d.zags_id = zg_list.zags_id
            and usr_d.cusrlogname = user
            and zg_list.zags_id = divorce.divc_zags)
        -- или может есть группа
        or exists (select null
           from ODB_GRP_MEMBER mem, usr, ODB_GROUP_USR grp
          where mem.iusrid = usr.iusrid
            and mem.grp_id = grp.grp_id
            and grp.grp_name = 'ZAGS_DIVORCE_ALL'
            and usr.cusrlogname = user))
;
grant select on XXI.VDIVORCE_CERT to ODB;


prompt
prompt Creating view VFR_LIC
prompt =====================
prompt
create or replace force view xxi.vfr_lic as
select fr_lic.bik,
       fr_lic.dexpdate,
       fr_lic.licdata,
       fr_lic.bik || to_char(dexpdate, 'DDMMRRRR') licSTR
  from fr_lic, smr
 where bik = smr.csmrmfo8
   and (fr_lic.dexpdate >= sysdate or fr_lic.dexpdate is null);
grant select on XXI.VFR_LIC to ODB;


prompt
prompt Creating view VMC_MERCER
prompt ========================
prompt
create or replace force view xxi.vmc_mercer as
select he.ccusname HeFio,
       she.ccusname SheFio,
       mercer."MERCER_ID",
       mercer."MERCER_HE",
       mercer."MERCER_SHE",
       mercer."MERCER_HE_LNBEF",
       mercer."MERCER_HE_LNAFT",
       mercer."MERCER_SHE_LNBEF",
       mercer."MERCER_SHE_LNBAFT",
       mercer."MERCER_HEAGE",
       mercer."MERCER_SHEAGE",
       mercer."MERCER_DATE" TM$MERCER_DATE,
       mercer."MERCER_USR",
       mercer."MERCER_ZAGS",
       mercer."MERCER_DIVSHE",
       mercer."MERCER_DIVHE",
       mercer."MERCER_DSPMT_HE",
       mercer."MERCER_NUM",
       mercer."MERCER_SERIA",
       mercer."MERCER_DIESHE",
       mercer."MERCER_DIEHE",
       mercer."MERCER_OTHER",
       mercer."MERCER_DSPMT_SHE",
       to_date(MERCER_DATE, 'DD.MM.RRRR') CR_DATE,
       to_char(MERCER_DATE, 'HH24:MI:SS') CR_TIME,
       mercer."MC_DATE"
  from MC_MERCER mercer, cus he, cus she
 where he.icusnum(+) = mercer.mercer_he
   and she.icusnum(+) = mercer.mercer_she
      --где документ из загса пользователя
   and (exists (select null
           from zags zg_list, usr usr_d
          where usr_d.zags_id = zg_list.zags_id
            and usr_d.cusrlogname = user
            and zg_list.zags_id = mercer.mercer_zags)
        -- или может есть группа
        or exists (select null
           from ODB_GRP_MEMBER mem, usr, ODB_GROUP_USR grp
          where mem.iusrid = usr.iusrid
            and mem.grp_id = grp.grp_id
            and grp.grp_name = 'ZAGS_MERCER_ALL'
            and usr.cusrlogname = user))
;
grant select on XXI.VMC_MERCER to ODB;


prompt
prompt Creating view VNOTARY
prompt =====================
prompt
create or replace force view xxi.vnotary as
select not_id, otd.cotdname, not_name, not_ruk, NOT_ADDRESS, NOT_TELEPHONE
  from notary, otd
 where notary.not_otd = otd.iotdnum(+);
grant select on XXI.VNOTARY to ODB;


prompt
prompt Creating view V_PATERN_CERT
prompt ===========================
prompt
create or replace force view xxi.v_patern_cert as
select f.ccusname FatherFiO,
       m.ccusname MotherFio,
       ch.ccusname ChildFio,
       f.dcusbirthday FatherBirthDate,
       m.dcusbirthday MotherBirthDate,
       ch.dcusbirthday ChildrenBirth,
       pc_id,
       pc_act_id,
       pс_aft_lname,
       pс_aft_fname,
       pс_aft_mname,
       pс_ch,
       pс_f,
       pс_m,
       pс_type,
       pс_trz,
       pс_fz,
       pс_crname,
       pс_crdate,
       pс_seria,
       pс_number,
       pс_date tm$pс_date,
       pс_user,
       pс_zags,
       pc_zmname,
       pc_zfname,
       pc_zlname,
       pc_zplace,
       to_date(PС_DATE, 'DD.MM.RRRR') CR_DATE,
       to_char(PС_DATE, 'HH24:MI:SS') CR_TIME
  from PATERN_CERT pat_cer, cus ch, cus f, cus m
 where ch.icusnum(+) = pat_cer.pс_ch
   and f.icusnum(+) = pat_cer.pс_f
   and m.icusnum(+) = pat_cer.pс_m
      --где документ из загса пользователя
   and (exists (select null
           from zags zg_list, usr usr_d
          where usr_d.zags_id = zg_list.zags_id
            and usr_d.cusrlogname = user
            and zg_list.zags_id = pat_cer.pс_zags)
        -- или может есть группа
        or exists (select null
           from ODB_GRP_MEMBER mem, usr, ODB_GROUP_USR grp
          where mem.iusrid = usr.iusrid
            and mem.grp_id = grp.grp_id
            and grp.grp_name = 'ZAGS_PATERN_ALL'
            and usr.cusrlogname = user))
;
grant select on XXI.V_PATERN_CERT to ODB;


prompt
prompt Creating view VPUD
prompt ==================
prompt
create or replace force view xxi.vpud as
select code, name from doc_types t;
grant select on XXI.VPUD to ODB;


prompt
prompt Creating package UTIL
prompt =====================
prompt
create or replace package xxi.UTIL is

  -- Author  : SAID
  -- Created : 02.01.2021 15:21:59
  -- Purpose : Разные функции для получения данных
  FUNCTION Get_Nat_Name(ID_ IN number) RETURN VARCHAR2;
  FUNCTION Get_Country_Name(ID_ IN number) RETURN VARCHAR2;
  function Address(ID number) return varchar2;
  FUNCTION Get_Citizen(ID_ IN number) RETURN VARCHAR2;
end UTIL;
/
grant execute on XXI.UTIL to ODB;


prompt
prompt Creating function DATE2STR
prompt ==========================
prompt
CREATE OR REPLACE FUNCTION XXI.Date2Str(dDay IN DATE) RETURN VARCHAR2 IS
  Rus_Month VARCHAR2(4000);
BEGIN
  BEGIN
    SELECT TO_CHAR(dDay, 'DD') || ' ' ||
           DECODE(TO_CHAR(dDay, 'MM'),
                  '01',
                  'января ',
                  '02',
                  'февраля ',
                  '03',
                  'марта ',
                  '04',
                  'апреля ',
                  '05',
                  'мая ',
                  '06',
                  'июня ',
                  '07',
                  'июля ',
                  '08',
                  'августа ',
                  '09',
                  'сентября ',
                  '10',
                  'октября ',
                  '11',
                  'ноября ',
                  '12',
                  'декабря ') || TO_CHAR(dDay, 'YYYY') || ' г.'
      INTO Rus_Month
      FROM SYS.DUAL;
  EXCEPTION
    WHEN OTHERS THEN
      Rus_Month := sqlerrm;
  END;

  RETURN Rus_Month;

END Date2Str;
/
grant execute on XXI.DATE2STR to ODB;


prompt
prompt Creating view V_REP_ADOPTOIN
prompt ============================
prompt
create or replace force view xxi.v_rep_adoptoin as
with data_ as
 (select adopt.ID ADOPT_ID,
 to_char(adopt.ID) ID,
         adopt.OLD_LASTNAME,
         date2str(adopt.DOC_DATE) DOC_DATE,

         adopt.OLD_FIRSTNAME,
         adopt.OLD_MIDDLNAME,

         adopt.NEW_LASTNAME,
         adopt.NEW_FIRSTNAME,
         adopt.NEW_MIDDLNAME,

         date2str(adopt.NEW_BRTH) NEW_BRTH,
         date2str(adopt.OLD_BRTH) OLD_BRTH,

         adopt.BRN_CITY,
         adopt.BRN_AREA,
         adopt.BRN_OBL_RESP,

         to_char(ch_br.br_act_id) BR_ACT_ID,
         date2str(ch_br.br_act_date) br_act_date,

         f.ccuslast_name   F_LNAME,
         f.ccusfirst_name  F_FNAME,
         f.ccusmiddle_name F_MNAME,

         m.ccuslast_name   M_LNAME,
         m.ccusfirst_name  M_FNAME,
         m.ccusmiddle_name M_MNAME,

         UTIL.Get_Nat_Name(f.ccusnationality) F_NAT_NAME,
         UTIL.Get_Nat_Name(m.ccusnationality) M_NAT_NAME,

         M_AD.CCUSLAST_NAME   M_AD_LNAME,
         M_AD.CCUSFIRST_NAME  M_AD_FNAME,
         M_AD.CCUSMIDDLE_NAME M_AD_MNAME,
         F_AD.CCUSLAST_NAME   F_AD_LNAME,
         F_AD.CCUSFIRST_NAME  F_AD_FNAME,
         F_AD.CCUSMIDDLE_NAME F_AD_MNAME,

         UTIL.Get_Nat_Name(M_AD.Ccusnationality) M_AD_NAT_NAME,
         UTIL.Get_Nat_Name(F_AD.Ccusnationality) F_AD_NAT_NAME,

         f_ad_addr.AREA F_AD_ADDR_CITY,
         f_ad_addr.PUNCT_NAME F_AD_ADDR_AREA,
         UTIL.Get_Country_Name(f_ad_addr.Country) F_AD_ADDR_COUNTRY,

         m_ad_addr.AREA M_AD_ADDR_CITY,
         m_ad_addr.PUNCT_NAME M_AD_ADDR_AREA,
         UTIL.Get_Country_Name(m_ad_addr.Country) M_AD_ADDR_COUNTRY,

         UTIL.Address(F_AD.ICUSNUM) F_AD_ST_H_KV,
         UTIL.Address(M_AD.ICUSNUM) M_AD_ST_H_KV,
         decode(ADOPT_PARENTS,
                'Y',
                'Записываются',
                'N',
                'Не записываются') ADOPT_PARENTS,
         ZAP_ISPOLKOM_RESH,
         ZAP_SOVET_DEP_TRUD,
         adopt.svid_seria,
         adopt.svid_nomer
    from ADOPTOIN      adopt,
         cus           ch,
         brn_birth_act ch_br,
         cus           f,
         cus           m,
         cus           M_AD,
         cus           F_AD,
         cus_addr      f_ad_addr,
         cus_addr      m_ad_addr
   where ch.icusnum(+) = adopt.cusid_ch

     and ch_br.br_act_id(+) = adopt.brnact

     and f.icusnum(+) = adopt.cusid_f
     and m.icusnum(+) = adopt.cusid_m

     and M_AD.ICUSNUM(+) = adopt.cusid_m_ad
     and F_AD.ICUSNUM(+) = adopt.cusid_f_ad

     and f_ad_addr.icusnum(+) = F_AD.ICUSNUM
     and m_ad_addr.icusnum(+) = M_AD.ICUSNUM

  )

select nvl(SVID_NOMER, ' ') "SVID_NOMER",
       nvl(BRN_AREA, ' ') "BRN_AREA",
       nvl(F_AD_ADDR_COUNTRY, ' ') "F_AD_ADDR_COUNTRY",
       nvl(DOC_DATE, ' ') "DOC_DATE",
       nvl(F_AD_FNAME, ' ') "F_AD_FNAME",
       nvl(M_AD_ADDR_COUNTRY, ' ') "M_AD_ADDR_COUNTRY",
       nvl(M_FNAME, ' ') "M_FNAME",
       nvl(M_AD_FNAME, ' ') "M_AD_FNAME",
       nvl(M_AD_NAT_NAME, ' ') "M_AD_NAT_NAME",
       nvl(M_AD_MNAME, ' ') "M_AD_MNAME",
       nvl(M_MNAME, ' ') "M_MNAME",
       nvl(ZAP_SOVET_DEP_TRUD, ' ') "ZAP_SOVET_DEP_TRUD",
       nvl(F_AD_ST_H_KV, ' ') "F_AD_ST_H_KV",
       nvl(OLD_LASTNAME, ' ') "OLD_LASTNAME",
       nvl(M_AD_ADDR_AREA, ' ') "M_AD_ADDR_AREA",
       nvl(M_AD_ST_H_KV, ' ') "M_AD_ST_H_KV",
       nvl(BR_ACT_DATE, ' ') "BR_ACT_DATE",
       nvl(OLD_BRTH, ' ') "OLD_BRTH",
       nvl(F_FNAME, ' ') "F_FNAME",
       nvl(BRN_OBL_RESP, ' ') "BRN_OBL_RESP",
       nvl(ZAP_ISPOLKOM_RESH, ' ') "ZAP_ISPOLKOM_RESH",
       nvl(OLD_MIDDLNAME, ' ') "OLD_MIDDLNAME",
       nvl(F_MNAME, ' ') "F_MNAME",
       nvl(NEW_FIRSTNAME, ' ') "NEW_FIRSTNAME",
       nvl(NEW_BRTH, ' ') "NEW_BRTH",
       nvl(F_AD_ADDR_AREA, ' ') "F_AD_ADDR_AREA",
       nvl(F_AD_MNAME, ' ') "F_AD_MNAME",
       nvl(F_AD_NAT_NAME, ' ') "F_AD_NAT_NAME",
       nvl(ADOPT_PARENTS, ' ') "ADOPT_PARENTS",
       nvl(OLD_FIRSTNAME, ' ') "OLD_FIRSTNAME",
       nvl(M_LNAME, ' ') "M_LNAME",
       nvl(M_AD_ADDR_CITY, ' ') "M_AD_ADDR_CITY",
       nvl(F_NAT_NAME, ' ') "F_NAT_NAME",
       nvl(BRN_CITY, ' ') "BRN_CITY",
       nvl(NEW_LASTNAME, ' ') "NEW_LASTNAME",
       nvl(M_NAT_NAME, ' ') "M_NAT_NAME",
       nvl(F_LNAME, ' ') "F_LNAME",
       nvl(SVID_SERIA, ' ') "SVID_SERIA",
       nvl(NEW_MIDDLNAME, ' ') "NEW_MIDDLNAME",
       nvl(M_AD_LNAME, ' ') "M_AD_LNAME",
       nvl(F_AD_ADDR_CITY, ' ') "F_AD_ADDR_CITY",
       nvl(BR_ACT_ID, ' ') "BR_ACT_ID",
       nvl(F_AD_LNAME, ' ') "F_AD_LNAME",
       nvl(ID, ' ') "ID",
       ADOPT_ID
  from data_;
grant select on XXI.V_REP_ADOPTOIN to ODB;


prompt
prompt Creating function NUM2STR_F
prompt ===========================
prompt
CREATE OR REPLACE FUNCTION XXI.NUM2STR_F(num NUMBER) RETURN VARCHAR2 IS
  -- *****************************************************************************
  -- Переводит число в текст с учетом наличия дробной части (десятых, сотых и т.п.)
  -- Данная функция базируется на num2str
  -- При отсутствии дробной части работает как функция num2str

  -- Автор: Полников Игорь
  -- Дата создания: 09.07.2006
  -- Даты модификаций: 07.09.2006
  --                   prorok. 07.06.2012
  -- *****************************************************************************
  result VARCHAR2(500) := NULL;

  compl VARCHAR2(500); -- целая часть
  fract VARCHAR2(500); -- дробная часть

  sNum    VARCHAR2(500);
  p       NUMBER;
  s       VARCHAR2(500) := NULL;
  l_fract NUMBER;
BEGIN
  sNum := TO_CHAR(num,
                  'FM9999999999999990D999999',
                  'NLS_NUMERIC_CHARACTERS=''.,'''); -- исходное число
  p    := INSTR(sNum, '.'); -- позиция точки
  -- определение целой части числа
  IF p = 1 THEN
    compl := 0;
  ELSE
    compl := NVL(SUBSTR(sNum, 1, p), sNum);
  END IF;
  --  compl := TRUNC(num);  -- целая часть числа

  -- определение дробной части числа
  IF p = 0 THEN
    fract := 0;
  ELSE
    fract := SUBSTR(sNum, p + 1);
  END IF;
  l_fract := NVL(LENGTH(fract), 0); -- длина дробной части
  -- собираю в строку целую и дробную часть
  IF l_fract <> 0 THEN
    -- есть дробная часть
    s := 'целых ';
  END IF;

  -- удаляю точку, если такая есть в конце
  IF SUBSTR(compl, LENGTH(compl), 1) = '.' THEN
    compl := SUBSTR(compl, 1, LENGTH(compl) - 1);
  END IF;

  -- преобразую целую часть в строку
  result := num2str(compl, NULL) || s;

  s := NULL;
  IF l_fract <> 0 THEN
    IF l_fract = 1 THEN
      s := 'десятых';
    ELSIF l_fract = 2 THEN
      s := 'сотых';
    ELSIF l_fract = 3 THEN
      s := 'тысячных';
    ELSIF l_fract = 4 THEN
      s := 'десятитысячных';
    ELSIF l_fract = 5 THEN
      s := 'стотысячных';
    ELSIF l_fract = 6 THEN
      s := 'миллионных';
    END IF;
    result := result || LOWER(num2str(fract, NULL)) || s;
  END IF;

  result := REPLACE(result, 'Один целых', 'Одна целая');
  result := REPLACE(result, 'один целых', 'одна целая');
  result := REPLACE(result,
                    'один десятых',
                    'одна десятая');
  result := REPLACE(result, 'один сотых', 'одна сотая');
  result := REPLACE(result,
                    'один тысячных',
                    'одна тысячная');
  result := REPLACE(result,
                    'один десятитысячных',
                    'одна десятитысячная');
  result := REPLACE(result,
                    'один стотысячных',
                    'одна стотысячная');
  result := REPLACE(result,
                    'один миллионных',
                    'одна миллионная');

  result := REPLACE(result, 'Два целых', 'Две целых');
  result := REPLACE(result, 'два целых', 'две целых');
  result := REPLACE(result,
                    'два десятых',
                    'две десятых');
  result := REPLACE(result, 'два сотых', 'две сотых');
  result := REPLACE(result,
                    'два тысячных',
                    'две тысячных');
  result := REPLACE(result,
                    'два десятитысячных',
                    'две десятитысячных');
  result := REPLACE(result,
                    'два стотысячных',
                    'две стотысячных');
  result := REPLACE(result,
                    'два миллионных',
                    'две миллионных');

  RETURN result;
END;
/
grant execute on XXI.NUM2STR_F to ODB;


prompt
prompt Creating view V_REP_BRN_BIRTH_ACT
prompt =================================
prompt
create or replace force view xxi.v_rep_brn_birth_act as
with data_ as
 (select nvl(to_char(br.BR_ACT_ID), ' ') BR_ACT_ID,
         nvl(decode(ch.ccussex, 1, 'Мужской', 2, 'Женский'),
             ' ') CH_GENDR,
         nvl(to_char(br.BR_ACT_MEDORGA), ' ') MEDORGA,
         nvl(Date2Str(br.BR_ACT_DATE), ' ') BR_ACT_DATE,
         nvl(ch.ccuslast_name, ' ') CH_LNAME,
         nvl(ch.ccusfirst_name, ' ') CH_FNAME,
         nvl(ch.ccusmiddle_name, ' ') CH_MNAME,
         nvl(Date2Str(ch.dcusbirthday), ' ') CH_BR_DT,
         nvl(ch.ccusplace_birth, ' ') CH_PL_BR,
         nvl(num2str_f(br.br_act_brchcnt), ' ') CH_CNT,
         nvl(decode(br.BR_ACT_LD,
                    'L',
                    'Живорожденный',
                    'D',
                    'Мертворожденный'),
             ' ') LB_DB,
         --Документ подтверждающий факт рождения ребенка
         --если тип А -
         --//////////////////////////////////
         case
           when BR_ACT_DBF = 'A' then
            nvl(to_char(br.BR_ACT_NDOCA), ' ')
           else
            ' '
         end NDOCA,
         case
           when BR_ACT_DBF = 'A' then
            nvl(Date2Str(br.BR_ACT_DATEDOCA), ' ')
           else
            ' '
         end DATEDOCA,
         --если тип Б -
         --//////////////////////////////////
         case
           when BR_ACT_DBF = 'B' then
            nvl(br.br_act_fiob, ' ')
           else
            ' '
         end FIOB,
         case
           when BR_ACT_DBF = 'B' then
            nvl(Date2Str(br.BR_ACT_DATEDOCB), ' ')
           else
            ' '
         end DATEDOCB,
         nvl(br.BR_ACT_NAMECOURT, ' ') NAMECOURT,
         nvl(br.BR_ACT_DESCCOURT, ' ') DESCCOURT,
         nvl(Date2Str(br.BR_ACT_DCOURT), ' ') DCOURT,
         --Сведения об отце
         nvl(f.ccuslast_name, ' ') F_L_NAME,
         nvl(f.ccusfirst_name, ' ') F_F_NAME,
         nvl(f.ccusmiddle_name, ' ') F_M_NAME,
         nvl(Date2Str(f.dcusbirthday), ' ') F_BR_DT,
         nvl(f.CCUSPLACE_BIRTH, ' ') F_PL_BR,
         nvl(f_cit.country_name, ' ') F_CITIZ,
         nvl(f_nat.NAME, ' ') F_NAT,
         nvl(MJCUS.Address(f.icusnum), ' ') F_ADDR,
         --Сведения о матери
         nvl(m.ccuslast_name, ' ') M_L_NAME,
         nvl(m.ccusfirst_name, ' ') M_F_NAME,
         nvl(m.ccusmiddle_name, ' ') M_M_NAME,
         nvl(Date2Str(m.dcusbirthday), ' ') M_BR_DT,
         nvl(m.CCUSPLACE_BIRTH, ' ') M_PL_BR,
         nvl(m_cit.country_name, ' ') M_CITIZ,
         nvl(m_nat.NAME, ' ') M_NAT,
         nvl(MJCUS.Address(m.icusnum), ' ') M_ADDR,
         -- Сведения об отце указаны на основании
         --A
         case
           when BR_ACT_TGRABF = 'A' then
            nvl(to_char(mercer.mercer_id), ' ')
           else
            ' '
         end MERCER_ID,
         case
           when BR_ACT_TGRABF = 'A' then
            nvl(Date2Str(mercer.MERCER_DATE), ' ')
           else
            ' '
         end MERCER_DATE,
         case
           when BR_ACT_TGRABF = 'A' then
            FindZags(mercer.mercer_zags)
           else
            ' '
         end MERCER_ZAGS,
         --Б
         case
           when BR_ACT_TGRABF = 'B' then
            nvl(to_char(patern.pc_id), ' ')
           else
            ' '
         end PATERN_ID,
         case
           when BR_ACT_TGRABF = 'B' then
            nvl(Date2Str(patern.PС_DATE), ' ')
           else
            ' '
         end PATERN_DATE,
         case
           when BR_ACT_TGRABF = 'B' then
            FindZags(patern.pс_zags)
           else
            ' '
         end PATERN_ZAGS,
         Date2Str(br.BR_ACT_MZDATE) MZDATE,
         nvl(br.br_act_seria, ' ') ACT_SERIA,
         nvl(br.BR_ACT_NUM, ' ') ACT_NUM
    from brn_birth_act br,
         cus           ch,
         cus           f,
         cus           m,
         --Данные отца
         CUS_CITIZEN f_cit,
         NATIONALITY f_nat,
         --Данные матери
         CUS_CITIZEN m_cit,
         NATIONALITY m_nat,
         -- Заключении брака
         MC_MERCER   mercer,
         PATERN_CERT patern
   where
  --Ребенок
   ch.icusnum(+) = br.br_act_ch
  --Отец
and (f_cit.icusnum(+) = f.icusnum and f_cit.osn(+) = 'Y')
and f_nat.ID(+) = f.ccusnationality
and f.icusnum(+) = br.br_act_f
  --Мать
and m.icusnum(+) = br.br_act_m
and (m_cit.icusnum(+) = m.icusnum and m_cit.osn(+) = 'Y')
and m_nat.ID(+) = m.ccusnationality
and mercer.mercer_id(+) = br.br_act_mercer_id
and patern.pc_id(+) = br.br_act_patcer)

select BR_ACT_ID,
       CH_GENDR,
       MEDORGA,
       BR_ACT_DATE,
       CH_LNAME,
       CH_FNAME,
       CH_MNAME,
       CH_BR_DT,
       CH_PL_BR,
       CH_CNT,
       LB_DB,
       NDOCA,
       DATEDOCA,
       FIOB,
       DATEDOCB,
       NAMECOURT,
       DESCCOURT,
       DCOURT,
       F_L_NAME,
       F_F_NAME,
       F_M_NAME,
       F_BR_DT,
       F_PL_BR,
       F_CITIZ,
       F_NAT,
       F_ADDR,
       M_L_NAME,
       M_F_NAME,
       M_M_NAME,
       M_BR_DT,
       M_PL_BR,
       M_CITIZ,
       M_NAT,
       M_ADDR,
       MERCER_ID,
       MERCER_DATE,
       MERCER_ZAGS,
       PATERN_ID,
       PATERN_DATE,
       PATERN_ZAGS,
       MZDATE,
       ACT_SERIA,
       ACT_NUM
  from data_
;
grant select on XXI.V_REP_BRN_BIRTH_ACT to ODB;


prompt
prompt Creating view V_REP_DEATH_CERT
prompt ==============================
prompt
create or replace force view xxi.v_rep_death_cert as
with data_ as
 (select to_char(DC_ID) DC_ID,
         date2str(DC_OPEN) DC_OPEN,
         cus.ccuslast_name,
         cus.ccusfirst_name,
         cus.ccusmiddle_name,
         date2str(cus.dcusbirthday) dcusbirthday,
         MJCUS.Address(cus.icusnum) Address,
         decode(cus.ccussex, 1, 'Мужской', 2, 'Женский') SEX,
         UTIl.Get_Nat_Name(cus.ccusnationality) Nat_Name,
         util.Get_Citizen(cus.icusnum) CITIZEN,
         DC_DPL,
         DC_CD,
         date2str(DC_DD) DC_DD,
         DC_LLOC,
         DC_SERIA,
         DC_NUMBER,
         case
           when DC_ZTP = 'F' then
            trim(BOTH ',' from DC_FADLAST_NAME || ' ' || DC_FADFIRST_NAME || ' ' ||
                 DC_FADMIDDLE_NAME || ', ' || DC_FADLOCATION)
           when DC_ZTP = 'J' then
            trim(BOTH ',' from DC_FADORG_NAME || ', ' || DC_FADREG_ADR)
         end DC_ZTP,
         --Если А
         case
           when DC_FTYPE = 'A' then
            DC_FMON
           else
            ' '
         end DC_FMON,
         case
           when DC_FTYPE in ('B', 'B1') then
            DC_RCNAME
           else
            ' '
         end DC_RCNAME,
         case
           when DC_FTYPE = 'A' then
            DC_FNUM
           else
            ' '
         end DC_FNUM,
         case
           when DC_FTYPE = 'A' then
            date2str(DC_FD)
           else
            ' '
         end DC_FD,
         case
           when DC_FTYPE = 'V' then
            DC_NRNAME
           else
            ' '
         end DC_NRNAME
    from death_cert dc, cus
   where cus.icusnum(+) = dc.dc_cus)
select nvl(CITIZEN, ' ') "CITIZEN",
       nvl(DC_FNUM, ' ') "DC_FNUM",
       nvl(DC_DD, ' ') "DC_DD",
       nvl(DCUSBIRTHDAY, ' ') "DCUSBIRTHDAY",
       nvl(DC_FMON, ' ') "DC_FMON",
       nvl(NAT_NAME, ' ') "NAT_NAME",
       nvl(DC_SERIA, ' ') "DC_SERIA",
       nvl(DC_ZTP, ' ') "DC_ZTP",
       nvl(DC_DPL, ' ') "DC_DPL",
       nvl(DC_RCNAME, ' ') "DC_RCNAME",
       nvl(DC_LLOC, ' ') "DC_LLOC",
       nvl(DC_OPEN, ' ') "DC_OPEN",
       nvl(DC_CD, ' ') "DC_CD",
       nvl(CCUSLAST_NAME, ' ') "CCUSLAST_NAME",
       nvl(DC_ID, ' ') "DC_ID",
       nvl(DC_NUMBER, ' ') "DC_NUMBER",
       nvl(CCUSMIDDLE_NAME, ' ') "CCUSMIDDLE_NAME",
       nvl(CCUSFIRST_NAME, ' ') "CCUSFIRST_NAME",
       nvl(DC_FD, ' ') "DC_FD",
       nvl(DC_NRNAME, ' ') "DC_NRNAME",
       nvl(ADDRESS, ' ') "ADDRESS",
       nvl(SEX, ' ') "SEX"
  from data_
;
grant select on XXI.V_REP_DEATH_CERT to ODB;


prompt
prompt Creating view V_REP_DIVORCE_CERT
prompt ================================
prompt
create or replace force view xxi.v_rep_divorce_cert as
with data_ as
 (select TO_CHAR(divorce.divc_id) DIVC_ID,
         date2str(divorce.DIVC_DATE) DIVC_DATE,

         divorce.DIVC_HE_LNBEF,
         divorce.DIVC_HE_LNAFT,

         divorce.DIVC_SHE_LNBEF,
         divorce.DIVC_SHE_LNAFT,

         he.ccuslast_name   HE_L_NAME,
         he.ccusfirst_name  HE_F_NAME,
         he.ccusmiddle_name HE_M_NAME,

         she.ccuslast_name   SHE_L_NAME,
         she.ccusfirst_name  SHE_F_NAME,
         she.ccusmiddle_name SHE_M_NAME,

         he.ccusplace_birth HE_BR_PL,
         she.ccusplace_birth SHE_BR_PL,
         util.Get_Citizen(he.icusnum) HE_CIT,
         util.Get_Citizen(she.icusnum) SHE_CIT,

         util.Get_Nat_Name(he.Ccusnationality) HE_NAT,
         util.Get_Nat_Name(she.Ccusnationality) SHE_NAT,

         he_doc.doc_ser     HE_DOC_SER,
         he_doc.doc_num     HE_DOC_NUM,
         she_doc.doc_ser    SHE_DOC_SER,
         she_doc.doc_num    SHE_DOC_NUM,
         he_doc.doc_agency  HE_DOC_AGEN,
         she_doc.doc_agency SHE_DOC_AGEN,

         date2str(DIVC_DT) DIVC_DT,
         case
           when DIVC_TYPE = 'A' then
            date2str(DIVC_TCHD)
           else
            ' '
         end DIVC_TCHD,
         case
           when DIVC_TYPE = 'A' then
            DIVC_TCHNUM
           else
            ' '
         end DIVC_TCHNUM,
         case
           when DIVC_TYPE = 'B' then
            DIVC_CAN
           else
            ' '
         end DIVC_CAN,
         case
           when DIVC_TYPE = 'B' then
            date2str(DIVC_CAD)
           else
            ' '
         end DIVC_CAD,
         case
           when DIVC_TYPE in ('V1', 'V2') then
            DIVC_ZOSCN
           else
            ' '
         end DIVC_ZOSCN,
         case
           when DIVC_TYPE in ('V1', 'V2') then
            date2str(DIVC_ZOSCD)
           else
            ' '
         end DIVC_ZOSCD,
         case
           when DIVC_TYPE in ('V1', 'V2') then
            DIVC_ZOSFIO
           else
            ' '
         end DIVC_ZOSFIO,
         --////////
         case
           when DIVC_TYPE = 'V3' then
            DIVC_ZOSCN2
           else
            ' '
         end DIVC_ZOSCN2,
         case
           when DIVC_TYPE = 'V3' then
            date2str(DIVC_ZOSCD2)
           else
            ' '
         end DIVC_ZOSCD2,
         case
           when DIVC_TYPE = 'V3' then
            DIVC_ZOSFIO2
           else
            ' '
         end DIVC_ZOSFIO2,
         --//////////////////////
         case
           when DIVC_TYPE = 'V3' then
            to_char(DIVC_ZOSPRISON)
           else
            ' '
         end DIVC_ZOSPRISON,
         to_char(mercer.MERCER_ID) MERCER_ID,
         date2str(mercer.MERCER_DATE) MERCER_DATE,
         findzags(mercer.mercer_zags) MERCER_ZAGS,
         mjcus.Address(he.icusnum) HE_ADDR,
         mjcus.Address(she.icusnum) SHE_ADDR,
         trim(both ',' from DIVC_ZLNAME || ', ' || DIVC_ZАNAME || ', ' ||
              DIVC_ZMNAME || ', ' || DIVC_ZPLACE) ZAYAVIT,
         divorce.divc_seria DIV_SER,
         divorce.divc_num DIV_NUM
    from DIVORCE_CERT divorce,
         cus          he,
         cus          she,
         cus_docum    he_doc,
         cus_docum    she_doc,
         mc_mercer    mercer
   where he.icusnum(+) = divorce.DIVC_he
     and she.icusnum(+) = divorce.DIVC_she
     and he_doc.icusnum(+) = he.icusnum
     and she_doc.icusnum(+) = she.icusnum
     and mercer.mercer_id(+) = divorce.DIVC_MC_MERCER)
select nvl(DIVC_HE_LNBEF, ' ') "DIVC_HE_LNBEF",
       nvl(SHE_DOC_SER, ' ') "SHE_DOC_SER",
       nvl(HE_M_NAME, ' ') "HE_M_NAME",
       nvl(MERCER_ZAGS, ' ') "MERCER_ZAGS",
       nvl(DIVC_ZOSFIO2, ' ') "DIVC_ZOSFIO2",
       nvl(DIVC_ZOSFIO, ' ') "DIVC_ZOSFIO",
       nvl(DIVC_DATE, ' ') "DIVC_DATE",
       nvl(DIVC_CAD, ' ') "DIVC_CAD",
       nvl(MERCER_ID, ' ') "MERCER_ID",
       nvl(DIVC_ZOSCD, ' ') "DIVC_ZOSCD",
       nvl(HE_DOC_NUM, ' ') "HE_DOC_NUM",
       nvl(HE_DOC_AGEN, ' ') "HE_DOC_AGEN",
       nvl(MERCER_DATE, ' ') "MERCER_DATE",
       nvl(DIVC_ZOSCN, ' ') "DIVC_ZOSCN",
       nvl(DIVC_ID, ' ') "DIVC_ID",
       nvl(HE_F_NAME, ' ') "HE_F_NAME",
       nvl(SHE_L_NAME, ' ') "SHE_L_NAME",
       nvl(SHE_DOC_NUM, ' ') "SHE_DOC_NUM",
       nvl(HE_CIT, ' ') "HE_CIT",
       nvl(SHE_BR_PL, ' ') "SHE_BR_PL",
       nvl(SHE_M_NAME, ' ') "SHE_M_NAME",
       nvl(HE_NAT, ' ') "HE_NAT",
       nvl(SHE_F_NAME, ' ') "SHE_F_NAME",
       nvl(SHE_NAT, ' ') "SHE_NAT",
       nvl(DIVC_ZOSCN2, ' ') "DIVC_ZOSCN2",
       nvl(DIVC_ZOSPRISON, ' ') "DIVC_ZOSPRISON",
       nvl(SHE_DOC_AGEN, ' ') "SHE_DOC_AGEN",
       nvl(DIVC_CAN, ' ') "DIVC_CAN",
       nvl(DIVC_TCHD, ' ') "DIVC_TCHD",
       nvl(ZAYAVIT, ' ') "ZAYAVIT",
       nvl(DIVC_DT, ' ') "DIVC_DT",
       nvl(DIVC_SHE_LNAFT, ' ') "DIVC_SHE_LNAFT",
       nvl(SHE_CIT, ' ') "SHE_CIT",
       nvl(HE_BR_PL, ' ') "HE_BR_PL",
       nvl(HE_ADDR, ' ') "HE_ADDR",
       nvl(DIVC_ZOSCD2, ' ') "DIVC_ZOSCD2",
       nvl(HE_DOC_SER, ' ') "HE_DOC_SER",
       nvl(DIV_NUM, ' ') "DIV_NUM",
       nvl(DIVC_TCHNUM, ' ') "DIVC_TCHNUM",
       nvl(DIV_SER, ' ') "DIV_SER",
       nvl(SHE_ADDR, ' ') "SHE_ADDR",
       nvl(HE_L_NAME, ' ') "HE_L_NAME",
       nvl(DIVC_HE_LNAFT, ' ') "DIVC_HE_LNAFT",
       nvl(DIVC_SHE_LNBEF, ' ') "DIVC_SHE_LNBEF"
  from data_
;
grant select on XXI.V_REP_DIVORCE_CERT to ODB;


prompt
prompt Creating view V_REP_MC_MERCER
prompt =============================
prompt
create or replace force view xxi.v_rep_mc_mercer as
with data_ as
 (select TO_CHAR(MERCER_ID) MERCER_ID,
         date2str(MERCER_DATE) MERCER_DATE,
         mercer.MERCER_HE_LNBEF,
         mercer.MERCER_HE_LNAFT,
         mercer.MERCER_SHE_LNBEF,
         mercer.MERCER_SHE_LNBAFT,
         he.ccuslast_name HE_L_NAME,
         he.ccusfirst_name HE_F_NAME,
         he.ccusmiddle_name HE_M_NAME,

         she.ccuslast_name   SHE_L_NAME,
         she.ccusfirst_name  SHE_F_NAME,
         she.ccusmiddle_name SHE_M_NAME,

         he.ccusplace_birth HE_BR_PL,
         she.ccusplace_birth SHE_BR_PL,
         util.Get_Citizen(he.icusnum) HE_CIT,
         util.Get_Citizen(she.icusnum) SHE_CIT,

         util.Get_Nat_Name(he.Ccusnationality) HE_NAT,
         util.Get_Nat_Name(she.Ccusnationality) SHE_NAT,
         ----Он
         --Если А
         CASE
           WHEN MERCER_DSPMT_HE = 'A' THEN
            date2str(he_div.DIVC_DATE)
           ELSE
            ' '
         END HE_DIVC_DATE,
         CASE
           WHEN MERCER_DSPMT_HE = 'A' THEN
            TO_CHAR(he_div.DIVC_ID)
           ELSE
            ' '
         END HE_DIVC_ID,
         CASE
           WHEN MERCER_DSPMT_HE = 'A' THEN
            findzags(he_div.divc_zags)
           ELSE
            ' '
         END HE_DIVC_ZAGS,
         --Если Б
         --///////////////////
         CASE
           WHEN MERCER_DSPMT_HE = 'B' THEN
            date2str(he_dth.DC_OPEN)
           ELSE
            ' '
         END HE_DC_OPEN,
         CASE
           WHEN MERCER_DSPMT_HE = 'B' THEN
            TO_CHAR(he_dth.DC_ID)
           ELSE
            ' '
         END HE_DC_ID,
         CASE
           WHEN MERCER_DSPMT_HE = 'B' THEN
            findzags(he_dth.DC_ZAGS)
           ELSE
            ' '
         END HE_DC_ZAGS,
         ----Она
         --Если А
         CASE
           WHEN MERCER_DSPMT_SHE = 'A' THEN
            date2str(she_div.DIVC_DATE)
           ELSE
            ' '
         END SHE_DIVC_DATE,
         CASE
           WHEN MERCER_DSPMT_SHE = 'A' THEN
            TO_CHAR(she_div.DIVC_ID)
           ELSE
            ' '
         END SHE_DIVC_ID,
         CASE
           WHEN MERCER_DSPMT_SHE = 'A' THEN
            findzags(she_div.divc_zags)
           ELSE
            ' '
         END SHE_DIVC_ZAGS,
         --Если Б
         --///////////////////
         CASE
           WHEN MERCER_DSPMT_SHE = 'B' THEN
            date2str(she_dth.DC_OPEN)
           ELSE
            ' '
         END SHE_DC_OPEN,
         CASE
           WHEN MERCER_DSPMT_SHE = 'B' THEN
            TO_CHAR(she_dth.DC_ID)
           ELSE
            ' '
         END SHE_DC_ID,
         CASE
           WHEN MERCER_DSPMT_SHE = 'B' THEN
            findzags(she_dth.DC_ZAGS)
           ELSE
            ' '
         END SHE_DC_ZAGS,
         --//////////////////////
         mjcus.Address(he.icusnum) HE_ADDR,
         mjcus.Address(she.icusnum) SHE_ADDR,
         --///////
         he_doc.doc_ser      HE_DOC_SER,
         he_doc.doc_num      HE_DOC_NUM,
         she_doc.doc_ser     SHE_DOC_SER,
         she_doc.doc_num     SHE_DOC_NUM,
         he_doc.doc_agency   HE_DOC_AGEN,
         she_doc.doc_agency  SHE_DOC_AGEN,
         mercer.mercer_seria MERCER_SERIA,
         mercer.mercer_num   MERCER_NUM

    from MC_MERCER    mercer,
         cus          he,
         cus          she,
         divorce_cert he_div,
         divorce_cert she_div,
         death_cert   he_dth,
         death_cert   she_dth,
         cus_docum    he_doc,
         cus_docum    she_doc
   where he.icusnum(+) = mercer.mercer_he
     and she.icusnum(+) = mercer.mercer_she
     and he_div.divc_id(+) = mercer.MERCER_DIVHE
     and she_div.divc_id(+) = mercer.MERCER_DIVSHE
     and he_dth.dc_id(+) = mercer.mercer_diehe
     and she_dth.dc_id(+) = mercer.mercer_dieshe
     and he_doc.icusnum(+) = he.icusnum
     and she_doc.icusnum(+) = she.icusnum)
select nvl(HE_CIT, ' ') "HE_CIT",
       nvl(HE_DC_ID, ' ') "HE_DC_ID",
       nvl(HE_DIVC_DATE, ' ') "HE_DIVC_DATE",
       nvl(HE_DC_OPEN, ' ') "HE_DC_OPEN",
       nvl(SHE_DOC_SER, ' ') "SHE_DOC_SER",
       nvl(SHE_DOC_AGEN, ' ') "SHE_DOC_AGEN",
       nvl(MERCER_SHE_LNBEF, ' ') "MERCER_SHE_LNBEF",
       nvl(SHE_CIT, ' ') "SHE_CIT",
       nvl(SHE_DOC_NUM, ' ') "SHE_DOC_NUM",
       nvl(HE_DOC_NUM, ' ') "HE_DOC_NUM",
       nvl(SHE_BR_PL, ' ') "SHE_BR_PL",
       nvl(SHE_DIVC_ID, ' ') "SHE_DIVC_ID",
       nvl(SHE_DC_ID, ' ') "SHE_DC_ID",
       nvl(HE_DOC_AGEN, ' ') "HE_DOC_AGEN",
       nvl(MERCER_HE_LNAFT, ' ') "MERCER_HE_LNAFT",
       nvl(SHE_L_NAME, ' ') "SHE_L_NAME",
       nvl(HE_F_NAME, ' ') "HE_F_NAME",
       nvl(SHE_NAT, ' ') "SHE_NAT",
       nvl(MERCER_HE_LNBEF, ' ') "MERCER_HE_LNBEF",
       nvl(HE_DOC_SER, ' ') "HE_DOC_SER",
       nvl(SHE_DC_OPEN, ' ') "SHE_DC_OPEN",
       nvl(HE_ADDR, ' ') "HE_ADDR",
       nvl(SHE_DIVC_DATE, ' ') "SHE_DIVC_DATE",
       nvl(HE_DIVC_ID, ' ') "HE_DIVC_ID",
       nvl(SHE_DIVC_ZAGS, ' ') "SHE_DIVC_ZAGS",
       nvl(HE_DC_ZAGS, ' ') "HE_DC_ZAGS",
       nvl(SHE_F_NAME, ' ') "SHE_F_NAME",
       nvl(MERCER_DATE, ' ') "MERCER_DATE",
       nvl(HE_DIVC_ZAGS, ' ') "HE_DIVC_ZAGS",
       nvl(MERCER_SERIA, ' ') "MERCER_SERIA",
       nvl(MERCER_SHE_LNBAFT, ' ') "MERCER_SHE_LNBAFT",
       nvl(MERCER_ID, ' ') "MERCER_ID",
       nvl(HE_NAT, ' ') "HE_NAT",
       nvl(SHE_ADDR, ' ') "SHE_ADDR",
       nvl(MERCER_NUM, ' ') "MERCER_NUM",
       nvl(SHE_M_NAME, ' ') "SHE_M_NAME",
       nvl(HE_L_NAME, ' ') "HE_L_NAME",
       nvl(HE_M_NAME, ' ') "HE_M_NAME",
       nvl(SHE_DC_ZAGS, ' ') "SHE_DC_ZAGS",
       nvl(HE_BR_PL, ' ') "HE_BR_PL"
  from data_
;
grant select on XXI.V_REP_MC_MERCER to ODB;


prompt
prompt Creating package DBMS_SQL_ADD
prompt =============================
prompt
CREATE OR REPLACE PACKAGE XXI.DBMS_SQL_ADD IS

  FUNCTION Open_Parse(cSQL IN VARCHAR2) RETURN INTEGER;

  FUNCTION EXEC_PARAM(params VARCHAR2) RETURN varchar2;

  PROCEDURE Execute_SQL(SQL_Str IN VARCHAR2);

  PROCEDURE Commit;

  PROCEDURE RollBack;

END DBMS_SQL_ADD;
/
grant execute on XXI.DBMS_SQL_ADD to ODB;


prompt
prompt Creating view VREP_PARAMS
prompt =========================
prompt
create or replace force view xxi.vrep_params as
select rep_id,
       prm_name,
       prm_id,
       is_list,
       list_query,
       case
         when prm_def_value like '&%' then
          DBMS_SQL_ADD.EXEC_PARAM(PRM_DEF_VALUE)
         else
          prm_def_value
       end prm_def_value
  from REP_PARAMS;
grant select on XXI.VREP_PARAMS to ODB;


prompt
prompt Creating view V_REP_PATERN_CERT
prompt ===============================
prompt
create or replace force view xxi.v_rep_patern_cert as
with data_ as
 (select date2str(PC.PС_DATE) PС_DATE,
         TO_CHAR(pc.pc_id) PC_ID,
         ch.ccuslast_name CH_LNAME,
         ch.ccusfirst_name CH_FNAME,
         ch.Ccusmiddle_Name CH_MNAME,
         decode(ch.ccussex, 1, 'Мужской', 2, 'Женский') CH_SEX,
         date2str(CH.DCUSBIRTHDAY) CH_BRDT,
         CH.CCUSPLACE_BIRTH CH_BR_PL,
         TO_CHAR(ch_br_act.br_act_id) CH_ACT_NUM,
         date2str(BR_ACT_DATE) BR_ACT_DATE,
         findzags(ch_br_act.br_act_zgid) ZAGS_NAME,
         PС_AFT_LNAME,
         PС_AFT_FNAME,
         PС_AFT_MNAME,
         f.ccuslast_name F_LNAME,
         f.ccusfirst_name F_FNAME,
         f.Ccusmiddle_Name F_MNAME,
         m.ccuslast_name M_LNAME,
         m.ccusfirst_name M_FNAME,
         m.Ccusmiddle_Name M_MNAME,
         date2str(f.dcusbirthday) F_BR_DT,
         date2str(m.dcusbirthday) M_BR_DT,
         f.CCUSPLACE_BIRTH F_BR_PL,
         m.CCUSPLACE_BIRTH M_BR_PL,
         util.Get_Citizen(f.icusnum) F_CITIZEN,
         util.Get_Citizen(m.icusnum) M_CITIZEN,
         util.Get_Nat_Name(f.ccusnationality) F_NAT,
         util.Get_Nat_Name(m.ccusnationality) M_NAT,
         mjcus.Address(f.icusnum) F_ADDR,
         mjcus.Address(m.icusnum) M_ADDR,
         PC_ZMNAME,
         PC_ZFNAME,
         PC_ZLNAME,
         PC_ZPLACE,
         PС_SERIA,
         PС_NUMBER,
         case
           when PС_TYPE = 'B' then
            date2str(PС_FZ)
           else
            ' '
         end PС_FZ,
         case
           when PС_TYPE = 'A' then
            date2str(PС_TRZ)
           else
            ' '
         end PС_TRZ,
         case
           when PС_TYPE in ('V1', 'V2') then
            date2str(PС_CRDATE)
           else
            ' '
         end PС_CRDATE,
         case
           when PС_TYPE in ('V1', 'V2') then
            PС_CRNAME
           else
            ' '
         end PС_CRNAME
    from PATERN_CERT pc, cus ch, brn_birth_act ch_br_act, cus f, cus m
   where ch.icusnum(+) = pc.pс_ch
     and f.icusnum(+) = pc.pс_f
     and m.icusnum(+) = pc.pс_m
     and ch_br_act.br_act_id(+) = pc.pc_act_id)
select nvl(PС_DATE, ' ') "PС_DATE",
       nvl(PC_ID, ' ') "PC_ID",
       nvl(CH_LNAME, ' ') "CH_LNAME",
       nvl(CH_FNAME, ' ') "CH_FNAME",
       nvl(CH_MNAME, ' ') "CH_MNAME",
       nvl(CH_SEX, ' ') "CH_SEX",
       nvl(CH_BRDT, ' ') "CH_BRDT",
       nvl(CH_BR_PL, ' ') "CH_BR_PL",
       nvl(CH_ACT_NUM, ' ') "CH_ACT_NUM",
       nvl(BR_ACT_DATE, ' ') "BR_ACT_DATE",
       nvl(ZAGS_NAME, ' ') "ZAGS_NAME",
       nvl(PС_AFT_LNAME, ' ') "PС_AFT_LNAME",
       nvl(PС_AFT_FNAME, ' ') "PС_AFT_FNAME",
       nvl(PС_AFT_MNAME, ' ') "PС_AFT_MNAME",
       nvl(F_LNAME, ' ') "F_LNAME",
       nvl(F_FNAME, ' ') "F_FNAME",
       nvl(F_MNAME, ' ') "F_MNAME",
       nvl(M_LNAME, ' ') "M_LNAME",
       nvl(M_FNAME, ' ') "M_FNAME",
       nvl(M_MNAME, ' ') "M_MNAME",
       nvl(F_BR_DT, ' ') "F_BR_DT",
       nvl(M_BR_DT, ' ') "M_BR_DT",
       nvl(F_BR_PL, ' ') "F_BR_PL",
       nvl(M_BR_PL, ' ') "M_BR_PL",
       nvl(F_CITIZEN, ' ') "F_CITIZEN",
       nvl(M_CITIZEN, ' ') "M_CITIZEN",
       nvl(F_NAT, ' ') "F_NAT",
       nvl(M_NAT, ' ') "M_NAT",
       nvl(F_ADDR, ' ') "F_ADDR",
       nvl(M_ADDR, ' ') "M_ADDR",
       nvl(PC_ZMNAME, ' ') "PC_ZMNAME",
       nvl(PC_ZFNAME, ' ') "PC_ZFNAME",
       nvl(PC_ZLNAME, ' ') "PC_ZLNAME",
       nvl(PC_ZPLACE, ' ') "PC_ZPLACE",
       nvl(PС_SERIA, ' ') "PС_SERIA",
       nvl(PС_NUMBER, ' ') "PС_NUMBER",
       nvl(PС_FZ, ' ') "PС_FZ",
       nvl(PС_TRZ, ' ') "PС_TRZ",
       nvl(PС_CRDATE, ' ') "PС_CRDATE",
       nvl(PС_CRNAME, ' ') "PС_CRNAME"

  from data_;
grant select on XXI.V_REP_PATERN_CERT to ODB;


prompt
prompt Creating view V_REP_UPDATE_ABH_NAME
prompt ===================================
prompt
create or replace force view xxi.v_rep_update_abh_name as
with data_ as
 (select id,
         old_lastname,
         old_firstname,
         old_middlname,
         new_lastname,
         new_firstname,
         new_middlname,
         (select Date2Str(cus.dcusbirthday)
            from cus
           where cus.icusnum = t.cusid) dcusbirthday,
         (select cus.CCUSPLACE_BIRTH from cus where cus.icusnum = t.cusid) CCUSPLACE_BIRTH,
         (select act.br_act_id
            from brn_birth_act act
           where act.br_act_id = t.brn_act_id) br_act_id,
         (select Date2Str(trunc(act.br_act_date))
            from brn_birth_act act
           where act.br_act_id = t.brn_act_id) br_act_date,
         (select zags.zags_name from zags where zags.zags_id = t.zags_id) zags_name,
         (select cit.COUNTRY_NAME
            from CUS_CITIZEN cit
           where cit.icusnum = t.cusid
             and OSN = 'Y') COUNTRY_NAME,
         (select nat.NAME
            from NATIONALITY nat
           where nat.ID = (select cus.CCUSNATIONALITY
                             from cus
                            where cus.icusnum = t.cusid)) NATIONALITY,
         MJCUS.Address(t.cusid) Address,
         t.svid_seria,
         t.svid_number,
         Date2Str(t.doc_date) CURDATE
    from UPDATE_ABH_NAME t)
select ID,
       OLD_LASTNAME,
       OLD_FIRSTNAME,
       OLD_MIDDLNAME,
       NEW_LASTNAME,
       NEW_FIRSTNAME,
       NEW_MIDDLNAME,
       DCUSBIRTHDAY,
       CCUSPLACE_BIRTH,
       BR_ACT_ID,
       BR_ACT_DATE,
       ZAGS_NAME,
       COUNTRY_NAME,
       NATIONALITY,
       ADDRESS,
       SVID_SERIA,
       SVID_NUMBER,
       CURDATE
  from data_;
grant select on XXI.V_REP_UPDATE_ABH_NAME to ODB;


prompt
prompt Creating view V_REP_UPDATE_NAME
prompt ===============================
prompt
create or replace force view xxi.v_rep_update_name as
with data_ as
 (select id,
         old_lastname,
         old_firstname,
         old_middlname,
         new_lastname,
         new_firstname,
         new_middlname,
         (select Date2Str(cus.dcusbirthday)
            from cus
           where cus.icusnum = t.cusid) dcusbirthday,
         (select cus.CCUSPLACE_BIRTH from cus where cus.icusnum = t.cusid) CCUSPLACE_BIRTH,
         (select act.br_act_id
            from brn_birth_act act
           where act.br_act_id = t.brn_act_id) br_act_id,
         (select Date2Str(trunc(act.br_act_date))
            from brn_birth_act act
           where act.br_act_id = t.brn_act_id) br_act_date,
         (select zags.zags_name from zags where zags.zags_id = t.zags_id) zags_name,
         (select cit.COUNTRY_NAME
            from CUS_CITIZEN cit
           where cit.icusnum = t.cusid
             and OSN = 'Y') COUNTRY_NAME,
         (select nat.NAME
            from NATIONALITY nat
           where nat.ID = (select cus.CCUSNATIONALITY
                             from cus
                            where cus.icusnum = t.cusid)) NATIONALITY,
         MJCUS.Address(t.cusid) Address,
         t.svid_seria,
         t.svid_number,
         Date2Str(t.doc_date) CURDATE
    from update_name t)
select ID,
       OLD_LASTNAME,
       OLD_FIRSTNAME,
       OLD_MIDDLNAME,
       NEW_LASTNAME,
       NEW_FIRSTNAME,
       NEW_MIDDLNAME,
       DCUSBIRTHDAY,
       CCUSPLACE_BIRTH,
       BR_ACT_ID,
       BR_ACT_DATE,
       ZAGS_NAME,
       COUNTRY_NAME,
       NATIONALITY,
       ADDRESS,
       SVID_SERIA,
       SVID_NUMBER,
       CURDATE
  from data_;
grant select on XXI.V_REP_UPDATE_NAME to ODB;


prompt
prompt Creating view V_REP_UPD_NAT
prompt ===========================
prompt
create or replace force view xxi.v_rep_upd_nat as
with data_ as
 (select id,
         (select nationality.name
            from nationality
           where nationality.id = t.old_nat) old_nat,
         (select nationality.name
            from nationality
           where nationality.id = t.new_nat) new_nat,
         (select Date2Str(cus.dcusbirthday)
            from cus
           where cus.icusnum = t.cusid) dcusbirthday,
         (select cus.ccuslast_name from cus where cus.icusnum = t.cusid) lastname,
         (select cus.ccusfirst_name from cus where cus.icusnum = t.cusid) firstname,
         (select cus.ccusmiddle_name from cus where cus.icusnum = t.cusid) middlname,
         (select cus.CCUSPLACE_BIRTH from cus where cus.icusnum = t.cusid) CCUSPLACE_BIRTH,
         (select act.br_act_id
            from brn_birth_act act
           where act.br_act_id = t.brn_act_id) br_act_id,
         (select Date2Str(trunc(act.br_act_date))
            from brn_birth_act act
           where act.br_act_id = t.brn_act_id) br_act_date,
         (select zags.zags_name from zags where zags.zags_id = t.zags_id) zags_name,
         (select cit.COUNTRY_NAME
            from CUS_CITIZEN cit
           where cit.icusnum = t.cusid
             and OSN = 'Y') COUNTRY_NAME,
         (select nat.NAME
            from NATIONALITY nat
           where nat.ID = (select cus.CCUSNATIONALITY
                             from cus
                            where cus.icusnum = t.cusid)) NATIONALITY,
         MJCUS.Address(t.cusid) Address,
         t.svid_seria,
         t.svid_number,
         Date2Str(t.doc_date) CURDATE
    from UPD_NAT t)
select ID,
       DCUSBIRTHDAY,
       OLD_NAT,
       NEW_NAT,
       CCUSPLACE_BIRTH,
       BR_ACT_ID,
       BR_ACT_DATE,
       ZAGS_NAME,
       COUNTRY_NAME,
       NATIONALITY,
       ADDRESS,
       SVID_SERIA,
       SVID_NUMBER,
       CURDATE,
       lastname,
       firstname,
       middlname
  from data_;
grant select on XXI.V_REP_UPD_NAT to ODB;


prompt
prompt Creating view VUPDATE_ABH_NAME
prompt ==============================
prompt
create or replace force view xxi.vupdate_abh_name as
select cus.ccusname FIO,
       abh_nm."ID",
       abh_nm."OLD_LASTNAME",
       abh_nm."OLD_FIRSTNAME",
       abh_nm."OLD_MIDDLNAME",
       abh_nm."NEW_LASTNAME",
       abh_nm."NEW_FIRSTNAME",
       abh_nm."NEW_MIDDLNAME",
       abh_nm."BRN_ACT_ID",
       abh_nm."DOC_DATE" TM$DOC_DATE,
       abh_nm."OPER",
       abh_nm."ZAGS_ID",
       abh_nm."CUSID",
       abh_nm."SVID_NUMBER",
       abh_nm."SVID_SERIA",
       to_date(doc_date, 'DD.MM.RRRR') CR_DATE,
       to_char(doc_date, 'HH24:MI:SS') CR_TIME
  from UPDATE_ABH_NAME abh_nm, cus
 where cus.icusnum(+) = abh_nm.cusid
      --где документ из загса пользователя
   and (exists (select null
           from zags zg_list, usr usr_d
          where usr_d.zags_id = zg_list.zags_id
            and usr_d.cusrlogname = user
            and zg_list.zags_id = abh_nm.zags_id)
        -- или может есть группа
        or exists (select null
           from ODB_GRP_MEMBER mem, usr, ODB_GROUP_USR grp
          where mem.iusrid = usr.iusrid
            and mem.grp_id = grp.grp_id
            and grp.grp_name = 'ZAGS_UPD_ABH_NAME_ALL'
            and usr.cusrlogname = user))
;
grant select on XXI.VUPDATE_ABH_NAME to ODB;


prompt
prompt Creating view VUPDATE_NAME
prompt ==========================
prompt
create or replace force view xxi.vupdate_name as
select cus.ccusname FIO,
       upd_nm."ID",
       upd_nm."OLD_LASTNAME_AB",
       upd_nm."OLD_FIRSTNAME_AB",
       upd_nm."OLD_MIDDLNAME_AB",
       upd_nm."NEW_LASTNAME_AB",
       upd_nm."NEW_FIRSTNAME_AB",
       upd_nm."NEW_MIDDLNAME_AB",
       upd_nm."OLD_LASTNAME",
       upd_nm."OLD_FIRSTNAME",
       upd_nm."OLD_MIDDLNAME",
       upd_nm."NEW_LASTNAME",
       upd_nm."NEW_FIRSTNAME",
       upd_nm."NEW_MIDDLNAME",
       upd_nm."BRN_ACT_ID",
       upd_nm."DOC_DATE" TM$DOC_DATE,
       upd_nm."OPER",
       upd_nm."ZAGS_ID",
       upd_nm."CUSID",
       upd_nm."SVID_NUMBER",
       upd_nm."SVID_SERIA",
       to_date(doc_date, 'DD.MM.RRRR') CR_DATE,
       to_char(doc_date, 'HH24:MI:SS') CR_TIME
  from UPDATE_NAME upd_nm, cus
 where cus.icusnum(+) = upd_nm.cusid
      --где документ из загса пользователя
   and (exists (select null
           from zags zg_list, usr usr_d
          where usr_d.zags_id = zg_list.zags_id
            and usr_d.cusrlogname = user
            and zg_list.zags_id = upd_nm.zags_id)
        -- или может есть группа
        or exists (select null
           from ODB_GRP_MEMBER mem, usr, ODB_GROUP_USR grp
          where mem.iusrid = usr.iusrid
            and mem.grp_id = grp.grp_id
            and grp.grp_name = 'ZAGS_UPD_NAME_ALL'
            and usr.cusrlogname = user))
;
grant select on XXI.VUPDATE_NAME to ODB;


prompt
prompt Creating view VUPD_NAT
prompt ======================
prompt
create or replace force view xxi.vupd_nat as
select cus.ccusname FIO,
       util.Get_Nat_Name(new_nat) old_nat,
       util.Get_Nat_Name(new_nat) new_nat,
       id,
       cusid,
       oper,
       doc_date TM$doc_date,
       zags_id,
       brn_act_id,
       svid_seria,
       svid_number,
       to_date(doc_date, 'DD.MM.RRRR') CR_DATE,
       to_char(doc_date, 'HH24:MI:SS') CR_TIME
  from UPD_NAT nat, cus
 where cus.icusnum(+) = nat.cusid
      --где документ из загса пользователя
   and (exists (select null
           from zags zg_list, usr usr_d
          where usr_d.zags_id = zg_list.zags_id
            and usr_d.cusrlogname = user
            and zg_list.zags_id = nat.zags_id)
        -- или может есть группа
        or exists (select null
           from ODB_GRP_MEMBER mem, usr, ODB_GROUP_USR grp
          where mem.iusrid = usr.iusrid
            and mem.grp_id = grp.grp_id
            and grp.grp_name = 'ZAGS_UPD_NAT_ALL'
            and usr.cusrlogname = user))
;
grant select on XXI.VUPD_NAT to ODB;


prompt
prompt Creating view VZAGS
prompt ===================
prompt
create or replace force view xxi.vzags as
select otd.cotdname,
       zags.zags_id,
       zags.zags_otd,
       zags.zags_name,
       zags.zags_ruk,
       zags.zags_adr,
       zags.zags_city_abh,
       zags.zags_adr_abh,
       zags.zags_ruk_abh,
       zags.addr,
       zags.addr_abh
  from zags, otd
 where zags.zags_otd = otd.iotdnum(+);
grant select on XXI.VZAGS to ODB;


prompt
prompt Creating package AC
prompt ===================
prompt
create or replace package xxi.AC is

  -- Author  : SAID
  -- Created : 23.12.2020 0:20:13
  -- Purpose : 
  FUNCTION CheckCode(cError OUT VARCHAR2,
                     cType  IN VARCHAR2,
                     cCode  IN VARCHAR2) RETURN BOOLEAN;
end AC;
/
grant execute on XXI.AC to ODB;


prompt
prompt Creating package ADOPT
prompt ======================
prompt
create or replace package xxi.ADOPT is

  procedure EditAdopt(error               out varchar2,
                      id_                 number,
                      OLD_LASTNAME_       VARCHAR2, /*Фамилия до*/
                      OLD_FIRSTNAME_      VARCHAR2, /*Имя до*/
                      OLD_MIDDLNAME_      VARCHAR2, /*Отчество до*/
                      NEW_LASTNAME_       VARCHAR2, /*Фамилия после*/
                      NEW_FIRSTNAME_      VARCHAR2, /*Имя после*/
                      NEW_MIDDLNAME_      VARCHAR2, /*Отчество после*/
                      CUSID_CH_           NUMBER, /*ребенок, ссылка на клиента*/
                      CUSID_M_            NUMBER, /*мать, ссылка на клиента*/
                      CUSID_F_            NUMBER, /*отец, ссылка на клиента*/
                      BRNACT_             NUMBER, /*рождение ребенка*/
                      SVID_SERIA_         VARCHAR2, /*серия*/
                      SVID_NOMER_         VARCHAR2, /*номер*/
                      CUSID_M_AD_         NUMBER, /*мать усынов.*/
                      CUSID_F_AD_         NUMBER, /*отец усынов.*/
                      ADOPT_PARENTS_      VARCHAR2, /*записываются ли усыновители родителями ребенка*/
                      ZAP_ISPOLKOM_RESH_  VARCHAR2, /*Решение исполкома (осн. записи об усыновл.)*/
                      ZAP_SOVET_DEP_TRUD_ VARCHAR2, /*Совета депутатов трудящихся (края, республики) (осн. записи об усыновл.)*/
                      ZAP_DATE_           DATE, /*дата (осн. записи об усыновл.)*/
                      ZAP_NUMBER_         VARCHAR2, /*номер (осн. записи об усыновл.)*/
                      NEW_BRTH_           DATE, /*дата рождения после*/
                      OLD_BRTH_           DATE, /*дата рождения до*/
                      BRN_CITY_           VARCHAR2,
                      BRN_AREA_           VARCHAR2,
                      BRN_OBL_RESP_       VARCHAR2);

  procedure AddAdopt(error               out varchar2,
                     id_                 out number,
                     OLD_LASTNAME_       VARCHAR2, /*Фамилия до*/
                     OLD_FIRSTNAME_      VARCHAR2, /*Имя до*/
                     OLD_MIDDLNAME_      VARCHAR2, /*Отчество до*/
                     NEW_LASTNAME_       VARCHAR2, /*Фамилия после*/
                     NEW_FIRSTNAME_      VARCHAR2, /*Имя после*/
                     NEW_MIDDLNAME_      VARCHAR2, /*Отчество после*/
                     CUSID_CH_           NUMBER, /*ребенок, ссылка на клиента*/
                     CUSID_M_            NUMBER, /*мать, ссылка на клиента*/
                     CUSID_F_            NUMBER, /*отец, ссылка на клиента*/
                     BRNACT_             NUMBER, /*рождение ребенка*/
                     SVID_SERIA_         VARCHAR2, /*серия*/
                     SVID_NOMER_         VARCHAR2, /*номер*/
                     CUSID_M_AD_         NUMBER, /*мать усынов.*/
                     CUSID_F_AD_         NUMBER, /*отец усынов.*/
                     ADOPT_PARENTS_      VARCHAR2, /*записываются ли усыновители родителями ребенка*/
                     ZAP_ISPOLKOM_RESH_  VARCHAR2, /*Решение исполкома (осн. записи об усыновл.)*/
                     ZAP_SOVET_DEP_TRUD_ VARCHAR2, /*Совета депутатов трудящихся (края, республики) (осн. записи об усыновл.)*/
                     ZAP_DATE_           DATE, /*дата (осн. записи об усыновл.)*/
                     ZAP_NUMBER_         VARCHAR2, /*номер (осн. записи об усыновл.)*/
                     NEW_BRTH_           DATE, /*дата рождения после*/
                     OLD_BRTH_           DATE, /*дата рождения до*/
                     BRN_CITY_           VARCHAR2,
                     BRN_AREA_           VARCHAR2,
                     BRN_OBL_RESP_       VARCHAR2);
  procedure CompareXmls(ID    in number,
                        clob_ in clob,
                        error out varchar2,
                        res   out number);
  procedure RetXmls(ID number, error out varchar2, clob_ out clob);
end ADOPT;
/
grant execute on XXI.ADOPT to ODB;


prompt
prompt Creating package AUDITING
prompt =========================
prompt
create or replace package xxi.auditing IS

  V_SESSION            NUMBER;
  V_MACHINE            VARCHAR2(64);
  V_PROGRAM            VARCHAR2(64);
  V_ACTION             VARCHAR2(64);
  V_MODULE             VARCHAR2(64);
  V_OSUSER             VARCHAR2(64);
  V_USER               VARCHAR2(64);
  V_IP_ADDRESS         VARCHAR2(64);
  V_CLIENT_IDENTIFIER  VARCHAR2(64);
  V_ID_SESSION         NUMBER;
  V_DATE_SESSION       DATE;
  ID_MACHINE           NUMBER;
  ID_PROGRAM           NUMBER;
  ID_ACTION            NUMBER;
  ID_MODULE            NUMBER;
  ID_OSUSER            NUMBER;
  ID_USER              NUMBER;
  ID_IP_ADDRESS        NUMBER;
  ID_CLIENT_IDENTIFIER NUMBER;

  User_Sysdate DATE;
END;
/
grant execute on XXI.AUDITING to ODB;


prompt
prompt Creating package BURN_UTIL
prompt ==========================
prompt
create or replace package xxi.BURN_UTIL is

  -- Author  : SAID
  -- Created : 04.10.2020 15:11:10
  -- Purpose : Пакет регистрации акта о рождении ребенка
  function CRE_BURN(BR_ACT_ZTP_            VARCHAR2, /*Тип заявителя.F-физ.-J-юр.*/
                    BR_ACT_BRCHCNT_        NUMBER, /*Количество родившихся детей*/
                    BR_ACT_LD_             VARCHAR2, /*Живорожденный или мертворожденный*/
                    BR_ACT_TGRABF_         VARCHAR2, /*Тип основании сведении об отце*/
                    BR_ACT_MZDATE_         DATE, /*Дата заявления от матери, если (BR_ACT_TGRABF = V)*/
                    BR_ACT_DBF_            VARCHAR2, /*Документ подтверждающий факт орождении ребенка, тип*/
                    BR_ACT_CH_             NUMBER, /*Ссылка на ребенка*/
                    BR_ACT_F_              NUMBER, /*Ссылка на отца*/
                    BR_ACT_M_              NUMBER, /*Ссылка на мать*/
                    BR_ACT_MEDORGA_        VARCHAR2, /*Наименование мед. орг. выд. документ (A-Док. уст. формы)*/
                    BR_ACT_DATEDOCA_       DATE, /*Дата документа  (A-Док. уст. формы)*/
                    BR_ACT_NDOCA_          VARCHAR2, /*Номер документа  (A-Док. уст. формы)*/
                    BR_ACT_FIOB_           VARCHAR2, /*ФИО лица присутствовавшего во время родов (Б-Заявление)*/
                    BR_ACT_DATEDOCB_       DATE, /*Дата документа  (Б-Заявление)*/
                    BR_ACT_NAMECOURT_      VARCHAR2, /*Наимнование суда*/
                    BR_ACT_DESCCOURT_      VARCHAR2, /*Решение суда №*/
                    BR_ACT_DCOURT_         DATE, /*Дата решения суда*/
                    BR_ACT_FADFIRST_NAME_  VARCHAR2, /*Имя (Заявитель о рождении)*/
                    BR_ACT_FADLAST_NAME_   VARCHAR2, /*Фамилия (Заявитель о рождении)*/
                    BR_ACT_FADMIDDLE_NAME_ VARCHAR2, /*Отчество (Заявитель о рождении)*/
                    BR_ACT_FADLOCATION_    VARCHAR2, /*Место жительства (Заявитель о рождении)*/
                    BR_ACT_FADORG_NAME_    VARCHAR2, /*Наименование организации (Заявитель о рождении)*/
                    BR_ACT_FADREG_ADR_     VARCHAR2, /*Адрес регистрации (Заявитель о рождении)*/
                    BR_ACT_MERCER_ID_      NUMBER, /*Ссылка на свидетельство о заключении брака */
                    BR_ACT_NUM_            VARCHAR2, /*Номер (печать ЗАГСа)*/
                    BR_ACT_SERIA_          VARCHAR2, /*Серия (печать ЗАГСа)*/
                    BR_ACT_PATCER_         NUMBER, /*Ссылка на установл. отц*/
                    CRID                   OUT NUMBER) return varchar2;
  function EDIT_BURN(BR_ACT_ZTP_            VARCHAR2, /*Тип заявителя.F-физ.-J-юр.*/
                     BR_ACT_BRCHCNT_        NUMBER, /*Количество родившихся детей*/
                     BR_ACT_LD_             VARCHAR2, /*Живорожденный или мертворожденный*/
                     BR_ACT_TGRABF_         VARCHAR2, /*Тип основании сведении об отце*/
                     BR_ACT_MZDATE_         DATE, /*Дата заявления от матери, если (BR_ACT_TGRABF = V)*/
                     BR_ACT_DBF_            VARCHAR2, /*Документ подтверждающий факт орождении ребенка, тип*/
                     BR_ACT_CH_             NUMBER, /*Ссылка на ребенка*/
                     BR_ACT_F_              NUMBER, /*Ссылка на отца*/
                     BR_ACT_M_              NUMBER, /*Ссылка на мать*/
                     BR_ACT_MEDORGA_        VARCHAR2, /*Наименование мед. орг. выд. документ (A-Док. уст. формы)*/
                     BR_ACT_DATEDOCA_       DATE, /*Дата документа  (A-Док. уст. формы)*/
                     BR_ACT_NDOCA_          VARCHAR2, /*Номер документа  (A-Док. уст. формы)*/
                     BR_ACT_FIOB_           VARCHAR2, /*ФИО лица присутствовавшего во время родов (Б-Заявление)*/
                     BR_ACT_DATEDOCB_       DATE, /*Дата документа  (Б-Заявление)*/
                     BR_ACT_NAMECOURT_      VARCHAR2, /*Наимнование суда*/
                     BR_ACT_DESCCOURT_      VARCHAR2, /*Решение суда №*/
                     BR_ACT_DCOURT_         DATE, /*Дата решения суда*/
                     BR_ACT_FADFIRST_NAME_  VARCHAR2, /*Имя (Заявитель о рождении)*/
                     BR_ACT_FADLAST_NAME_   VARCHAR2, /*Фамилия (Заявитель о рождении)*/
                     BR_ACT_FADMIDDLE_NAME_ VARCHAR2, /*Отчество (Заявитель о рождении)*/
                     BR_ACT_FADLOCATION_    VARCHAR2, /*Место жительства (Заявитель о рождении)*/
                     BR_ACT_FADORG_NAME_    VARCHAR2, /*Наименование организации (Заявитель о рождении)*/
                     BR_ACT_FADREG_ADR_     VARCHAR2, /*Адрес регистрации (Заявитель о рождении)*/
                     BR_ACT_MERCER_ID_      NUMBER, /*Ссылка на свидетельство о заключении брака */
                     BR_ACT_NUM_            VARCHAR2, /*Номер (печать ЗАГСа)*/
                     BR_ACT_SERIA_          VARCHAR2, /*Серия (печать ЗАГСа)*/
                     BR_ACT_PATCER_         NUMBER, /*Ссылка на установл. отц*/
                     CRID                   NUMBER) return varchar2;

  procedure RetXmls(ID number, error out varchar2, clob_ out clob);
  procedure CompareXmls(ID    in number,
                        clob_ in clob,
                        error out varchar2,
                        res   out number);
end BURN_UTIL;
/
grant execute on XXI.BURN_UTIL to ODB;


prompt
prompt Creating package DEATCH
prompt =======================
prompt
create or replace package xxi.Deatch is

  function AddDeath(DC_NUMBER_         VARCHAR2, /*Выдано свидетельство номер*/
                    DC_SERIA_          VARCHAR2, /*Выдано свидетельство серия*/
                    DC_FADREG_ADR_     VARCHAR2, /*Адрес регистрации (Заявитель о смерти)*/
                    DC_FADORG_NAME_    VARCHAR2, /*Наименование организации (Заявитель о смерти)*/
                    DC_FADLOCATION_    VARCHAR2, /*Место жительства (Заявитель о смерти)*/
                    DC_FADMIDDLE_NAME_ VARCHAR2, /*Отчество (Заявитель о смерти)*/
                    DC_FADLAST_NAME_   VARCHAR2, /*Фамилия (Заявитель о смерти)*/
                    DC_FADFIRST_NAME_  VARCHAR2, /*Имя (Заявитель о смерти)*/
                    DC_ZTP_            VARCHAR2, /*Тип заявителя.F-физ.-J-юр.*/
                    DC_LLOC_           VARCHAR2, /*Последнее место жительства*/
                    DC_NRNAME_         VARCHAR2, /*V-Док. подтвержд. факт смерти-Необоснованно репрес.-Наименование органа выд. документ*/
                    DC_RCNAME_         VARCHAR2, /*B-Док. подтвержд. факт смерти-Решение суда-Наименование суда*/
                    DC_FMON_           VARCHAR2, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти-Наименование мед. организации*/
                    DC_FTYPE_          VARCHAR2, /*Док. подтвержд. факт смерти-Тип*/
                    DC_FD_             DATE, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Дата*/
                    DC_FNUM_           VARCHAR2, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Номер*/
                    DC_CD_             VARCHAR2, /*Причина смерти*/
                    DC_DPL_            VARCHAR2, /*Место смерти*/
                    DC_DD_             DATE, /*Дата смерти*/
                    DC_CUS_            NUMBER, /*Ссылка на cus*/
                    DC_ID_             OUT NUMBER /*ID*/) return varchar2;

  function EditDeath(DC_NUMBER_         VARCHAR2, /*Выдано свидетельство номер*/
                     DC_SERIA_          VARCHAR2, /*Выдано свидетельство серия*/
                     DC_FADREG_ADR_     VARCHAR2, /*Адрес регистрации (Заявитель о смерти)*/
                     DC_FADORG_NAME_    VARCHAR2, /*Наименование организации (Заявитель о смерти)*/
                     DC_FADLOCATION_    VARCHAR2, /*Место жительства (Заявитель о смерти)*/
                     DC_FADMIDDLE_NAME_ VARCHAR2, /*Отчество (Заявитель о смерти)*/
                     DC_FADLAST_NAME_   VARCHAR2, /*Фамилия (Заявитель о смерти)*/
                     DC_FADFIRST_NAME_  VARCHAR2, /*Имя (Заявитель о смерти)*/
                     DC_ZTP_            VARCHAR2, /*Тип заявителя.F-физ.-J-юр.*/
                     DC_LLOC_           VARCHAR2, /*Последнее место жительства*/
                     DC_NRNAME_         VARCHAR2, /*V-Док. подтвержд. факт смерти-Необоснованно репрес.-Наименование органа выд. документ*/
                     DC_RCNAME_         VARCHAR2, /*B-Док. подтвержд. факт смерти-Решение суда-Наименование суда*/
                     DC_FMON_           VARCHAR2, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти-Наименование мед. организации*/
                     DC_FTYPE_          VARCHAR2, /*Док. подтвержд. факт смерти-Тип*/
                     DC_FD_             DATE, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Дата*/
                     DC_FNUM_           VARCHAR2, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Номер*/
                     DC_CD_             VARCHAR2, /*Причина смерти*/
                     DC_DPL_            VARCHAR2, /*Место смерти*/
                     DC_DD_             DATE, /*Дата смерти*/
                     DC_CUS_            NUMBER, /*Ссылка на cus*/
                     DC_ID_             NUMBER /*ID*/) return varchar2;

  procedure RetXmls(ID number, error out varchar2, clob_ out clob);
  procedure CompareXmls(ID    in number,
                        clob_ in clob,
                        error out varchar2,
                        res   out number);
end Deatch;
/
grant execute on XXI.DEATCH to ODB;


prompt
prompt Creating package DIVORCE
prompt ========================
prompt
create or replace package xxi.Divorce is
  procedure AddDivorce(error           out varchar2,
                       DIVC_SERIA_     VARCHAR2, /*Выдано свидетельство серия*/
                       DIVC_NUM_       VARCHAR2, /*Выдано свидетельство номер*/
                       DIVC_MC_MERCER_ NUMBER, /*Ссылка на акт о заключении брака*/
                       DIVC_ZOSPRISON_ NUMBER, /*V-(Расторжение,приговор суда) приговор суда=лишение свободы (количество лет)*/
                       DIVC_ZOSFIO2_   VARCHAR2, /*V-(Расторжение,приговор суда) приговор суда=об осуждении ФИО*/
                       DIVC_ZOSCD2_    DATE, /*V-(Расторжение,приговор суда) приговор суда=дата (от)*/
                       DIVC_ZOSCN2_    VARCHAR2, /*V-(Расторжение,приговор суда) приговор суда=наименование суда */
                       DIVC_ZOSFIO_    VARCHAR2, /*V-(Расторжение,заявление одного из супругов) о признании ФИО*/
                       DIVC_ZOSCD_     DATE, /*V-(Расторжение,заявление одного из супругов) дата решения суда*/
                       DIVC_ZOSCN_     VARCHAR2, /*V-(Расторжение,заявление одного из супругов) наименование суда*/
                       DIVC_CAD_       DATE, /*B-(Расторжение,решение суда) дата*/
                       DIVC_CAN_       VARCHAR2, /*B-(Расторжение,решение суда) наименование суда*/
                       DIVC_TCHNUM_    VARCHAR2, /*A-(Расторжение, совместное заявление) номер*/
                       DIVC_TCHD_      DATE, /*A-(Расторжение, совместное заявление) дата*/
                       DIVC_TYPE_      VARCHAR2, /*Типы основании расторжения брака*/
                       DIVC_DT_        DATE, /*Дата прекращения брака*/
                       DIVC_SHE_LNAFT_ VARCHAR2, /*Фамилия ее после рб*/
                       DIVC_SHE_LNBEF_ VARCHAR2, /*Фамилия ее до рб*/
                       DIVC_HE_LNAFT_  VARCHAR2, /*Фамилия его после рб*/
                       DIVC_HE_LNBEF_  VARCHAR2, /*Фамилия его до рб*/
                       DIVC_SHE_       NUMBER, /*Она ссылка на CUS*/
                       DIVC_HE_        NUMBER, /*Он ссылка на CUS*/
                       DIVC_ZPLACE_    VARCHAR2, /*Заявитель Место жительства*/
                       DIVC_ZMNAME_    VARCHAR2, /*Заявитель Отчество*/
                       DIVC_ZАNAME_   VARCHAR2, /*Заявитель Имя*/
                       DIVC_ZLNAME_    VARCHAR2, /*Заявитель Фамилия*/
                       ID              out number);
  procedure EditDivorce(error           out varchar2,
                        DIVC_SERIA_     VARCHAR2, /*Выдано свидетельство серия*/
                        DIVC_NUM_       VARCHAR2, /*Выдано свидетельство номер*/
                        DIVC_MC_MERCER_ NUMBER, /*Ссылка на акт о заключении брака*/
                        DIVC_ZOSPRISON_ NUMBER, /*V-(Расторжение,приговор суда) приговор суда=лишение свободы (количество лет)*/
                        DIVC_ZOSFIO2_   VARCHAR2, /*V-(Расторжение,приговор суда) приговор суда=об осуждении ФИО*/
                        DIVC_ZOSCD2_    DATE, /*V-(Расторжение,приговор суда) приговор суда=дата (от)*/
                        DIVC_ZOSCN2_    VARCHAR2, /*V-(Расторжение,приговор суда) приговор суда=наименование суда */
                        DIVC_ZOSFIO_    VARCHAR2, /*V-(Расторжение,заявление одного из супругов) о признании ФИО*/
                        DIVC_ZOSCD_     DATE, /*V-(Расторжение,заявление одного из супругов) дата решения суда*/
                        DIVC_ZOSCN_     VARCHAR2, /*V-(Расторжение,заявление одного из супругов) наименование суда*/
                        DIVC_CAD_       DATE, /*B-(Расторжение,решение суда) дата*/
                        DIVC_CAN_       VARCHAR2, /*B-(Расторжение,решение суда) наименование суда*/
                        DIVC_TCHNUM_    VARCHAR2, /*A-(Расторжение, совместное заявление) номер*/
                        DIVC_TCHD_      DATE, /*A-(Расторжение, совместное заявление) дата*/
                        DIVC_TYPE_      VARCHAR2, /*Типы основании расторжения брака*/
                        DIVC_DT_        DATE, /*Дата прекращения брака*/
                        DIVC_SHE_LNAFT_ VARCHAR2, /*Фамилия ее после рб*/
                        DIVC_SHE_LNBEF_ VARCHAR2, /*Фамилия ее до рб*/
                        DIVC_HE_LNAFT_  VARCHAR2, /*Фамилия его после рб*/
                        DIVC_HE_LNBEF_  VARCHAR2, /*Фамилия его до рб*/
                        DIVC_SHE_       NUMBER, /*Она ссылка на CUS*/
                        DIVC_HE_        NUMBER, /*Он ссылка на CUS*/
                        DIVC_ZPLACE_    VARCHAR2, /*Заявитель Место жительства*/
                        DIVC_ZMNAME_    VARCHAR2, /*Заявитель Отчество*/
                        DIVC_ZАNAME_   VARCHAR2, /*Заявитель Имя*/
                        DIVC_ZLNAME_    VARCHAR2, /*Заявитель Фамилия*/
                        ID              IN number);
  procedure CompareXmls(ID    in number,
                        clob_ in clob,
                        error out varchar2,
                        res   out number);
  procedure RetXmls(ID number, error out varchar2, clob_ out clob);
end Divorce;
/
grant execute on XXI.DIVORCE to ODB;


prompt
prompt Creating package FRREPORT
prompt =========================
prompt
CREATE OR REPLACE PACKAGE XXI.frreport IS
  -- проверить наличие роли FR_DESIGNER
  function CheckDsgnRole return boolean;

  -- клонирование отчета
  function CloneReport(source_RT_ID     ap_report_cat.report_type_id%type,
                       source_R_ID      ap_report_cat.report_id%type,
                       dest_R_ID        ap_report_cat.report_id%type,
                       dest_Report_UFS  ap_report_cat.report_ufs%type,
                       dest_REPORT_NAME ap_report_cat.report_name%type)
    return varchar;

  -- права доступа на отчет по пользователю
  procedure AP_Grant2Report(l_report_type_id ap_report_cat.report_type_id%type,
                            l_report_id      ap_report_cat.report_id%type,
                            l_user_logname   VARCHAR2);

  -- подбор свободного ID отчета альт.печати типа report_type_id
  function AP_GetNewReportID(l_report_type_id ap_report_cat.report_type_id%type)
    return number;

  function AddQueryToStorage(REPORTID_p     number,
                             REPORTTYPEID_p number,
                             QUERYNAME_p    varchar2,
                             REPORTFILE_p   varchar2,
                             QUERYBODY_p    varchar2) return varchar2;

  function GetQueryFromStorage(QUERYID_p number, FR_QUERY_p varchar2)
    return CLOB;

  function PutQueryToStorage(QUERYID_p   number,
                             FR_QUERY_p  varchar2,
                             QUERYBODY_p varchar2) return varchar2;

END; -- Package spec
/
grant execute on XXI.FRREPORT to ODB;
grant execute, debug on XXI.FRREPORT to PUBLIC;


prompt
prompt Creating package LOCKS
prompt ======================
prompt
CREATE OR REPLACE PACKAGE XXI.LOCKS IS

  -- AUTHOR  : SAID
  -- CREATED : 14.01.2021 14:40:43
  -- PURPOSE : БЛОКИРОВКИ ЗАПИСЕЙ

  --
  --ДОБАВИТЬ ЗАПИСЬ О БЛОКИРОВКЕ СТРОКИ ТАБЛИЦЫ
  --
  PROCEDURE LOCK_ROW_ADD(TBL_NAME IN VARCHAR2,
                         USR      IN VARCHAR2,
                         KEY      IN NUMBER,
                         ERROR    OUT VARCHAR2);
  --
  --ВОЗВРАТ ДАННЫХ ПОЛЬЗОВАТЕЛЯ ЗАЛОЧИВШЕГО СТРОКУ
  --
  PROCEDURE LOCK_ROW_VIEW(TBL_NAME IN VARCHAR2,
                          KEY      IN NUMBER,
                          USR_INFO OUT VARCHAR2,
                          ERROR    OUT VARCHAR2);
  --
  --УДАЛЕНИЕ ДАННЫХ О СТРОКЕ
  --
  PROCEDURE LOCK_ROW_DEL(TBL_NAME IN VARCHAR2,
                         USR_ID   IN VARCHAR2,
                         KEY      IN NUMBER,
                         ERROR    OUT VARCHAR2);
END LOCKS;
/
grant execute on XXI.LOCKS to ODB;


prompt
prompt Creating package MERCER
prompt =======================
prompt
create or replace package xxi.Mercer is

  procedure AddMercer(error              out varchar2,
                      id                 out number,
                      MERCER_OTHER_      VARCHAR2, /*Иные сведения и служебные пометки*/
                      MERCER_DIEHE_      NUMBER, /*Свид. о смерти его пред. брака (Ссылка)*/
                      MERCER_DIESHE_     NUMBER, /*Свид. о смерти её пред. брака (Ссылка)*/
                      MERCER_SERIA_      VARCHAR2, /*Серия свид.*/
                      MERCER_NUM_        VARCHAR2, /*Номер свид.*/
                      MERCER_DSPMT_HE_   VARCHAR2, /*Тип докум. подтв. прекр. пред. брака, Он*/
                      MERCER_DIVHE_      NUMBER, /*Свидетельство расторжения брака Он (ссылка)*/
                      MERCER_DIVSHE_     NUMBER, /*Свидетельство расторжения брака Она (ссылка)*/
                      MERCER_SHEAGE_     NUMBER, /*Она лет*/
                      MERCER_HEAGE_      NUMBER, /*Он лет*/
                      MERCER_SHE_LNBAFT_ VARCHAR2, /*Фамиля ее после зб*/
                      MERCER_SHE_LNBEF_  VARCHAR2, /*Фамиля ее до зб*/
                      MERCER_HE_LNAFT_   VARCHAR2, /*Фамиля его после зб*/
                      MERCER_HE_LNBEF_   VARCHAR2, /*Фамиля его до зб*/
                      MERCER_SHE_        NUMBER, /*Она ссылка на CUS*/
                      MERCER_HE_         NUMBER, /*Он ссылка на CUS*/
                      MERCER_DSPMT_SHE_  VARCHAR2, /*Тип докум. подтв. прекр. пред. брака, Она*/
                      MC_DATE_ date);

  procedure EditMercer(error              out varchar2,
                       id                 in number,
                       MERCER_OTHER_      VARCHAR2, /*Иные сведения и служебные пометки*/
                       MERCER_DIEHE_      NUMBER, /*Свид. о смерти его пред. брака (Ссылка)*/
                       MERCER_DIESHE_     NUMBER, /*Свид. о смерти её пред. брака (Ссылка)*/
                       MERCER_SERIA_      VARCHAR2, /*Серия свид.*/
                       MERCER_NUM_        VARCHAR2, /*Номер свид.*/
                       MERCER_DSPMT_HE_   VARCHAR2, /*Тип докум. подтв. прекр. пред. брака, Он*/
                       MERCER_DIVHE_      NUMBER, /*Свидетельство расторжения брака Он (ссылка)*/
                       MERCER_DIVSHE_     NUMBER, /*Свидетельство расторжения брака Она (ссылка)*/
                       MERCER_SHEAGE_     NUMBER, /*Она лет*/
                       MERCER_HEAGE_      NUMBER, /*Он лет*/
                       MERCER_SHE_LNBAFT_ VARCHAR2, /*Фамиля ее после зб*/
                       MERCER_SHE_LNBEF_  VARCHAR2, /*Фамиля ее до зб*/
                       MERCER_HE_LNAFT_   VARCHAR2, /*Фамиля его после зб*/
                       MERCER_HE_LNBEF_   VARCHAR2, /*Фамиля его до зб*/
                       MERCER_SHE_        NUMBER, /*Она ссылка на CUS*/
                       MERCER_HE_         NUMBER, /*Он ссылка на CUS*/
                       MERCER_DSPMT_SHE_  VARCHAR2, /*Тип докум. подтв. прекр. пред. брака, Она*/
                       MC_DATE_ date);
  procedure RetXmls(ID number, error out varchar2, clob_ out clob);
  procedure CompareXmls(ID    in number,
                        clob_ in clob,
                        error out varchar2,
                        res   out number);
end Mercer;
/
grant execute on XXI.MERCER to ODB;


prompt
prompt Creating package MJUSERS
prompt ========================
prompt
create or replace package xxi.MJUsers is

  -- Author  : SAID
  -- Created : 18.09.2020 21:32:05
  -- Purpose : Администрирование пользователей

  /*---*
  * характеристики пароля по умолчанию (если не заданы в USR)
  *---*/
  C_DEF_PWD_LENGTH         NUMBER := 30;
  C_DEF_PWD_CHR_QUANTITY   NUMBER := 20;
  C_DEF_PWD_NUM_QUANTITY   NUMBER := 10;
  C_DEF_PWD_SPEC_QUANTITY  NUMBER := 0;
  C_DEF_PWD_HISTORY_LENGTH NUMBER := 0;

  --
  --Создание пользователя в таблице usr
  --
  function createusr(CUSRLOGNAME_ VARCHAR2,
                     CUSRNAME_    VARCHAR2,
                     PASS         VARCHAR2) return varchar2;
  --
  --Редактировать
  --
  function UpdateUser(IUSRBRANCH_           number,
                      CUSRPOSITION_         varchar2,
                      DUSRHIRE_             date,
                      DUSRFIRE_             date,
                      ZAGS_                 number,
                      IUSRPWD_LENGTH_       number,
                      IUSRCHR_QUANTITY_     number,
                      IUSRNUM_QUANTITY_     number,
                      IUSRSPEC_QUANTITY_    number,
                      MUST_CHANGE_PASSWORD_ CHAR,
                      USRID                 number,
                      CUSRNAME_             varchar2,
                      NOTARY_               number,
                      ACC_LEVEL             varchar2,
                      FIO_SH_               varchar2,
                      FIO_ABH_SH_           varchar2,
                      FIO_ABH_              varchar2) return varchar2;
  --
  --Проверка наличия разрешения на действие, на форме
  --
  function OdbAccess(usr number, act number) return number;
  --
  --Убрать разрешение у пользователя
  --
  function OdbActUsrDelete(usr number, act number) return varchar2;
  --
  --Убрать разрешение пользователю
  --
  function OdbActUsrAdd(usr number, act number) return varchar2;
  --
  --Проверка наличия разрешения на действие, при нажатии
  --
  function OdbAccess(act number) return number;
  --
  --Проверка наличия разрешения на пункт меню при переборе, по пользователю
  --
  function OdbMnuAccess(usr number, act number) return number;
  --
  --Проверка наличия разрешения на пункт меню при переборе
  --
  function OdbMnuAccess(act number) return number;
  --
  --Проверка наличия разрешения на пункт меню при переборе, по группам
  --
  function OdbMnuAccessGrp(grp number, act number) return number;
  --
  --Добавить действие
  --
  procedure AddOdbActionItem(error       out varchar2,
                             ACT_PARENT_ number,
                             ACT_NAME_   varchar2);
  --
  --Редактировать
  --
  procedure EditOdbActionItem(error     out varchar2,
                              ID        number,
                              ACT_NAME_ varchar2,
                              new_ac    number);
  --
  --убрать доступ к меню
  --
  function OdbMnuUsrDelete(usr number, act number) return varchar2;
  --
  --добавить доступ к меню
  --
  function OdbMnuUsrAdd(usr number, act number) return varchar2;
  --
  --добавить элемент меню
  --
  procedure AddOdbMenuItem(error       out varchar2,
                           MNU_PARENT_ number,
                           MNU_NAME_   varchar2);
  --
  --изменить элемент меню
  --
  procedure EditOdbMenuItem(error     out varchar2,
                            ID        number,
                            MNU_NAME_ varchar2,
                            new_mnu   number);
  --
  --удаление пользователя
  --
  procedure DeleteUser(error out varchar2, USR_ID number);
  --
  -- Изменить пароль
  --
  FUNCTION Set_Up_Password(ErrMsg   out VARCHAR2,
                           Log_Name IN VARCHAR2,
                           Pwd      IN VARCHAR2,
                           Pwd2     IN VARCHAR2) RETURN NUMBER;
  --
  --Учреждение, Загс, Нотариус, Оба...
  --
  function ACC_LEV(logn varchar2) return varchar2;

  function OdbMnuGrpAdd(grp number, act number) return varchar2;
  function OdbMnuGrpDelete(grp number, act number) return varchar2;
  FUNCTION MNU_ACCESS(MNU_ID NUMBER, USR_LOGIN VARCHAR2 DEFAULT USER)
    RETURN NUMBER;
  FUNCTION ACT_ACCESS(ACT_ID NUMBER, USR_LOGIN VARCHAR2 DEFAULT USER)
    RETURN NUMBER;
  FUNCTION ODB_ACT_GRP_ADD(GRP NUMBER, ACT NUMBER) RETURN VARCHAR2;
  FUNCTION ODB_ACT_GRP_DEL(GRP NUMBER, ACT NUMBER) RETURN VARCHAR2;
  FUNCTION ODB_ACT_ACCESS_GRP(GRP NUMBER, ACT NUMBER) RETURN NUMBER;
end MJUsers;
/
grant execute on XXI.MJUSERS to ODB;


prompt
prompt Creating package NOTATY
prompt =======================
prompt
create or replace package xxi.NOTATY is

  -- Author  : SAIDP
  -- Created : 25.02.2021 18:17:01
  -- Purpose : НОТАРИАТ
  

end NOTATY;
/
grant execute on XXI.NOTATY to ODB;


prompt
prompt Creating type T_TAB_VARCHAR2_2000
prompt =================================
prompt
CREATE OR REPLACE TYPE XXI."T_TAB_VARCHAR2_2000"                                          AS TABLE OF VARCHAR2 (2000)
/

prompt
prompt Creating package N3D_FX_PKG
prompt ===========================
prompt
create or replace package xxi.N3D_FX_Pkg is
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  -- Общие функции
  -- Java FX
  cVersion constant VarChar2(30) := '(6.0.4)(04.02.2019)(Nick-3D)';
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  -- Select single
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  procedure Select_Single(pTableName in VarChar2, -- Имя таблицы
                          pIdName    in T_Tab_VarChar2_2000, -- Список Id
                          pIdType    in Sys.ODCINumberList, -- Список Типов Id
                          pIdValueS  in T_Tab_VarChar2_2000, -- Список строковых значений Id
                          pIdValueN  in Sys.ODCINumberList); -- Список сисловых значений Id
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  -- UnSelect single
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  procedure UnSelect_Single(pTableName in VarChar2, -- Имя таблицы
                            pIdName    in T_Tab_VarChar2_2000, -- Список Id
                            pIdType    in Sys.ODCINumberList, -- Список Типов Id
                            pIdValueS  in T_Tab_VarChar2_2000, -- Список строковых значений Id
                            pIdValueN  in Sys.ODCINumberList); -- Список сисловых значений Id
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  -- Select all
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  procedure Select_All(pTableName in VarChar2, -- Имя таблицы
                       pIdName    in T_Tab_VarChar2_2000, -- Список Id
                       pSQL       in VarChar2, -- Запрос
                       pWhere     in VarChar2); -- Условие
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  -- UnSelect all
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  procedure UnSelect_All(pTableName in VarChar2, -- Имя таблицы
                         pIdName    in T_Tab_VarChar2_2000, -- Список Id
                         pSQL       in VarChar2, -- Запрос
                         pWhere     in VarChar2); -- Условие
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  -- Проверка SQL
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  procedure Check_SQL(pSQL     in VarChar2, -- SQL
                      pType    in Integer, -- Тип (0 - SQL, 1 - PL/SQL)
                      pName    in VarChar2, -- Имя запроса
                      pResult  out Integer, -- Результат
                      pMessage out VarChar2); -- Сообщение об ошибке
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
end;
/
grant execute on XXI.N3D_FX_PKG to ODB;


prompt
prompt Creating package N3D_PKG
prompt ========================
prompt
create or replace package xxi.N3D_Pkg is

  -- Author  : XXI
  -- Created : 23.12.2020 9:54:41
  -- Purpose : 

  cResultOk    constant Integer := 0;
  cResultFake  constant Integer := -1;
  cResultError constant Integer := -2;

end N3D_Pkg;
/
grant execute on XXI.N3D_PKG to ODB;


prompt
prompt Creating package PATERN
prompt =======================
prompt
create or replace package xxi.PATERN is

  -- Author  : SAID
  -- Created : 14.11.2020 20:57:05
  -- Purpose : 
  procedure AddPatern(error          out varchar2,
                      PС_NUMBER_    VARCHAR2, /*Выдано свид. номер*/
                      PС_SERIA_     VARCHAR2, /*Выдано свид. серия*/
                      PС_CRDATE_    DATE, /*V-решение суда - дата*/
                      PС_CRNAME_    VARCHAR2, /*V-решение суда - наименование*/
                      PС_FZ_        DATE, /*B-заявление отца ребенка -дата*/
                      PС_TRZ_       DATE, /*A-Совмест. заявл. род-дата*/
                      PС_TYPE_      VARCHAR2, /*Тип основания для уст. отцовства*/
                      PС_M_         NUMBER, /*Ссылка на мать*/
                      PС_F_         NUMBER, /*Ссылка на отца*/
                      PС_CH_        NUMBER, /*Ссылка на ребенка*/
                      PС_AFT_MNAME_ VARCHAR2, /*Отчество после уст. отц*/
                      PС_AFT_FNAME_ VARCHAR2, /*Имя после уст. отц*/
                      PС_AFT_LNAME_ VARCHAR2, /*Фамилия после уст. отц*/
                      PC_ACT_ID_     NUMBER, /*Ссылка на свидетельство о рождении*/
                      PC_ZPLACE_     VARCHAR2, /*Место жительства заявителя*/
                      PC_ZLNAME_     VARCHAR2, /*Фамилия заявителя*/
                      PC_ZFNAME_     VARCHAR2, /*Имя заявителя*/
                      PC_ZMNAME_     VARCHAR2, /*Отчество заявителя*/
                      ID             out number);

  procedure EditPatern(error          out varchar2,
                       ID             in number,
                       PС_NUMBER_    VARCHAR2, /*Выдано свид. номер*/
                       PС_SERIA_     VARCHAR2, /*Выдано свид. серия*/
                       PС_CRDATE_    DATE, /*V-решение суда - дата*/
                       PС_CRNAME_    VARCHAR2, /*V-решение суда - наименование*/
                       PС_FZ_        DATE, /*B-заявление отца ребенка -дата*/
                       PС_TRZ_       DATE, /*A-Совмест. заявл. род-дата*/
                       PС_TYPE_      VARCHAR2, /*Тип основания для уст. отцовства*/
                       PС_M_         NUMBER, /*Ссылка на мать*/
                       PС_F_         NUMBER, /*Ссылка на отца*/
                       PС_CH_        NUMBER, /*Ссылка на ребенка*/
                       PС_AFT_MNAME_ VARCHAR2, /*Отчество после уст. отц*/
                       PС_AFT_FNAME_ VARCHAR2, /*Имя после уст. отц*/
                       PС_AFT_LNAME_ VARCHAR2, /*Фамилия после уст. отц*/
                       PC_ACT_ID_     NUMBER, /*Ссылка на свидетельство о рождении*/
                       PC_ZPLACE_     VARCHAR2, /*Место жительства заявителя*/
                       PC_ZLNAME_     VARCHAR2, /*Фамилия заявителя*/
                       PC_ZFNAME_     VARCHAR2, /*Имя заявителя*/
                       PC_ZMNAME_     VARCHAR2 /*Отчество заявителя*/);

  procedure CompareXmls(ID    in number,
                        clob_ in clob,
                        error out varchar2,
                        res   out number);
  procedure RetXmls(ID number, error out varchar2, clob_ out clob);
end PATERN;
/
grant execute on XXI.PATERN to ODB;


prompt
prompt Creating package SHEDULER
prompt =========================
prompt
create or replace package xxi.SHEDULER is

end SHEDULER;
/
grant execute on XXI.SHEDULER to ODB;


prompt
prompt Creating package TS
prompt ===================
prompt
CREATE OR REPLACE PACKAGE XXI.TS IS

  FUNCTION TO_2000(cStr IN VARCHAR2) RETURN VARCHAR2;
END TS;
/
grant execute on XXI.TS to ODB;


prompt
prompt Creating package UDPNAT
prompt =======================
prompt
create or replace package xxi.UDPNAT is

  -- Author  : SAID
  -- Created : 18.11.2020 21:38:40
  -- Purpose :

  --
  -- Редактировать документ
  -- 
  procedure EditUpdNat(error        out varchar2,
                       id_          in number,
                       NEW_NAT_     NUMBER, /*Новая национальность*/
                       OLD_NAT_     NUMBER, /*Старая национальность*/
                       BRN_ACT_ID_  NUMBER, /*Ссылка  на свидетельство о рождении*/
                       CUSID_       NUMBER, /*Ссылка на клиента*/
                       SVID_SERIA_  varchar2,
                       SVID_NUMBER_ varchar2);
  --
  -- Добавить документ
  --
  procedure AddUpdNat(error        out varchar2,
                      id_          out number,
                      NEW_NAT_     NUMBER, /*Новая национальность*/
                      OLD_NAT_     NUMBER, /*Старая национальность*/
                      BRN_ACT_ID_  NUMBER, /*Ссылка  на свидетельство о рождении*/
                      CUSID_       NUMBER, /*Ссылка на клиента*/
                      SVID_SERIA_  varchar2,
                      SVID_NUMBER_ varchar2);
  --
  -- Сравнить
  --
  procedure CompareXmls(ID    in number,
                        clob_ in clob,
                        error out varchar2,
                        res   out number);
  --
  -- Возврат xml
  --
  procedure RetXmls(ID number, error out varchar2, clob_ out clob);
end UDPNAT;
/
grant execute on XXI.UDPNAT to ODB;


prompt
prompt Creating package UPDABHNAME
prompt ===========================
prompt
create or replace package xxi.UpdAbhName is

  procedure AddUpdAbhName(error          out varchar2,
                          id_            out number,
                          OLD_LASTNAME_  VARCHAR2, /*Фамилия до перемены*/
                          OLD_FIRSTNAME_ VARCHAR2, /*Имя до перемены*/
                          OLD_MIDDLNAME_ VARCHAR2, /*Отчество до перемены*/
                          NEW_LASTNAME_  VARCHAR2, /*Фамилия после перемены*/
                          NEW_FIRSTNAME_ VARCHAR2, /*Имя после перемены*/
                          NEW_MIDDLNAME_ VARCHAR2, /*Отчество после перемены*/
                          BRN_ACT_ID_    NUMBER, /*Ссылка на акт о рождении*/
                          CUSID_         NUMBER, /*Ссылка на клиента*/
                          SVID_NUMBER_   VARCHAR2, /*Выдано свидетельство номер*/
                          SVID_SERIA_    VARCHAR2 /*Выдано свидетельство серия*/);

  procedure EditUpdAbhName(error          out varchar2,
                           id_            in number,
                           OLD_LASTNAME_  VARCHAR2, /*Фамилия до перемены*/
                           OLD_FIRSTNAME_ VARCHAR2, /*Имя до перемены*/
                           OLD_MIDDLNAME_ VARCHAR2, /*Отчество до перемены*/
                           NEW_LASTNAME_  VARCHAR2, /*Фамилия после перемены*/
                           NEW_FIRSTNAME_ VARCHAR2, /*Имя после перемены*/
                           NEW_MIDDLNAME_ VARCHAR2, /*Отчество после перемены*/
                           BRN_ACT_ID_    NUMBER, /*Ссылка на акт о рождении*/
                           CUSID_         NUMBER, /*Ссылка на клиента*/
                           SVID_NUMBER_   VARCHAR2, /*Выдано свидетельство номер*/
                           SVID_SERIA_    VARCHAR2 /*Выдано свидетельство серия*/);
  procedure RetXmls(ID number, error out varchar2, clob_ out clob);
  procedure CompareXmls(ID    in number,
                        clob_ in clob,
                        error out varchar2,
                        res   out number);
end UpdAbhName;
/
grant execute on XXI.UPDABHNAME to ODB;


prompt
prompt Creating package UPDATE_PRJ
prompt ===========================
prompt
create or replace package xxi.UPDATE_PRJ is

  -- Author  : SAID
  -- Created : 16.12.2020 22:44:45
  -- Purpose : Обновление файлов проекта
  procedure AddFileOrFolder(error       out varchar2,
                            folder_name varchar2,
                            file_name   varchar2,
                            isfolder    varchar2,
                            binary      blob,
                            parents     number,
                            bytes_       number);
  procedure UpdateFileOrFolder(error       out varchar2,
                               folder_name varchar2,
                               file_name   varchar2,
                               isfolder    varchar2,
                               binary      blob,
                               prj_id_     number,
                               bytes_       number);
end UPDATE_PRJ;
/
grant execute on XXI.UPDATE_PRJ to ODB;


prompt
prompt Creating package UPDNAME
prompt ========================
prompt
create or replace package xxi.UpdName is

  procedure AddUpdName(error             out varchar2,
                       id_               out number,
                       OLD_LASTNAME_     VARCHAR2, /*Фамилия до перемены*/
                       OLD_FIRSTNAME_    VARCHAR2, /*Имя до перемены*/
                       OLD_MIDDLNAME_    VARCHAR2, /*Отчество до перемены*/
                       NEW_LASTNAME_     VARCHAR2, /*Фамилия после перемены*/
                       NEW_FIRSTNAME_    VARCHAR2, /*Имя после перемены*/
                       NEW_MIDDLNAME_    VARCHAR2, /*Отчество после перемены*/
                       BRN_ACT_ID_       NUMBER, /*Ссылка на акт о рождении*/
                       CUSID_            NUMBER, /*Ссылка на клиента*/
                       SVID_NUMBER_      VARCHAR2, /*Выдано свидетельство номер*/
                       SVID_SERIA_       VARCHAR2, /*Выдано свидетельство серия*/
                       OLD_LASTNAME_AB_  VARCHAR2, /*Фамилия до перемены АБХ*/
                       OLD_FIRSTNAME_AB_ VARCHAR2, /*Имя до перемены АБХ*/
                       OLD_MIDDLNAME_AB_ VARCHAR2, /*Отчество до перемены АБХ*/
                       NEW_LASTNAME_AB_  VARCHAR2, /*Фамилия после перемены АБХ*/
                       NEW_FIRSTNAME_AB_ VARCHAR2, /*Имя после перемены АБХ*/
                       NEW_MIDDLNAME_AB_ VARCHAR2 /*Отчество после перемены АБХ*/);
  procedure EditUpdName(error             out varchar2,
                        id_               in number,
                        OLD_LASTNAME_     VARCHAR2, /*Фамилия до перемены*/
                        OLD_FIRSTNAME_    VARCHAR2, /*Имя до перемены*/
                        OLD_MIDDLNAME_    VARCHAR2, /*Отчество до перемены*/
                        NEW_LASTNAME_     VARCHAR2, /*Фамилия после перемены*/
                        NEW_FIRSTNAME_    VARCHAR2, /*Имя после перемены*/
                        NEW_MIDDLNAME_    VARCHAR2, /*Отчество после перемены*/
                        BRN_ACT_ID_       NUMBER, /*Ссылка на акт о рождении*/
                        CUSID_            NUMBER, /*Ссылка на клиента*/
                        SVID_NUMBER_      VARCHAR2, /*Выдано свидетельство номер*/
                        SVID_SERIA_       VARCHAR2, /*Выдано свидетельство серия*/
                        OLD_LASTNAME_AB_  VARCHAR2, /*Фамилия до перемены АБХ*/
                        OLD_FIRSTNAME_AB_ VARCHAR2, /*Имя до перемены АБХ*/
                        OLD_MIDDLNAME_AB_ VARCHAR2, /*Отчество до перемены АБХ*/
                        NEW_LASTNAME_AB_  VARCHAR2, /*Фамилия после перемены АБХ*/
                        NEW_FIRSTNAME_AB_ VARCHAR2, /*Имя после перемены АБХ*/
                        NEW_MIDDLNAME_AB_ VARCHAR2 /*Отчество после перемены АБХ*/);
  procedure RetXmls(ID number, error out varchar2, update_name out clob);
  procedure CompareXmls(ID          in number,
                        update_name in clob,
                        error       out varchar2,
                        res         out number);
end UpdName;
/
grant execute on XXI.UPDNAME to ODB;


prompt
prompt Creating type TBLCLASS
prompt ======================
prompt
CREATE OR REPLACE TYPE XXI."TBLCLASS"                                          as object (
  strnum number,
  str varchar2(4000),
  type_ varchar2(50)
)
/

prompt
prompt Creating type TBLCLASS_TABLE
prompt ============================
prompt
CREATE OR REPLACE TYPE XXI."TBLCLASS_TABLE"                                          as table of tblclass
/

prompt
prompt Creating type T_PROPERTIES_ITEM
prompt ===============================
prompt
CREATE OR REPLACE TYPE XXI."T_PROPERTIES_ITEM"                                          AS OBJECT ( cKey VARCHAR2 (30), cValue VARCHAR2 (2000) )
/

prompt
prompt Creating type T_TAB_PROPERTIES_ITEM
prompt ===================================
prompt
CREATE OR REPLACE TYPE XXI."T_TAB_PROPERTIES_ITEM"                                          AS TABLE OF T_PROPERTIES_ITEM
/

prompt
prompt Creating type T_PROPERTIES
prompt ==========================
prompt
CREATE OR REPLACE TYPE XXI."T_PROPERTIES"                                          AS OBJECT
(
--
-- psh
--
     tabItem  T_Tab_Properties_Item,

     CONSTRUCTOR FUNCTION T_Properties
         RETURN SELF AS RESULT,

     CONSTRUCTOR FUNCTION T_Properties ( tabItem  T_Tab_Properties_Item )
         RETURN SELF AS RESULT,

     MEMBER FUNCTION find ( Self  IN  T_Properties,
                            cKey  IN  VARCHAR2 )
         RETURN INTEGER,

     MEMBER FUNCTION get ( Self  IN  T_Properties,
                           cKey  IN  VARCHAR2 )
         RETURN VARCHAR2,

     MEMBER FUNCTION getProperty ( Self  IN  T_Properties,
                                   cKey  IN  VARCHAR2 )
         RETURN VARCHAR2,

     MEMBER FUNCTION getProperty ( Self      IN  T_Properties,
                                   cKey      IN  VARCHAR2,
                                   cDefault  IN  VARCHAR2 )
         RETURN VARCHAR2,

     MEMBER PROCEDURE put ( Self    IN OUT NOCOPY T_Properties,
                            cKey    IN            VARCHAR2,
                            cValue  IN            VARCHAR2 ),

     MEMBER PROCEDURE remove ( Self  IN OUT NOCOPY T_Properties,
                               cKey  IN            VARCHAR2 ),

     MEMBER PROCEDURE clear ( Self  IN OUT NOCOPY T_Properties )
)
/

prompt
prompt Creating type T_TAB_PROPERTIES
prompt ==============================
prompt
CREATE OR REPLACE TYPE XXI."T_TAB_PROPERTIES"                                          AS TABLE OF T_PROPERTIES
/

prompt
prompt Creating function DATE2STR_ABH_F
prompt ================================
prompt
CREATE OR REPLACE FUNCTION XXI.DATE2STR_ABH_F(d IN DATE) RETURN VARCHAR2 IS
  -- *****************************************************************************
  -- перевод даты в строку
  -- 14.07.2006 = Четырнадцатое июля две тысячи шестого года

  -- Автор: Полников Игорь
  -- Дата создания: 14.07.2006
  -- *****************************************************************************
  result VARCHAR2(200) := NULL;

  -- исходные числа
  num   VARCHAR2(2);
  month VARCHAR2(2);
  year  VARCHAR2(4);

  -- результирующие строки
  num_s   VARCHAR2(100);
  month_s VARCHAR2(100);
  year_s  VARCHAR2(100);

  TYPE string_tabletype is table of varchar2(60) index by binary_integer;
  unt  string_tabletype;
  unt2 string_tabletype;
BEGIN
  IF d IS NULL THEN
    RETURN NULL;
  END IF;

  -- константы для числа
  unt(1) := 'первое';
  unt(2) := 'второе';
  unt(3) := 'третье';
  unt(4) := 'четвертое';
  unt(5) := 'пятое';
  unt(6) := 'шестое';
  unt(7) := 'седьмое';
  unt(8) := 'восьмое';
  unt(9) := 'девятое';
  unt(10) := 'десятое';
  unt(11) := 'одиннадцатое';
  unt(12) := 'двенадцатое';
  unt(13) := 'тринадцатое';
  unt(14) := 'четырнадцатое';
  unt(15) := 'пятнадцатое';
  unt(16) := 'шестнадцатое';
  unt(17) := 'семнадцатое';
  unt(18) := 'восемнадцатое';
  unt(19) := 'девятнадцатое';
  unt(20) := 'двадцатое';
  unt(21) := 'тридцатое';
  unt(22) := 'двадцать';
  unt(23) := 'тридцать';

  -- константы для года
  -- единицы
  unt2(1) := 'первого';
  unt2(2) := 'второго';
  unt2(3) := 'третьего';
  unt2(4) := 'четвертого';
  unt2(5) := 'пятого';
  unt2(6) := 'шестого';
  unt2(7) := 'седьмого';
  unt2(8) := 'восьмого';
  unt2(9) := 'девятого';
  unt2(10) := 'десятого';
  unt2(11) := 'одиннадцатого';
  unt2(12) := 'двенадцатого';
  unt2(13) := 'тринадцатого';
  unt2(14) := 'четырнадцатого';
  unt2(15) := 'пятнадцатого';
  unt2(16) := 'шестнадцатого';
  unt2(17) := 'семнадцатого';
  unt2(18) := 'восемнадцатого';
  unt2(19) := 'девятнадцатого';

  -- десятки
  unt2(20) := '?аж?ат?и';
  unt2(21) := '?аж?иж?абат?и';
  unt2(22) := '?ын?аж?ат?и';
  unt2(23) := '?ын?аж?ат?и';
  unt2(24) := 'хын?аж?ат?и';
  unt2(25) := 'хын?аж?иж?абат?и';
  unt2(26) := '?шьын?аж?ат?и';
  unt2(27) := '?шьын?аж?иж?абат?и';

  unt2(28) := '?аж?а';
  unt2(29) := '?аж?иж?аба';
  unt2(30) := '?ын?аж?а';
  unt2(31) := '?ын?аж?иж?аба';
  unt2(32) := 'хын?аж?а';
  unt2(33) := 'хын?аж?иж?аба';
  unt2(34) := '?шьын?аж?а';
  unt2(35) := '?шьын?аж?иж?аба';

  -- сотни
  unt2(36) := 'аш?ыкт?и';
  unt2(37) := '?ыш?т?и';
  unt2(38) := 'хыш?т?и';
  unt2(39) := '?шьыш?т?и';
  unt2(40) := 'ах?ыш?т?и';
  unt2(41) := 'фыш?т?и';
  unt2(42) := 'быжьш?ыт?и';
  unt2(43) := 'ааш?т?и';
  unt2(44) := 'ж?ш?ыт?и';

  -- тысячи
  unt2(45) := 'аз?т?и';
  unt2(46) := '?ыныз?ьшы??сат?и';
  unt2(47) := 'ахныз?ьт?и';
  unt2(48) := '?шьныз?ьт?и';
  unt2(49) := 'х?ныз?ьт?и';
  unt2(50) := 'фныз?ьт?и';
  unt2(51) := 'быжьныз?ьт?и';
  unt2(52) := 'ааш?ныз?ьт?и';
  unt2(53) := 'ж?ш?ныз?ьт?и';

  num   := TO_CHAR(d, 'DD');
  month := TO_CHAR(d, 'MM');
  year  := TO_CHAR(d, 'YYYY');

  -- обработка числа
  IF TO_NUMBER(num) BETWEEN 1 AND 20 THEN
    num_s := unt(TO_NUMBER(num));
  ELSIF TO_NUMBER(num) = 30 THEN
    num_s := unt(21);
  ELSE
    IF SUBSTR(num, 1, 1) = '2' THEN
      num_s := unt(22);
    ELSE
      num_s := unt(23);
    END IF;
    num_s := num_s || ' ' || unt(TO_NUMBER(SUBSTR(num, 2)));
  END IF;

  -- обработка месяца
  SELECT DECODE(month,
                '01',
                'января',
                '02',
                'февраля',
                '03',
                'марта',
                '04',
                'апреля',
                '05',
                'мая',
                '06',
                'июня',
                '07',
                'июля',
                '08',
                'августа',
                '09',
                'сентября',
                '10',
                'октября',
                '11',
                'ноября',
                '12',
                'декабря')
    INTO month_s
    FROM dual;

  -- обработка года
  IF SUBSTR(year, 2) = '000' THEN
    -- тысячный год
    year_s := unt2(44 + TO_NUMBER(SUBSTR(year, 1, 1)));
  ELSIF SUBSTR(year, 3) = '00' THEN
    -- сотый год
    year_s := num2str(TRUNC(TO_NUMBER(year), -3), NULL) || ' ' ||
              unt2(35 + TO_NUMBER(SUBSTR(year, 2, 1)));
  ELSIF SUBSTR(year, 4) = '0' THEN
    -- десятый год
    year_s := num2str(TRUNC(TO_NUMBER(year), -2), NULL) || ' ';
    IF SUBSTR(year, 3, 1) = '1' THEN
      year_s := year_s || unt2(10);
    ELSE
      year_s := year_s || unt2(18 + TO_NUMBER(SUBSTR(year, 3, 1)));
    END IF;
  ELSE
    -- нет нулей на конце
    year_s := num2str(TRUNC(TO_NUMBER(year), -2), NULL) || ' ';
    IF TO_NUMBER(SUBSTR(year, 3, 2)) BETWEEN 1 AND 19 THEN
      year_s := year_s || unt2(TO_NUMBER(SUBSTR(year, 3, 2)));
    ELSE
      year_s := year_s || unt2(26 + TO_NUMBER(SUBSTR(year, 3, 1))) || ' ' ||
                unt2(TO_NUMBER(SUBSTR(year, 4, 1)));
    END IF;
  END IF;

  result := LOWER(num_s || ' ' || month_s || ' ' || year_s || ' года');
  result := UPPER(SUBSTR(result, 1, 1)) || SUBSTR(result, 2);
  result := REPLACE(result, '   ', ' '); -- лишние пробелы
  result := REPLACE(result, '  ', ' '); -- лишние пробелы

  RETURN result;
END;
/
grant execute on XXI.DATE2STR_ABH_F to ODB;


prompt
prompt Creating function GET_COUNTRY_NAME
prompt ==================================
prompt
CREATE OR REPLACE FUNCTION XXI.GET_COUNTRY_NAME(PID IN NUMBER) RETURN VARCHAR2 IS
  RET VARCHAR2(250);
BEGIN
  IF PID IS NOT NULL THEN
    SELECT NAME INTO RET FROM COUNTRIES WHERE CODE = PID;
  END IF;
  RETURN RET;
EXCEPTION
  WHEN OTHERS THEN
    RETURN '<Неизвестная страна>';
END;
/
grant execute on XXI.GET_COUNTRY_NAME to ODB;


prompt
prompt Creating function IIF
prompt =====================
prompt
CREATE OR REPLACE FUNCTION XXI.IIF(b_Check     IN BOOLEAN,
                               cTrueValue  IN VARCHAR2,
                               cFalseValue IN VARCHAR2,
                               cNullValue  IN VARCHAR2 DEFAULT 'VALUE_NOT_PASSED')
  RETURN VARCHAR2 IS
  -- $Id: iif.sql,v 2.1 2011/04/16 05:36:59 psh500 Exp $
BEGIN

  IF b_Check IS NULL THEN

    IF cNullValue = 'VALUE_NOT_PASSED' THEN
      RAISE_APPLICATION_ERROR(-20001, 'IIF: Wrong arguments');
    END IF;

    RETURN cNullValue;
  ELSIF b_Check THEN
    RETURN cTrueValue;
  ELSE
    RETURN cFalseValue;
  END IF;

END IIF;
/
grant execute on XXI.IIF to ODB;


prompt
prompt Creating function RET_CALLABLE_STATMENT
prompt =======================================
prompt
create or replace function xxi.ret_callable_statment(PACKAGE_NAME_ varchar2,
                                                 OBJECT_NAME_  varchar2)
  return tblclass_table as
  v_ret tblclass_table;
  cursor sel is
    select (SELECT '//' || COMMENTS || chr(13) || chr(10)
              FROM DBA_COL_COMMENTS com
             where upper(com.column_name) like
                   upper('%' || TRIM(TRAILING '_' FROM ARGUMENT_NAME) || '%')
               and com.owner = 'XXI'
               and rownum = 1) || case
             when IN_OUT = 'OUT' then
              'callStmt.registerOutParameter(' || ROWNUM || ',Types.' || case
                when DATA_TYPE like '%CHAR%' THEN
                 'VARCHAR);'
                when DATA_TYPE = 'NUMBER' THEN
                 'INTEGER);'
                when DATA_TYPE = 'DATE' THEN
                 'DATE);'
              END
             when IN_OUT = 'IN' then
              CASE
                WHEN DATA_TYPE like '%CHAR%' THEN
                 'callStmt.setString(' || ROWNUM || ',' ||
                 TRIM(TRAILING '_' FROM ARGUMENT_NAME) || '.getText());'
                WHEN DATA_TYPE = 'NUMBER' THEN
                 'if (!' || TRIM(TRAILING '_' FROM ARGUMENT_NAME) ||
                 '.getText().equals("")) { callStmt.setInt(' || ROWNUM ||
                 ',Integer.valueOf(' || TRIM(TRAILING '_' FROM ARGUMENT_NAME) ||
                 '.getText())); } else { callStmt.setNull(' || ROWNUM ||
                 ',java.sql.Types.INTEGER);}'
                when DATA_TYPE = 'DATE' THEN
                 'callStmt.setDate(' || ROWNUM || ',(' ||
                 TRIM(TRAILING '_' FROM ARGUMENT_NAME) ||
                 '.getValue() != null) ? java.sql.Date.valueOf(' ||
                 TRIM(TRAILING '_' FROM ARGUMENT_NAME) ||
                 '.getValue()) : null);'
              END
           end data
      from ALL_ARGUMENTS
     where owner = 'XXI'
       and PACKAGE_NAME = upper(PACKAGE_NAME_)
       and OBJECT_NAME = upper(OBJECT_NAME_)
     order by POSITION asc;
  cnt number := 0;
begin
  v_ret := tblclass_table();

  for r in sel loop
    cnt := cnt + 1;
    v_ret.extend;
    v_ret(v_ret.count) := tblclass(cnt, r.data, null);
  
  end loop;
  return v_ret;
end ret_callable_statment;
/
grant execute on XXI.RET_CALLABLE_STATMENT to ODB;


prompt
prompt Creating function RET_CLASS
prompt ===========================
prompt
create or replace function xxi.ret_class(tblname varchar2)
  return tblclass_table as
  v_ret tblclass_table;

  /*Fields*/
  if_number varchar2(500) := ' private IntegerProperty ';
  if_date   varchar2(500) := ' private SimpleObjectProperty<LocalDateTime> ';
  if_dt     varchar2(500) := ' private SimpleObjectProperty<LocalDate> ';
  if_string varchar2(500) := ' private StringProperty ';

  /*Constructor*/
  if_number_cons varchar2(500) := ' = new SimpleIntegerProperty();';
  if_string_cons varchar2(500) := ' = new SimpleStringProperty();';
  if_date_cons   varchar2(500) := ' = new SimpleObjectProperty<>();';

  /*Setters*/
  sett varchar2(500) := 'public void set';

  /*Getters*/
  if_string_get_start varchar2(500) := 'public String get';
  if_number_get_start varchar2(500) := 'public Integer get';
  if_date_get_start   varchar2(500) := 'public LocalDate get';
  if_dt_get_start     varchar2(500) := 'public LocalDateTime get';

  /*Property*/
  if_string_prop_start varchar2(500) := 'public StringProperty ';
  if_number_prop_start varchar2(500) := 'public IntegerProperty ';
  if_date_prop_start   varchar2(500) := 'public SimpleObjectProperty<LocalDate> ';
  if_dt_prop_start     varchar2(500) := 'public SimpleObjectProperty<LocalDateTime> ';
  cnt                  number := 0;
  type array_t is varray(8) of varchar2(500);
  array array_t := array_t( --'package mjmodel;',
                           'import java.time.LocalDateTime;',
                           'import java.time.LocalDate;',
                           'import javafx.beans.property.IntegerProperty;',
                           'import javafx.beans.property.SimpleIntegerProperty;',
                           'import javafx.beans.property.SimpleObjectProperty;',
                           'import javafx.beans.property.SimpleStringProperty;',
                           'import javafx.beans.property.StringProperty;',
                           'public class ' || tblname || ' {');
begin
  v_ret := tblclass_table();

  /*Import*/
  for i in 1 .. array.count loop
    cnt := cnt + 1;
    v_ret.extend;
    v_ret(v_ret.count) := tblclass(cnt, array(i), 'IMPERTS&CLASS');
  end loop;

  /*Fielsds*/
  for r in (SELECT COLUMN_NAME, DATA_TYPE, h.owner, h.DATA_DEFAULT
              FROM all_tab_cols h
             WHERE table_name = tblname) loop
    cnt := cnt + 1;
    if r.DATA_TYPE like '%CHAR%' then
      v_ret.extend;
      v_ret(v_ret.count) := tblclass(cnt,
                                     '/**' ||
                                     AUP_UTIL.Get_Col_Comment(Tab_Name   => tblname,
                                                              Tab_Owner  => r.owner,
                                                              Field_Name => r.column_name) || '*/' ||
                                     if_string || r.COLUMN_NAME || ';',
                                     'FL');
    elsif r.DATA_TYPE = 'NUMBER' then
      v_ret.extend;
      v_ret(v_ret.count) := tblclass(cnt,
                                     '/**' ||
                                     AUP_UTIL.Get_Col_Comment(Tab_Name   => tblname,
                                                              Tab_Owner  => r.owner,
                                                              Field_Name => r.column_name) || '*/' ||
                                     if_number || r.COLUMN_NAME || ';',
                                     'FL');
    elsif r.DATA_TYPE = 'DATE' then
      if r.data_default is not null or r.column_name like upper('TM$%') then
        v_ret.extend;
        v_ret(v_ret.count) := tblclass(cnt,
                                       '/**' ||
                                       AUP_UTIL.Get_Col_Comment(Tab_Name   => tblname,
                                                                Tab_Owner  => r.owner,
                                                                Field_Name => r.column_name) || '*/' ||
                                       if_date || r.COLUMN_NAME || ';',
                                       'FL');
      else
        v_ret.extend;
        v_ret(v_ret.count) := tblclass(cnt,
                                       '/**' ||
                                       AUP_UTIL.Get_Col_Comment(Tab_Name   => tblname,
                                                                Tab_Owner  => r.owner,
                                                                Field_Name => r.column_name) || '*/' ||
                                       if_dt || r.COLUMN_NAME || ';',
                                       'FL');
      end if;
    
    end if;
  end loop;

  cnt := cnt + 1;
  v_ret.extend;
  v_ret(v_ret.count) := tblclass(cnt,
                                 'public ' || tblname || '() {',
                                 'CONS');
  /*constructor_*/
  for r in (SELECT COLUMN_NAME, DATA_TYPE, h.owner, h.DATA_DEFAULT
              FROM all_tab_cols h
             WHERE table_name = tblname) loop
    cnt := cnt + 1;
    if r.DATA_TYPE like '%CHAR%' then
      v_ret.extend;
      v_ret(v_ret.count) := tblclass(cnt,
                                     'this.' || r.COLUMN_NAME ||
                                     if_string_cons,
                                     'CONS');
    elsif r.DATA_TYPE = 'NUMBER' then
      v_ret.extend;
      v_ret(v_ret.count) := tblclass(cnt,
                                     'this.' || r.COLUMN_NAME ||
                                     if_number_cons,
                                     'CONS');
    elsif r.DATA_TYPE = 'DATE' then
      if r.data_default is not null or r.column_name like upper('TM$%') then
        v_ret.extend;
        v_ret(v_ret.count) := tblclass(cnt,
                                       'this.' || r.COLUMN_NAME ||
                                       if_date_cons,
                                       'CONS');
      else
        v_ret.extend;
        v_ret(v_ret.count) := tblclass(cnt,
                                       'this.' || r.COLUMN_NAME ||
                                       if_date_cons,
                                       'CONS');
      end if;
    
    end if;
  end loop;
  cnt := cnt + 1;
  v_ret.extend;
  v_ret(v_ret.count) := tblclass(cnt, '}', 'CONS');

  /*Sets*/
  for r in (SELECT COLUMN_NAME, DATA_TYPE, h.owner, h.DATA_DEFAULT
              FROM all_tab_cols h
             WHERE table_name = tblname) loop
    cnt := cnt + 1;
    if r.DATA_TYPE like '%CHAR%' then
      v_ret.extend;
      v_ret(v_ret.count) := tblclass(cnt,
                                     sett || r.COLUMN_NAME || '(String ' ||
                                     r.COLUMN_NAME || '){this.' ||
                                     r.COLUMN_NAME || '.set(' ||
                                     r.COLUMN_NAME || ');}',
                                     'SETS');
    elsif r.DATA_TYPE = 'NUMBER' then
      v_ret.extend;
      v_ret(v_ret.count) := tblclass(cnt,
                                     sett || r.COLUMN_NAME || '(Integer ' ||
                                     r.COLUMN_NAME || '){this.' ||
                                     r.COLUMN_NAME || '.set(' ||
                                     r.COLUMN_NAME || ');}',
                                     'SETS');
    elsif r.DATA_TYPE = 'DATE' then
      if r.data_default is not null or r.column_name like upper('TM$%') then
        v_ret.extend;
        v_ret(v_ret.count) := tblclass(cnt,
                                       sett || r.COLUMN_NAME ||
                                       '(LocalDateTime ' || r.COLUMN_NAME ||
                                       '){this.' || r.COLUMN_NAME || '.set(' ||
                                       r.COLUMN_NAME || ');}',
                                       'SETS');
      else
        v_ret.extend;
        v_ret(v_ret.count) := tblclass(cnt,
                                       sett || r.COLUMN_NAME ||
                                       '(LocalDate ' || r.COLUMN_NAME ||
                                       '){this.' || r.COLUMN_NAME || '.set(' ||
                                       r.COLUMN_NAME || ');}',
                                       'SETS');
      end if;
    
    end if;
  end loop;

  /*Gets*/
  for r in (SELECT COLUMN_NAME, DATA_TYPE, h.owner, h.DATA_DEFAULT
              FROM all_tab_cols h
             WHERE table_name = tblname) loop
    cnt := cnt + 1;
    if r.DATA_TYPE like '%CHAR%' then
      v_ret.extend;
      v_ret(v_ret.count) := tblclass(cnt,
                                     if_string_get_start || r.COLUMN_NAME ||
                                     '(){return ' || r.COLUMN_NAME ||
                                     '.get();}',
                                     'GETS');
    elsif r.DATA_TYPE = 'NUMBER' then
      v_ret.extend;
      v_ret(v_ret.count) := tblclass(cnt,
                                     if_number_get_start || r.COLUMN_NAME ||
                                     '(){return ' || r.COLUMN_NAME ||
                                     '.get();}',
                                     'GETS');
    elsif r.DATA_TYPE = 'DATE' then
      if r.data_default is not null or r.column_name like upper('TM$%') then
        v_ret.extend;
        v_ret(v_ret.count) := tblclass(cnt,
                                       if_dt_get_start || r.COLUMN_NAME ||
                                       '(){return ' || r.COLUMN_NAME ||
                                       '.get();}',
                                       'GETS');
      else
        v_ret.extend;
        v_ret(v_ret.count) := tblclass(cnt,
                                       if_date_get_start || r.COLUMN_NAME ||
                                       '(){return ' || r.COLUMN_NAME ||
                                       '.get();}',
                                       'GETS');
      end if;
    
    end if;
  end loop;

  /*property_*/
  for r in (SELECT COLUMN_NAME, DATA_TYPE, h.owner, h.DATA_DEFAULT
              FROM all_tab_cols h
             WHERE table_name = tblname) loop
    cnt := cnt + 1;
    if r.DATA_TYPE like '%CHAR%' then
      v_ret.extend;
      v_ret(v_ret.count) := tblclass(cnt,
                                     if_string_prop_start || r.COLUMN_NAME ||
                                     'Property() { return ' || r.COLUMN_NAME || ';}',
                                     'PROP');
    elsif r.DATA_TYPE = 'NUMBER' then
      v_ret.extend;
      v_ret(v_ret.count) := tblclass(cnt,
                                     if_number_prop_start || r.COLUMN_NAME ||
                                     'Property() { return ' || r.COLUMN_NAME || ';}',
                                     'PROP');
    elsif r.DATA_TYPE = 'DATE' then
      if r.data_default is not null or r.column_name like upper('TM$%') then
        v_ret.extend;
        v_ret(v_ret.count) := tblclass(cnt,
                                       if_dt_prop_start || r.COLUMN_NAME ||
                                       'Property() { return ' ||
                                       r.COLUMN_NAME || ';}',
                                       'PROP');
      else
        v_ret.extend;
        v_ret(v_ret.count) := tblclass(cnt,
                                       if_date_prop_start || r.COLUMN_NAME ||
                                       'Property() { return ' ||
                                       r.COLUMN_NAME || ';}',
                                       'PROP');
      end if;
    
    end if;
  end loop;

  /*End*/
  cnt := cnt + 1;
  v_ret.extend;
  v_ret(v_ret.count) := tblclass(cnt, '}', 'END');
  return v_ret;
end ret_class;
/
grant execute on XXI.RET_CLASS to ODB;


prompt
prompt Creating function RET_DOCX
prompt ==========================
prompt
create or replace function xxi.ret_docx(tblname varchar2)
  return tblclass_table as
  v_ret tblclass_table;
  cursor sel(tbl_name varchar2) is
SELECT 'variables.addTextVariable(new TextVariable("#{' || h.COLUMN_NAME ||
       '}", list.get' || COLUMN_NAME || '()));' data
  FROM all_tab_cols h
 WHERE upper(table_name) = tbl_name;
cnt number := 0;
begin
  v_ret := tblclass_table();

  for r in sel(tblname) loop
    cnt := cnt + 1;
    v_ret.extend;
    v_ret(v_ret.count) := tblclass(cnt, r.data, null);
  end loop;
  return v_ret;
end ret_docx;
/
grant execute on XXI.RET_DOCX to ODB;


prompt
prompt Creating function RET_FIELDS
prompt ============================
prompt
create or replace function xxi.ret_fields(tblname varchar2)
  return tblclass_table as
  v_ret tblclass_table;
  cursor sel(tbl_name varchar2) is
    SELECT 'list.set' || COLUMN_NAME || '(' || case DATA_TYPE
             when 'VARCHAR2' then
              'rs.getString("' || COLUMN_NAME || '"));'
             when 'CHAR' then
              'rs.getString("' || COLUMN_NAME || '"));'
             when 'NUMBER' then
              'rs.getInt("' || COLUMN_NAME || '"));'
             when 'DATE' then
              case
                when h.DATA_DEFAULT is null and
                     upper(column_name) not like upper('TM$%') then
                 '(rs.getDate("' || COLUMN_NAME || '")!=null)?' ||
                 'LocalDate.parse(' ||
                 'new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("' ||
                 COLUMN_NAME || '"))' || ', formatter)' || ':null);'
                when upper(column_name) like upper('TM$%') then
                 '(rs.getDate("' || COLUMN_NAME || '")!=null)?' ||
                 'LocalDateTime.parse(' ||
                 'new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("' ||
                 COLUMN_NAME || '"))' || ', formatterwt)' || ':null);'
                else
                 '(rs.getDate("' || COLUMN_NAME || '")!=null)?' ||
                 'LocalDateTime.parse(' ||
                 'new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("' ||
                 COLUMN_NAME || '"))' || ', formatterwt)' || ':null);'
              end
           end data
      FROM all_tab_cols h
     WHERE upper(table_name) = tbl_name;
  cnt number := 0;
begin
  v_ret := tblclass_table();

  for r in sel(tblname) loop
    cnt := cnt + 1;
    v_ret.extend;
    v_ret(v_ret.count) := tblclass(cnt, r.data, null);
  
  end loop;
  return v_ret;
end ret_fields;
/
grant execute on XXI.RET_FIELDS to ODB;


prompt
prompt Creating function SYS_FNK_FR
prompt ============================
prompt
create or replace function xxi.SYS_FNK_FR(t in varchar2) return varchar2 is
begin
  return utl_raw.cast_to_varchar2(utl_encode.base64_decode(utl_raw.cast_to_raw(t)));
end SYS_FNK_FR;
/
grant execute on XXI.SYS_FNK_FR to ODB;


prompt
prompt Creating function SYS_FNK_TO
prompt ============================
prompt
create or replace function xxi.SYS_FNK_TO(t in varchar2) return varchar2 is
begin
  return utl_raw.cast_to_varchar2(utl_encode.base64_encode(utl_raw.cast_to_raw(t)));
end SYS_FNK_TO;
/
grant execute on XXI.SYS_FNK_TO to ODB;


prompt
prompt Creating function UNCOMPRESSIPV6
prompt ================================
prompt
CREATE OR REPLACE FUNCTION XXI.UncompressIpV6(Ip IN VARCHAR2) RETURN VARCHAR2
  DETERMINISTIC IS
  IpFull VARCHAR2(40);
  len    INTEGER := 7;
BEGIN
  IF REGEXP_LIKE(Ip, '::') THEN
    IpFull := REGEXP_REPLACE(REGEXP_REPLACE(Ip, '^::', '0::'), '::$', '::0');
    IF REGEXP_LIKE(Ip, ':\d+\.\d+\.\d+\.\d+$') THEN
      -- Mixed notation, e.g.: 0::FFFF:129.144.52.38
      len := 6;
    END IF;
    WHILE REGEXP_COUNT(IpFull, ':') <= len LOOP
      IpFull := REGEXP_REPLACE(IpFull, '::', ':0::');
    END LOOP;
    RETURN REGEXP_REPLACE(IpFull, '::', ':');
  ELSE
    RETURN Ip;
  END IF;
END UncompressIpV6;
/
grant execute on XXI.UNCOMPRESSIPV6 to ODB;


prompt
prompt Creating package body AC
prompt ========================
prompt
create or replace package body xxi.AC is

  e_Bind_Not_Exists EXCEPTION;
  PRAGMA EXCEPTION_INIT(e_Bind_Not_Exists, -1006);
  --
  e_Variable_Not_In_Select EXCEPTION;
  PRAGMA EXCEPTION_INIT(e_Variable_Not_In_Select, -1007);
  --
  c_Const_DT_SQL   CONSTANT CHAR := '0';
  c_Const_DT_PLSQL CONSTANT CHAR := '1';
  c_Const_DT_JOB   CONSTANT CHAR := '2';
  c_Const_DT_TABLE CONSTANT CHAR := '3';

  --
  -- Проверка кода
  --
  FUNCTION CheckCode(cError OUT VARCHAR2,
                     cType  IN VARCHAR2,
                     cCode  IN VARCHAR2) RETURN BOOLEAN IS
    iCursor  INTEGER;
    desc_t   DBMS_SQL.Desc_Tab3;
    col_cnt  INTEGER;
    bOk      BOOLEAN := FALSE;
  BEGIN
  
    IF cType IN
       (c_Const_DT_SQL, c_Const_DT_PLSQL, c_Const_DT_JOB, c_Const_DT_TABLE) THEN
      BEGIN
        iCursor := DBMS_SQL_ADD.Open_Parse(cCode);
      
        IF cType = c_Const_DT_TABLE THEN
          DBMS_SQL.Bind_Variable(iCursor, 'o1', T_Tab_Properties());
        END IF;
      
        DBMS_SQL.Describe_Columns3(iCursor, col_cnt, desc_t);
      
        bOk    := TRUE;
        cError := 'Во время грамматического разбора "%NAME%" ошибок не обнаружено.';
      EXCEPTION
        WHEN OTHERS THEN
          IF SQLCode = -900 AND
             cType IN (c_Const_DT_PLSQL, c_Const_DT_JOB, c_Const_DT_TABLE) THEN
            cError := 'Проверка PL/SQL блока "%NAME%" успешно завершена';
            bOk    := TRUE;
          ELSE
            cError := TS.To_2000('Ошибка при проверке "%NAME%"' || CHR(10) ||
                                 '===' || CHR(10) || SQLErrM(SQLCode) ||
                                 CHR(10) || '==>' ||
                                 SUBSTR(cCode, DBMS_SQL.Last_Error_Position));
          END IF;
      END;
    
      IF DBMS_SQL.Is_Open(iCursor) THEN
        DBMS_SQL.Close_Cursor(iCursor);
      END IF;
    ELSE
      cError := 'Проверке подлежат настройки типов "Запрос", "DBMS_JOB", "PL/SQL блок" и "SQL коллекция".';
    END IF;
  
    RETURN bOk;
  END CheckCode;
  --
--
--

end AC;
/
grant execute on XXI.AC to ODB;


prompt
prompt Creating package body ADOPT
prompt ===========================
prompt
create or replace package body xxi.ADOPT is

  sep varchar2(2) := chr(13) || chr(10);

  procedure AddAdopt(error               out varchar2,
                     id_                 out number,
                     OLD_LASTNAME_       VARCHAR2, /*Фамилия до*/
                     OLD_FIRSTNAME_      VARCHAR2, /*Имя до*/
                     OLD_MIDDLNAME_      VARCHAR2, /*Отчество до*/
                     NEW_LASTNAME_       VARCHAR2, /*Фамилия после*/
                     NEW_FIRSTNAME_      VARCHAR2, /*Имя после*/
                     NEW_MIDDLNAME_      VARCHAR2, /*Отчество после*/
                     CUSID_CH_           NUMBER, /*ребенок, ссылка на клиента*/
                     CUSID_M_            NUMBER, /*мать, ссылка на клиента*/
                     CUSID_F_            NUMBER, /*отец, ссылка на клиента*/
                     BRNACT_             NUMBER, /*рождение ребенка*/
                     SVID_SERIA_         VARCHAR2, /*серия*/
                     SVID_NOMER_         VARCHAR2, /*номер*/
                     CUSID_M_AD_         NUMBER, /*мать усынов.*/
                     CUSID_F_AD_         NUMBER, /*отец усынов.*/
                     ADOPT_PARENTS_      VARCHAR2, /*записываются ли усыновители родителями ребенка*/
                     ZAP_ISPOLKOM_RESH_  VARCHAR2, /*Решение исполкома (осн. записи об усыновл.)*/
                     ZAP_SOVET_DEP_TRUD_ VARCHAR2, /*Совета депутатов трудящихся (края, республики) (осн. записи об усыновл.)*/
                     ZAP_DATE_           DATE, /*дата (осн. записи об усыновл.)*/
                     ZAP_NUMBER_         VARCHAR2, /*номер (осн. записи об усыновл.)*/
                     NEW_BRTH_           DATE, /*дата рождения после*/
                     OLD_BRTH_           DATE, /*дата рождения до*/
                     BRN_CITY_           VARCHAR2,
                     BRN_AREA_           VARCHAR2,
                     BRN_OBL_RESP_       VARCHAR2) is
    ZagsId number;
  begin
    select nvl(zags_id, -1)
      into ZagsId
      from usr
     where usr.cusrlogname = user;
  
    if ZagsId = -1 then
      error := error || sep || 'У пользователя "' || user ||
               '" не проставлен номер ЗАГСа!';
      return;
    end if;
  
    insert into ADOPTOIN
      (ZAGS_ID, /*ИД загса*/
       OLD_LASTNAME, /*Фамилия до*/
       OLD_FIRSTNAME, /*Имя до*/
       OLD_MIDDLNAME, /*Отчество до*/
       NEW_LASTNAME, /*Фамилия после*/
       NEW_FIRSTNAME, /*Имя после*/
       NEW_MIDDLNAME, /*Отчество после*/
       CUSID_CH, /*ребенок, ссылка на клиента*/
       CUSID_M, /*мать, ссылка на клиента*/
       CUSID_F, /*отец, ссылка на клиента*/
       BRNACT, /*рождение ребенка*/
       SVID_SERIA, /*серия*/
       SVID_NOMER, /*номер*/
       CUSID_M_AD, /*мать усынов.*/
       CUSID_F_AD, /*отец усынов.*/
       ADOPT_PARENTS, /*записываются ли усыновители родителями ребенка*/
       ZAP_ISPOLKOM_RESH, /*Решение исполкома (осн. записи об усыновл.)*/
       ZAP_SOVET_DEP_TRUD, /*Совета депутатов трудящихся (края, республики) (осн. записи об усыновл.)*/
       ZAP_DATE, /*дата (осн. записи об усыновл.)*/
       ZAP_NUMBER, /*номер (осн. записи об усыновл.)*/
       NEW_BRTH, /*дата рождения после*/
       OLD_BRTH, /*дата рождения до*/
       BRN_CITY,
       BRN_AREA,
       BRN_OBL_RESP)
    values
      (ZagsId, /*ИД загса*/
       OLD_LASTNAME_, /*Фамилия до*/
       OLD_FIRSTNAME_, /*Имя до*/
       OLD_MIDDLNAME_, /*Отчество до*/
       NEW_LASTNAME_, /*Фамилия после*/
       NEW_FIRSTNAME_, /*Имя после*/
       NEW_MIDDLNAME_, /*Отчество после*/
       CUSID_CH_, /*ребенок, ссылка на клиента*/
       CUSID_M_, /*мать, ссылка на клиента*/
       CUSID_F_, /*отец, ссылка на клиента*/
       BRNACT_, /*рождение ребенка*/
       SVID_SERIA_, /*серия*/
       SVID_NOMER_, /*номер*/
       CUSID_M_AD_, /*мать усынов.*/
       CUSID_F_AD_, /*отец усынов.*/
       ADOPT_PARENTS_, /*записываются ли усыновители родителями ребенка*/
       ZAP_ISPOLKOM_RESH_, /*Решение исполкома (осн. записи об усыновл.)*/
       ZAP_SOVET_DEP_TRUD_, /*Совета депутатов трудящихся (края, республики) (осн. записи об усыновл.)*/
       ZAP_DATE_, /*дата (осн. записи об усыновл.)*/
       ZAP_NUMBER_, /*номер (осн. записи об усыновл.)*/
       NEW_BRTH_, /*дата рождения после*/
       OLD_BRTH_, /*дата рождения до*/
       BRN_CITY_,
       BRN_AREA_,
       BRN_OBL_RESP_)
    returning ID into id_;
  
    update cus
       set cus.ccuslast_name   = NEW_LASTNAME_,
           cus.ccusfirst_name  = NEW_FIRSTNAME_,
           cus.ccusmiddle_name = NEW_MIDDLNAME_,
           cus.dcusbirthday    = NEW_BRTH_
     where cus.icusnum = CUSID_CH_;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  procedure EditAdopt(error               out varchar2,
                      id_                 number,
                      OLD_LASTNAME_       VARCHAR2, /*Фамилия до*/
                      OLD_FIRSTNAME_      VARCHAR2, /*Имя до*/
                      OLD_MIDDLNAME_      VARCHAR2, /*Отчество до*/
                      NEW_LASTNAME_       VARCHAR2, /*Фамилия после*/
                      NEW_FIRSTNAME_      VARCHAR2, /*Имя после*/
                      NEW_MIDDLNAME_      VARCHAR2, /*Отчество после*/
                      CUSID_CH_           NUMBER, /*ребенок, ссылка на клиента*/
                      CUSID_M_            NUMBER, /*мать, ссылка на клиента*/
                      CUSID_F_            NUMBER, /*отец, ссылка на клиента*/
                      BRNACT_             NUMBER, /*рождение ребенка*/
                      SVID_SERIA_         VARCHAR2, /*серия*/
                      SVID_NOMER_         VARCHAR2, /*номер*/
                      CUSID_M_AD_         NUMBER, /*мать усынов.*/
                      CUSID_F_AD_         NUMBER, /*отец усынов.*/
                      ADOPT_PARENTS_      VARCHAR2, /*записываются ли усыновители родителями ребенка*/
                      ZAP_ISPOLKOM_RESH_  VARCHAR2, /*Решение исполкома (осн. записи об усыновл.)*/
                      ZAP_SOVET_DEP_TRUD_ VARCHAR2, /*Совета депутатов трудящихся (края, республики) (осн. записи об усыновл.)*/
                      ZAP_DATE_           DATE, /*дата (осн. записи об усыновл.)*/
                      ZAP_NUMBER_         VARCHAR2, /*номер (осн. записи об усыновл.)*/
                      NEW_BRTH_           DATE, /*дата рождения после*/
                      OLD_BRTH_           DATE, /*дата рождения до*/
                      BRN_CITY_           VARCHAR2,
                      BRN_AREA_           VARCHAR2,
                      BRN_OBL_RESP_       VARCHAR2) is
  begin
    update ADOPTOIN f
       set OLD_LASTNAME       = OLD_LASTNAME_,
           OLD_FIRSTNAME      = OLD_FIRSTNAME_,
           OLD_MIDDLNAME      = OLD_MIDDLNAME_,
           NEW_LASTNAME       = NEW_LASTNAME_,
           NEW_FIRSTNAME      = NEW_FIRSTNAME_,
           NEW_MIDDLNAME      = NEW_MIDDLNAME_,
           CUSID_CH           = CUSID_CH_,
           CUSID_M            = CUSID_M_,
           CUSID_F            = CUSID_F_,
           BRNACT             = BRNACT_,
           SVID_SERIA         = SVID_SERIA_,
           SVID_NOMER         = SVID_NOMER_,
           CUSID_M_AD         = CUSID_M_AD_,
           CUSID_F_AD         = CUSID_F_AD_,
           ADOPT_PARENTS      = ADOPT_PARENTS_,
           ZAP_ISPOLKOM_RESH  = ZAP_ISPOLKOM_RESH_,
           ZAP_SOVET_DEP_TRUD = ZAP_SOVET_DEP_TRUD_,
           ZAP_DATE           = ZAP_DATE_,
           ZAP_NUMBER         = ZAP_NUMBER_,
           NEW_BRTH           = NEW_BRTH_,
           OLD_BRTH           = OLD_BRTH_,
           BRN_CITY           = BRN_CITY_,
           BRN_AREA           = BRN_AREA_,
           BRN_OBL_RESP       = BRN_OBL_RESP_
    
     where f.id = id_;
  
    update cus
       set cus.ccuslast_name   = NEW_LASTNAME_,
           cus.ccusfirst_name  = NEW_FIRSTNAME_,
           cus.ccusmiddle_name = NEW_MIDDLNAME_,
           cus.dcusbirthday    = NEW_BRTH_
     where cus.icusnum = CUSID_CH_;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  /*Возврат текущего документа, простой XML*/
  procedure RetXmls(ID number, error out varchar2, clob_ out clob) is
  begin
    clob_ := dbms_xmlgen.getxmltype(q'[
select 
               ID, /*ИД*/
               ZAGS_ID, /*ИД загса*/
               OLD_LASTNAME, /*Фамилия до*/
               OLD_FIRSTNAME, /*Имя до*/
               OLD_MIDDLNAME, /*Отчество до*/
               NEW_LASTNAME, /*Фамилия после*/
               NEW_FIRSTNAME, /*Имя после*/
               NEW_MIDDLNAME, /*Отчество после*/
               CUSID_CH, /*ребенок, ссылка на клиента*/
               to_char(DOC_DATE, 'dd.mm.yyyy hh24:mi:ss') DOC_DATE, /*дата документа*/
               OPER, /*пользователь*/
               CUSID_M, /*мать, ссылка на клиента*/
               CUSID_F, /*отец, ссылка на клиента*/
               BRNACT, /*рождение ребенка*/
               SVID_SERIA, /*серия*/
               SVID_NOMER, /*номер*/
               CUSID_M_AD, /*мать усынов.*/
               CUSID_F_AD, /*отец усынов.*/
               ADOPT_PARENTS, /*записываются ли усыновители родителями ребенка*/
               ZAP_ISPOLKOM_RESH, /*Решение исполкома (осн. записи об усыновл.)*/
               ZAP_SOVET_DEP_TRUD, /*Совета депутатов трудящихся (края, республики) (осн. записи об усыновл.)*/
               to_char(ZAP_DATE, 'dd.mm.yyyy') ZAP_DATE, /*дата (осн. записи об усыновл.)*/
               ZAP_NUMBER, /*номер (осн. записи об усыновл.)*/
               to_char(NEW_BRTH, 'dd.mm.yyyy') NEW_BRTH, /*дата рождения после*/
               to_char(OLD_BRTH, 'dd.mm.yyyy') OLD_BRTH /*дата рождения до*/,
               BRN_CITY,
               BRN_AREA,
               BRN_OBL_RESP
FROM adoptoin where id = ]' || ID).GetClobVal();
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  procedure CompareXmls(ID    in number,
                        clob_ in clob,
                        error out varchar2,
                        res   out number) is
    cnt number;
    cursor compare(ID_ number, XML clob) is
      with xml as
       (SELECT ID, /*ИД*/
               ZAGS_ID, /*ИД загса*/
               OLD_LASTNAME, /*Фамилия до*/
               OLD_FIRSTNAME, /*Имя до*/
               OLD_MIDDLNAME, /*Отчество до*/
               NEW_LASTNAME, /*Фамилия после*/
               NEW_FIRSTNAME, /*Имя после*/
               NEW_MIDDLNAME, /*Отчество после*/
               CUSID_CH, /*ребенок, ссылка на клиента*/
               to_date(DOC_DATE, 'dd.mm.yyyy hh24:mi:ss') DOC_DATE, /*дата документа*/
               OPER, /*пользователь*/
               CUSID_M, /*мать, ссылка на клиента*/
               CUSID_F, /*отец, ссылка на клиента*/
               BRNACT, /*рождение ребенка*/
               SVID_SERIA, /*серия*/
               SVID_NOMER, /*номер*/
               CUSID_M_AD, /*мать усынов.*/
               CUSID_F_AD, /*отец усынов.*/
               ADOPT_PARENTS, /*записываются ли усыновители родителями ребенка*/
               ZAP_ISPOLKOM_RESH, /*Решение исполкома (осн. записи об усыновл.)*/
               ZAP_SOVET_DEP_TRUD, /*Совета депутатов трудящихся (края, республики) (осн. записи об усыновл.)*/
               to_date(ZAP_DATE, 'dd.mm.yyyy') ZAP_DATE, /*дата (осн. записи об усыновл.)*/
               ZAP_NUMBER, /*номер (осн. записи об усыновл.)*/
               to_date(NEW_BRTH, 'dd.mm.yyyy') NEW_BRTH, /*дата рождения после*/
               to_date(OLD_BRTH, 'dd.mm.yyyy') OLD_BRTH /*дата рождения до*/,
               BRN_CITY,
               BRN_AREA,
               BRN_OBL_RESP
          FROM XMLTABLE('/ROWSET/ROW' PASSING xmltype(XML) COLUMNS ID NUMBER PATH 'ID', /*ИД*/
                        ZAGS_ID NUMBER PATH 'ZAGS_ID', /*ИД загса*/
                        OLD_LASTNAME VARCHAR2(100) PATH 'OLD_LASTNAME', /*Фамилия до*/
                        OLD_FIRSTNAME VARCHAR2(100) PATH 'OLD_FIRSTNAME', /*Имя до*/
                        OLD_MIDDLNAME VARCHAR2(100) PATH 'OLD_MIDDLNAME', /*Отчество до*/
                        NEW_LASTNAME VARCHAR2(100) PATH 'NEW_LASTNAME', /*Фамилия после*/
                        NEW_FIRSTNAME VARCHAR2(100) PATH 'NEW_FIRSTNAME', /*Имя после*/
                        NEW_MIDDLNAME VARCHAR2(100) PATH 'NEW_MIDDLNAME', /*Отчество после*/
                        CUSID_CH NUMBER PATH 'CUSID_CH', /*ребенок, ссылка на клиента*/
                        DOC_DATE VARCHAR2(100) PATH 'DOC_DATE', /*дата документа*/
                        OPER VARCHAR2(100) PATH 'OPER', /*пользователь*/
                        CUSID_M NUMBER PATH 'CUSID_M', /*мать, ссылка на клиента*/
                        CUSID_F NUMBER PATH 'CUSID_F', /*отец, ссылка на клиента*/
                        BRNACT NUMBER PATH 'BRNACT', /*рождение ребенка*/
                        SVID_SERIA VARCHAR2(100) PATH 'SVID_SERIA', /*серия*/
                        SVID_NOMER VARCHAR2(100) PATH 'SVID_NOMER', /*номер*/
                        CUSID_M_AD NUMBER PATH 'CUSID_M_AD', /*мать усынов.*/
                        CUSID_F_AD NUMBER PATH 'CUSID_F_AD', /*отец усынов.*/
                        ADOPT_PARENTS VARCHAR2(1) PATH 'ADOPT_PARENTS', /*записываются ли усыновители родителями ребенка*/
                        ZAP_ISPOLKOM_RESH VARCHAR2(100) PATH
                        'ZAP_ISPOLKOM_RESH', /*Решение исполкома (осн. записи об усыновл.)*/
                        ZAP_SOVET_DEP_TRUD VARCHAR2(100) PATH
                        'ZAP_SOVET_DEP_TRUD', /*Совета депутатов трудящихся (края, республики) (осн. записи об усыновл.)*/
                        ZAP_DATE VARCHAR2(100) PATH 'ZAP_DATE', /*дата (осн. записи об усыновл.)*/
                        ZAP_NUMBER VARCHAR2(100) PATH 'ZAP_NUMBER', /*номер (осн. записи об усыновл.)*/
                        NEW_BRTH VARCHAR2(100) PATH 'NEW_BRTH', /*дата рождения после*/
                        OLD_BRTH VARCHAR2(100) PATH 'OLD_BRTH', /*дата рождения до*/
                        BRN_CITY VARCHAR2(100) PATH 'BRN_CITY',
                        BRN_AREA VARCHAR2(100) PATH 'BRN_AREA',
                        BRN_OBL_RESP VARCHAR2(100) PATH 'BRN_OBL_RESP') xmls),
      cur as
       (select ID, /*ИД*/
               ZAGS_ID, /*ИД загса*/
               OLD_LASTNAME, /*Фамилия до*/
               OLD_FIRSTNAME, /*Имя до*/
               OLD_MIDDLNAME, /*Отчество до*/
               NEW_LASTNAME, /*Фамилия после*/
               NEW_FIRSTNAME, /*Имя после*/
               NEW_MIDDLNAME, /*Отчество после*/
               CUSID_CH, /*ребенок, ссылка на клиента*/
               DOC_DATE, /*дата документа*/
               OPER, /*пользователь*/
               CUSID_M, /*мать, ссылка на клиента*/
               CUSID_F, /*отец, ссылка на клиента*/
               BRNACT, /*рождение ребенка*/
               SVID_SERIA, /*серия*/
               SVID_NOMER, /*номер*/
               CUSID_M_AD, /*мать усынов.*/
               CUSID_F_AD, /*отец усынов.*/
               ADOPT_PARENTS, /*записываются ли усыновители родителями ребенка*/
               ZAP_ISPOLKOM_RESH, /*Решение исполкома (осн. записи об усыновл.)*/
               ZAP_SOVET_DEP_TRUD, /*Совета депутатов трудящихся (края, республики) (осн. записи об усыновл.)*/
               ZAP_DATE, /*дата (осн. записи об усыновл.)*/
               ZAP_NUMBER, /*номер (осн. записи об усыновл.)*/
               NEW_BRTH, /*дата рождения после*/
               OLD_BRTH, /*дата рождения до*/
               BRN_CITY,
               BRN_AREA,
               BRN_OBL_RESP
          from adoptoin
         where adoptoin.id = ID_)
      select count(*) cnt
        from (select *
                from cur
              minus
              select *
                from xml
              union all
              select *
                from xml
              minus
              select *
                from cur);
  
  begin
  
    for r in compare(ID, clob_) loop
      cnt := r.cnt;
    end loop;
  
    if cnt > 0 then
      res := 1;
    else
      res := 0;
    end if;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

end ADOPT;
/
grant execute on XXI.ADOPT to ODB;


prompt
prompt Creating package body AUDITING
prompt ==============================
prompt
CREATE OR REPLACE PACKAGE BODY XXI.AUDITING IS

  /*
  || ---------------------------------------------------------------------------
  ||  Вспомогательная информаци
  || ---------------------------------------------------------------------------
  */
  PROCEDURE from_current_session IS
    PRAGMA AUTONOMOUS_TRANSACTION;
    Cursor AUAU Is
      Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
        from sys.v_$session
       where audsid = V_SESSION;
    --  V_ACTION2      VARCHAR2(2000);
  BEGIN
    V_SESSION    := UserEnv('SessionID');
    V_IP_ADDRESS := SYS_CONTEXT('USERENV', 'IP_ADDRESS');
  
    if dbms_job.BACKGROUND_PROCESS then
      Select s_au_session.nextval into V_ID_SESSION from dual;
      V_USER := USER;
      Return;
    end if;
    Open AUAU;
    Fetch AUAU
      into V_MACHINE,
           V_MODULE,
           V_OSUSER,
           V_USER,
           V_PROGRAM,
           V_CLIENT_IDENTIFIER;
    Close AUAU;
  END from_current_session;

BEGIN
  from_current_session;
END;
/
grant execute on XXI.AUDITING to ODB;


prompt
prompt Creating package body AUP_UTIL
prompt ==============================
prompt
CREATE OR REPLACE PACKAGE BODY XXI.AUP_UTIL IS
  --
  --
  --
  i_Trigger_Exists_Cursor INTEGER;
  --
  --
  --
  FUNCTION Is_Table(Tab_Owner IN VARCHAR2, Tab_Name IN VARCHAR2)
    RETURN BOOLEAN IS
    Dummy CHAR(1);
    CURSOR C_DBA_TABLES(Tab_Owner IN VARCHAR2, Tab_Name IN VARCHAR2) IS
      SELECT 'x'
        FROM DBA_TABLES
       WHERE OWNER = Tab_Owner
         AND TABLE_NAME = Tab_Name;
  BEGIN
    OPEN C_DBA_TABLES(Tab_Owner, Tab_Name);
    FETCH C_DBA_TABLES
      INTO Dummy;
    CLOSE C_DBA_TABLES;
  
    RETURN Dummy IS NOT NULL;
  
  END Is_Table;

  FUNCTION Obj_Status(Obj_Owner IN VARCHAR2,
                      Obj_Name  IN VARCHAR2,
                      Obj_Type  IN VARCHAR2) RETURN VARCHAR2 IS
    CURSOR C_OBJECT_STATUS(Obj_Owner IN VARCHAR2,
                           Obj_Name  IN VARCHAR2,
                           Obj_Type  IN VARCHAR2) IS
      SELECT STATUS
        FROM sys.DBA_OBJECTS
       WHERE OWNER = Obj_Owner
         AND OBJECT_NAME = Obj_Name
         AND OBJECT_TYPE = Obj_Type;
    Cur_Status DBA_OBJECTS.STATUS%TYPE;
  BEGIN
    OPEN C_OBJECT_STATUS(Obj_Owner, Obj_Name, Obj_Type);
    FETCH C_OBJECT_STATUS
      INTO Cur_Status;
    CLOSE C_OBJECT_STATUS;
  
    RETURN Cur_Status;
  END Obj_Status;
  --
  --
  --
  FUNCTION Trigger_Name(Tab_Name IN VARCHAR2) RETURN VARCHAR2 IS
  BEGIN
    IF LENGTH(Tab_Name) > 22 THEN
      RETURN 'T_A_IDU_' || UPPER(SUBSTR(Tab_Name, 1, 21)) || '#';
    ELSE
      RETURN 'T_A_IDU_' || UPPER(Tab_Name);
    END IF;
  END Trigger_Name;
  --
  --
  --
  FUNCTION Trigger_Exists(Tab_Name IN VARCHAR2, Tab_Owner IN VARCHAR2)
    RETURN VARCHAR2 IS
    cSource VARCHAR2(32000);
    /*чисто чтоб компилилось*/
    CURSOR C_TRIGGER(Tab_Name  IN VARCHAR2,
                     Tab_Owner IN VARCHAR2,
                     Trg_Name  IN VARCHAR2,
                     Trg_Owner IN VARCHAR2) IS
      SELECT TRIGGER_BODY
        FROM DBA_TRIGGERS
       WHERE OWNER = Trg_Owner
         AND TABLE_OWNER = Tab_Owner
         AND TABLE_NAME = Tab_Name
         AND TRIGGER_NAME = Trg_Name;
    i_Length INTEGER;
  BEGIN
    IF i_Trigger_Exists_Cursor IS NULL THEN
      i_Trigger_Exists_Cursor := DBMS_SQL_ADD.Open_Parse('SELECT /*AUP_UTIL.Trigger_Exists*/ Trigger_Body FROM ' ||
                                                         'DBA_TRIGGERS WHERE Owner = :1 AND Table_Owner = :2 AND ' ||
                                                         'Table_Name = :3 AND Trigger_Name = :4');
      DBMS_SQL.Define_Column_Long(i_Trigger_Exists_Cursor, 1);
    END IF;
  
    DBMS_SQL.Bind_Variable(i_Trigger_Exists_Cursor, '1', Tab_Owner);
    DBMS_SQL.Bind_Variable(i_Trigger_Exists_Cursor, '2', Tab_Owner);
    DBMS_SQL.Bind_Variable(i_Trigger_Exists_Cursor, '3', Tab_Name);
    DBMS_SQL.Bind_Variable(i_Trigger_Exists_Cursor,
                           '4',
                           Trigger_Name(Tab_Name));
  
    IF DBMS_SQL.Execute_And_Fetch(i_Trigger_Exists_Cursor) > 0 THEN
      DBMS_SQL.Column_Value_Long(i_Trigger_Exists_Cursor,
                                 1,
                                 32000,
                                 1,
                                 cSource,
                                 i_Length);
    
      IF INSTR(cSource,
               'шаблон аудиторского триггера для',
               1,
               1) > 0 THEN
        RETURN Obj_Status(Tab_Owner, Trigger_Name(Tab_Name), 'TRIGGER');
      END IF;
    END IF;
  
    RETURN NULL;
  END Trigger_Exists;
  --
  --
  --
  FUNCTION Get_Col_Comment(Tab_Name   IN VARCHAR2,
                           Tab_Owner  IN VARCHAR2,
                           Field_Name IN VARCHAR2) RETURN VARCHAR2 IS
    Col_Comment DBA_COL_COMMENTS.COMMENTS%TYPE;
    CURSOR C_DBA_COL_COMMENTS(Tab_Name  IN VARCHAR2,
                              Tab_Owner IN VARCHAR2,
                              Col_Name  IN VARCHAR2) IS
      SELECT COMMENTS
        FROM DBA_COL_COMMENTS
       WHERE TABLE_NAME = Tab_Name
         AND OWNER = Tab_Owner
         AND COLUMN_NAME = Col_Name;
  BEGIN
  
    OPEN C_DBA_COL_COMMENTS(Tab_Name, NVL(Tab_Owner, 'XXI'), Field_Name);
    FETCH C_DBA_COL_COMMENTS
      INTO Col_Comment;
    CLOSE C_DBA_COL_COMMENTS;
  
    RETURN NVL(Col_Comment, 'Нет данных');
  
  END Get_Col_Comment;

  FUNCTION Get_Tab_Comment(Tab_Name IN VARCHAR2, Tab_Owner IN VARCHAR2)
    RETURN VARCHAR2 IS
    Tab_Comment DBA_TAB_COMMENTS.COMMENTS%TYPE;
    CURSOR C_DBA_TAB_COMMENT(Tab_Name IN VARCHAR2, Tab_Owner IN VARCHAR) IS
      SELECT COMMENTS
        FROM DBA_TAB_COMMENTS
       WHERE TABLE_NAME = Tab_Name
         AND OWNER = Tab_Owner
         AND TABLE_TYPE = 'TABLE';
  BEGIN
  
    OPEN C_DBA_TAB_COMMENT(Tab_Name, NVL(Tab_Owner, 'XXI'));
    FETCH C_DBA_TAB_COMMENT
      INTO Tab_Comment;
    CLOSE C_DBA_TAB_COMMENT;
  
    RETURN NVL(Tab_Comment, 'Нет данных');
  
  END Get_Tab_Comment;

  PROCEDURE Put_Data(Action_ID  IN INTEGER,
                     Field_Name IN VARCHAR2,
                     NewData    IN VARCHAR2,
                     OldData    IN VARCHAR2) IS
  BEGIN
  
    IF NewData IS NULL AND OldData IS NULL THEN
      NULL;
    ELSE
      INSERT INTO AU_DATA
        (IACTION_ID, CFIELD, CNEWDATA, COLDDATA)
      VALUES
        (Action_ID, Field_Name, NewData, OldData);
    END IF;
  
  END Put_Data;

  PROCEDURE Put_CLOB_Data(Action_ID  IN INTEGER,
                          Field_Name IN VARCHAR2,
                          OldData    IN CLOB) IS
  BEGIN
  
    INSERT INTO AU_CLOB_DATA
      (IACTION_ID, CFIELD, COLDDATA)
    VALUES
      (Action_ID, Field_Name, OldData);
  END Put_CLOB_Data;

  PROCEDURE Put_BLOB_Data(Action_ID  IN INTEGER,
                          Field_Name IN VARCHAR2,
                          OldData    IN BLOB) IS
  BEGIN
    INSERT INTO AU_BLOB_DATA
      (IACTION_ID, CFIELD, BOLDDATA)
    VALUES
      (Action_ID, Field_Name, OldData);
  END Put_BLOB_Data;

  FUNCTION Get_Mode(Tbl_Name IN VARCHAR2) RETURN CHAR IS
    Cur_Mode AU_TABLES.CMODE%TYPE;
    CURSOR C_AU_TABLES_MOD(tname IN VARCHAR2) IS
      SELECT CMODE FROM AU_TABLES WHERE CNAME = tname;
  BEGIN
  
    /*
      IF NOT AUDITING.Audit_Make THEN
        RETURN 'N';
      END IF;
    */
    IF Tbl_Name = 'AU_TABLES' THEN
      RETURN 'Y';
    END IF;
  
    OPEN C_AU_TABLES_MOD(Tbl_Name);
    FETCH C_AU_TABLES_MOD
      INTO Cur_Mode;
    CLOSE C_AU_TABLES_MOD;
  
    IF UPPER(NVL(Cur_Mode, 'N')) = 'Y' THEN
      RETURN 'Y';
    END IF;
  
    RETURN 'N';
  
  EXCEPTION
    WHEN OTHERS THEN
      RETURN 'N';
    
  END Get_Mode;

  --
  --
  --
  FUNCTION NewAction(cTable   IN VARCHAR2,
                     cOp_Code IN VARCHAR2 DEFAULT NULL,
                     ID_Num   IN VARCHAR2 DEFAULT NULL,
                     ID_ANum  IN VARCHAR2 DEFAULT NULL,
                     ID_Row   IN ROWID DEFAULT NULL) RETURN INTEGER IS
    ID_New               INTEGER;
    OP_CODE              CHAR;
    V_SESSION            NUMBER;
    V_MACHINE            VARCHAR2(64);
    V_PROGRAM            VARCHAR2(64);
    V_ACTION             VARCHAR2(64);
    V_MODULE             VARCHAR2(64);
    V_OSUSER             VARCHAR2(64);
    V_USER               VARCHAR2(64);
    V_IP_ADDRESS         VARCHAR2(64);
    V_CLIENT_IDENTIFIER  VARCHAR2(64);
    V_ID_SESSION         NUMBER;
    V_DATE_SESSION       DATE;
    ID_MACHINE           NUMBER;
    ID_PROGRAM           NUMBER;
    ID_ACTION            NUMBER;
    ID_MODULE            NUMBER;
    ID_OSUSER            NUMBER;
    ID_USER              NUMBER;
    ID_IP_ADDRESS        NUMBER;
    ID_CLIENT_IDENTIFIER NUMBER;
  BEGIN
  
    V_SESSION    := UserEnv('SessionID');
    V_IP_ADDRESS := SYS_CONTEXT('USERENV', 'IP_ADDRESS');
    dbms_application_info.read_module(module_name => V_MODULE,
                                      action_name => V_ACTION);
  
    Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
      into V_MACHINE,
           V_MODULE,
           V_OSUSER,
           V_USER,
           V_PROGRAM,
           V_CLIENT_IDENTIFIER
      from sys.v_$session
     where audsid = V_SESSION;
  
    IF cOp_Code IS NULL THEN
      IF INSERTING THEN
        OP_CODE := 'I';
      ELSIF UPDATING THEN
        OP_CODE := 'U';
      ELSIF DELETING THEN
        OP_CODE := 'D';
      ELSE
        OP_CODE := 'J';
      END IF;
    ELSE
      OP_CODE := SUBSTR(cOp_Code, 1, 1);
    END IF;
  
    INSERT INTO AU_ACTION
      (IACTION_ID,
       CAUDMACHINE,
       CAUDPROGRAM,
       CAUDOPERATION,
       CTABLE,
       RROWID,
       CAUDACTION,
       CAUDMODULE,
       ID_NUM,
       ID_ANUM)
    VALUES
      (S_AU_ACTION.NextVal,
       V_MACHINE,
       V_PROGRAM,
       OP_CODE,
       cTable,
       ID_Row,
       V_ACTION,
       V_MODULE,
       ID_Num,
       ID_ANum)
    RETURNING IACTION_ID INTO ID_New;
  
    RETURN ID_New;
  END NewAction;
  --
  --
  --
  FUNCTION NewAction_AT(cTable   IN VARCHAR2,
                        cOp_Code IN VARCHAR2 DEFAULT NULL,
                        ID_Num   IN VARCHAR2 DEFAULT NULL,
                        ID_ANum  IN VARCHAR2 DEFAULT NULL,
                        ID_Row   IN ROWID DEFAULT NULL) RETURN INTEGER IS
    PRAGMA AUTONOMOUS_TRANSACTION;
    ID_New INTEGER;
  BEGIN
    ID_New := NewAction(cTable   => cTable,
                        cOp_Code => cOp_Code,
                        ID_Num   => ID_Num,
                        ID_ANum  => ID_ANum,
                        ID_Row   => ID_Row);
  
    DBMS_TRANSACTION.Commit();
  
    RETURN ID_New;
  END NewAction_AT;
  --
  --
  --
  PROCEDURE Put_Log(ID_Action IN INTEGER,
                    i_Order   IN OUT INTEGER,
                    cObj      IN VARCHAR2,
                    cText     IN VARCHAR2) IS
  BEGIN
    i_Order := NVL(i_Order, 0) + 1;
  
    INSERT INTO AU_LOG
      (iAction_ID, iOrder, cObj, cText)
    VALUES
      (ID_Action, i_Order, cObj, SUBSTR(cText, 1, 2000));
  
  END Put_Log;
  --
  --
  --
  PROCEDURE Put_Log(ID_Action IN INTEGER,
                    i_Order   IN OUT INTEGER,
                    cText     IN VARCHAR2) IS
  BEGIN
    Put_Log(ID_Action => ID_Action,
            i_Order   => i_Order,
            cObj      => NULL,
            cText     => cText);
  END Put_Log;
  --
  --
  --
  PROCEDURE Put_Log_AT(ID_Action IN INTEGER,
                       i_Order   IN OUT INTEGER,
                       cObj      IN VARCHAR2,
                       cText     IN VARCHAR2) IS
    PRAGMA AUTONOMOUS_TRANSACTION;
  BEGIN
    Put_Log(ID_Action => ID_Action,
            i_Order   => i_Order,
            cObj      => cObj,
            cText     => cText);
  
    DBMS_TRANSACTION.Commit();
  END Put_Log_AT;
  --
  --
  --
  PROCEDURE Put_Log_AT(ID_Action IN INTEGER,
                       i_Order   IN OUT INTEGER,
                       cText     IN VARCHAR2) IS
  BEGIN
    Put_Log_AT(ID_Action => ID_Action,
               i_Order   => i_Order,
               cObj      => NULL,
               cText     => cText);
  END Put_Log_AT;
  --
  -- Аудит поля в авт тран
  --
  PROCEDURE Put_Data_AT(Action_ID  IN INTEGER,
                        Field_Name IN VARCHAR2,
                        NewData    IN VARCHAR2,
                        OldData    IN VARCHAR2 DEFAULT NULL) IS
    PRAGMA AUTONOMOUS_TRANSACTION;
  BEGIN
    Put_Data(Action_ID  => Action_ID,
             Field_Name => Field_Name,
             NewData    => NewData,
             OldData    => OldData);
  
    DBMS_TRANSACTION.Commit();
  END Put_Data_AT;
  --
  -- au bulk insert
  --
  PROCEDURE Put_Data(TabAu      IN OUT NOCOPY T_TabAu,
                     Field_Name IN VARCHAR2,
                     NewData    IN VARCHAR2,
                     OldData    IN VARCHAR2) IS
    i_Curr INTEGER;
  BEGIN
  
    IF NewData IS NULL AND OldData IS NULL OR NewData = OldData THEN
      NULL;
    ELSE
      TabAu.Extend();
      i_Curr := TabAu.Last();
    
      TabAu(i_Curr).cField := Field_Name;
      TabAu(i_Curr).cNewData := NewData;
      TabAu(i_Curr).cOldData := OldData;
    END IF;
  
  END Put_Data;
  --
  -- Запись аудита
  --
  PROCEDURE Put_Data(Action_ID IN INTEGER, TabAu IN T_TabAu) IS
  BEGIN
  
    IF TabAu IS NOT NULL THEN
    
      FORALL i IN INDICES OF TabAu
        INSERT INTO AU_DATA
          (iAction_ID, cField, cNewData, cOldData)
        VALUES
          (Action_ID,
           TabAu    (i).cField,
           TabAu    (i).cNewData,
           TabAu    (i).cOldData);
    
    END IF;
  
  END Put_Data;
  --
  -- Предыдущее значение
  --
  FUNCTION PreviousValue(cTable  IN VARCHAR2,
                         cField  IN VARCHAR2,
                         ID_Num  IN VARCHAR2 DEFAULT NULL,
                         ID_ANum IN VARCHAR2 DEFAULT NULL,
                         ID_Row  IN ROWID DEFAULT NULL) RETURN VARCHAR2 IS
    CURSOR curID2 IS
      SELECT D.cOldData
        FROM AU_ACTION A, AU_DATA D
       WHERE A.cTable = PreviousValue.cTable
         AND A.ID_Num = PreviousValue.ID_Num
         AND A.ID_ANum = PreviousValue.ID_ANum
         AND A.iAction_ID = D.iAction_ID
         AND D.cField = PreviousValue.cField
       ORDER BY A.iAction_ID DESC;
    CURSOR curID1 IS
      SELECT D.cOldData
        FROM AU_ACTION A, AU_DATA D
       WHERE A.cTable = PreviousValue.cTable
         AND A.ID_Num = PreviousValue.ID_Num
         AND A.ID_ANum IS NULL
         AND A.iAction_ID = D.iAction_ID
         AND D.cField = PreviousValue.cField
       ORDER BY A.iAction_ID DESC;
    CURSOR curIDRow IS
      SELECT D.cOldData
        FROM AU_ACTION A, AU_DATA D
       WHERE A.cTable = PreviousValue.cTable
         AND A.rRowID = PreviousValue.ID_Row
         AND A.iAction_ID = D.iAction_ID
         AND D.cField = PreviousValue.cField
       ORDER BY A.iAction_ID DESC;
    cValue AU_DATA.cOldData%TYPE;
  BEGIN
  
    IF ID_Num IS NULL THEN
      OPEN curIDRow;
      FETCH curIDRow
        INTO cValue;
      CLOSE curIDRow;
    ELSE
      IF ID_ANum IS NULL THEN
        OPEN curID1;
        FETCH curID1
          INTO cValue;
        CLOSE curID1;
      ELSE
        OPEN curID2;
        FETCH curID2
          INTO cValue;
        CLOSE curID2;
      END IF;
    END IF;
  
    RETURN cValue;
  END PreviousValue;
  --
  -- Очистка лога
  --
  PROCEDURE DeleteLogAction(cTable  IN VARCHAR2,
                            ID_Num  IN VARCHAR2 DEFAULT NULL,
                            ID_ANum IN VARCHAR2 DEFAULT NULL) IS
  BEGIN
  
    DELETE FROM AU_ACTION
     WHERE cTable = DeleteLogAction.cTable
       AND ID_Num = DeleteLogAction.ID_Num
       AND ID_ANum = DeleteLogAction.ID_ANum
       AND cAudOperation = 'L';
  
  END DeleteLogAction;
  --
  -- Удалить аудит, 0 - успех
  --
  FUNCTION PK(CNAME varchar2) return varchar2 IS
    ret varchar2(500) := '';
  BEGIN
    SELECT COLUMN_NAME
      into ret
      FROM all_constraints cons, all_cons_columns cols
     WHERE cols.table_name = CNAME
       AND cons.constraint_type = 'P'
       AND cons.constraint_name = cols.constraint_name
       AND cons.owner = cols.owner
     ORDER BY cols.table_name, cols.position;
    return(ret);
  END;
  --
--
--
END AUP_UTIL;
/
grant execute on XXI.AUP_UTIL to ODB;


prompt
prompt Creating package body BURN_UTIL
prompt ===============================
prompt
create or replace package body xxi.BURN_UTIL is

  procedure writelog(error VARCHAR2, ret out VARCHAR2, pkname varchar2) is
    pragma autonomous_transaction;
  begin
    insert into LOG_ERROR
      (CURDATE_, DESC_ERROR_, PACKNAME)
    values
      (sysdate, error, pkname);
    ret := 'error';
    commit;
  end;

  function CRE_BURN(BR_ACT_ZTP_            VARCHAR2, /*Тип заявителя.F-физ.-J-юр.*/
                    BR_ACT_BRCHCNT_        NUMBER, /*Количество родившихся детей*/
                    BR_ACT_LD_             VARCHAR2, /*Живорожденный или мертворожденный*/
                    BR_ACT_TGRABF_         VARCHAR2, /*Тип основании сведении об отце*/
                    BR_ACT_MZDATE_         DATE, /*Дата заявления от матери, если (BR_ACT_TGRABF = V)*/
                    BR_ACT_DBF_            VARCHAR2, /*Документ подтверждающий факт орождении ребенка, тип*/
                    BR_ACT_CH_             NUMBER, /*Ссылка на ребенка*/
                    BR_ACT_F_              NUMBER, /*Ссылка на отца*/
                    BR_ACT_M_              NUMBER, /*Ссылка на мать*/
                    BR_ACT_MEDORGA_        VARCHAR2, /*Наименование мед. орг. выд. документ (A-Док. уст. формы)*/
                    BR_ACT_DATEDOCA_       DATE, /*Дата документа  (A-Док. уст. формы)*/
                    BR_ACT_NDOCA_          VARCHAR2, /*Номер документа  (A-Док. уст. формы)*/
                    BR_ACT_FIOB_           VARCHAR2, /*ФИО лица присутствовавшего во время родов (Б-Заявление)*/
                    BR_ACT_DATEDOCB_       DATE, /*Дата документа  (Б-Заявление)*/
                    BR_ACT_NAMECOURT_      VARCHAR2, /*Наимнование суда*/
                    BR_ACT_DESCCOURT_      VARCHAR2, /*Решение суда №*/
                    BR_ACT_DCOURT_         DATE, /*Дата решения суда*/
                    BR_ACT_FADFIRST_NAME_  VARCHAR2, /*Имя (Заявитель о рождении)*/
                    BR_ACT_FADLAST_NAME_   VARCHAR2, /*Фамилия (Заявитель о рождении)*/
                    BR_ACT_FADMIDDLE_NAME_ VARCHAR2, /*Отчество (Заявитель о рождении)*/
                    BR_ACT_FADLOCATION_    VARCHAR2, /*Место жительства (Заявитель о рождении)*/
                    BR_ACT_FADORG_NAME_    VARCHAR2, /*Наименование организации (Заявитель о рождении)*/
                    BR_ACT_FADREG_ADR_     VARCHAR2, /*Адрес регистрации (Заявитель о рождении)*/
                    BR_ACT_MERCER_ID_      NUMBER, /*Ссылка на свидетельство о заключении брака */
                    BR_ACT_NUM_            VARCHAR2, /*Номер (печать ЗАГСа)*/
                    BR_ACT_SERIA_          VARCHAR2, /*Серия (печать ЗАГСа)*/
                    BR_ACT_PATCER_         NUMBER, /*Ссылка на установл. отц*/
                    CRID                   OUT NUMBER) return varchar2 is
    ret    varchar2(4000) := 'ok';
    ZagsId number;
  begin
    begin
      select nvl(zags_id, -1)
        into ZagsId
        from usr
       where usr.cusrlogname = user;
    
      if ZagsId = -1 then
        writelog('У пользователя "' || user ||
                 '" не проставлен номер ЗАГСа!',
                 ret,
                 'AddBrnAct');
        return ret;
      end if;
    
      insert into BRN_BIRTH_ACT
        (BR_ACT_ZTP,
         BR_ACT_BRCHCNT,
         BR_ACT_LD,
         BR_ACT_TGRABF,
         BR_ACT_MZDATE,
         BR_ACT_DBF,
         BR_ACT_CH,
         BR_ACT_F,
         BR_ACT_M,
         BR_ACT_MEDORGA,
         BR_ACT_DATEDOCA,
         BR_ACT_NDOCA,
         BR_ACT_FIOB,
         BR_ACT_DATEDOCB,
         BR_ACT_NAMECOURT,
         BR_ACT_DESCCOURT,
         BR_ACT_DCOURT,
         BR_ACT_FADFIRST_NAME,
         BR_ACT_FADLAST_NAME,
         BR_ACT_FADMIDDLE_NAME,
         BR_ACT_FADLOCATION,
         BR_ACT_FADORG_NAME,
         BR_ACT_FADREG_ADR,
         BR_ACT_MERCER_ID,
         BR_ACT_NUM,
         BR_ACT_SERIA,
         BR_ACT_PATCER,
         BR_ACT_ZGID)
      values
        (BR_ACT_ZTP_,
         BR_ACT_BRCHCNT_,
         BR_ACT_LD_,
         BR_ACT_TGRABF_,
         BR_ACT_MZDATE_,
         BR_ACT_DBF_,
         BR_ACT_CH_,
         BR_ACT_F_,
         BR_ACT_M_,
         BR_ACT_MEDORGA_,
         BR_ACT_DATEDOCA_,
         BR_ACT_NDOCA_,
         BR_ACT_FIOB_,
         BR_ACT_DATEDOCB_,
         BR_ACT_NAMECOURT_,
         BR_ACT_DESCCOURT_,
         BR_ACT_DCOURT_,
         BR_ACT_FADFIRST_NAME_,
         BR_ACT_FADLAST_NAME_,
         BR_ACT_FADMIDDLE_NAME_,
         BR_ACT_FADLOCATION_,
         BR_ACT_FADORG_NAME_,
         BR_ACT_FADREG_ADR_,
         BR_ACT_MERCER_ID_,
         BR_ACT_NUM_,
         BR_ACT_SERIA_,
         BR_ACT_PATCER_,
         ZagsId)
      returning BR_ACT_ID into CRID;
    exception
      when others then
        writelog(sqlerrm || chr(13) || chr(10) ||
                 DBMS_UTILITY.FORMAT_ERROR_BACKTRACE,
                 ret,
                 'AddBrnAct');
    end;
    return(ret);
  end;

  function EDIT_BURN(BR_ACT_ZTP_            VARCHAR2, /*Тип заявителя.F-физ.-J-юр.*/
                     BR_ACT_BRCHCNT_        NUMBER, /*Количество родившихся детей*/
                     BR_ACT_LD_             VARCHAR2, /*Живорожденный или мертворожденный*/
                     BR_ACT_TGRABF_         VARCHAR2, /*Тип основании сведении об отце*/
                     BR_ACT_MZDATE_         DATE, /*Дата заявления от матери, если (BR_ACT_TGRABF = V)*/
                     BR_ACT_DBF_            VARCHAR2, /*Документ подтверждающий факт орождении ребенка, тип*/
                     BR_ACT_CH_             NUMBER, /*Ссылка на ребенка*/
                     BR_ACT_F_              NUMBER, /*Ссылка на отца*/
                     BR_ACT_M_              NUMBER, /*Ссылка на мать*/
                     BR_ACT_MEDORGA_        VARCHAR2, /*Наименование мед. орг. выд. документ (A-Док. уст. формы)*/
                     BR_ACT_DATEDOCA_       DATE, /*Дата документа  (A-Док. уст. формы)*/
                     BR_ACT_NDOCA_          VARCHAR2, /*Номер документа  (A-Док. уст. формы)*/
                     BR_ACT_FIOB_           VARCHAR2, /*ФИО лица присутствовавшего во время родов (Б-Заявление)*/
                     BR_ACT_DATEDOCB_       DATE, /*Дата документа  (Б-Заявление)*/
                     BR_ACT_NAMECOURT_      VARCHAR2, /*Наимнование суда*/
                     BR_ACT_DESCCOURT_      VARCHAR2, /*Решение суда №*/
                     BR_ACT_DCOURT_         DATE, /*Дата решения суда*/
                     BR_ACT_FADFIRST_NAME_  VARCHAR2, /*Имя (Заявитель о рождении)*/
                     BR_ACT_FADLAST_NAME_   VARCHAR2, /*Фамилия (Заявитель о рождении)*/
                     BR_ACT_FADMIDDLE_NAME_ VARCHAR2, /*Отчество (Заявитель о рождении)*/
                     BR_ACT_FADLOCATION_    VARCHAR2, /*Место жительства (Заявитель о рождении)*/
                     BR_ACT_FADORG_NAME_    VARCHAR2, /*Наименование организации (Заявитель о рождении)*/
                     BR_ACT_FADREG_ADR_     VARCHAR2, /*Адрес регистрации (Заявитель о рождении)*/
                     BR_ACT_MERCER_ID_      NUMBER, /*Ссылка на свидетельство о заключении брака */
                     BR_ACT_NUM_            VARCHAR2, /*Номер (печать ЗАГСа)*/
                     BR_ACT_SERIA_          VARCHAR2, /*Серия (печать ЗАГСа)*/
                     BR_ACT_PATCER_         NUMBER, /*Ссылка на установл. отц*/
                     CRID                   NUMBER) return varchar2 is
    ret varchar2(4000) := 'ok';
  begin
    begin
      update BRN_BIRTH_ACT t
         set BR_ACT_ZTP            = BR_ACT_ZTP_,
             BR_ACT_BRCHCNT        = BR_ACT_BRCHCNT_,
             BR_ACT_LD             = BR_ACT_LD_,
             BR_ACT_TGRABF         = BR_ACT_TGRABF_,
             BR_ACT_MZDATE         = BR_ACT_MZDATE_,
             BR_ACT_DBF            = BR_ACT_DBF_,
             BR_ACT_CH             = BR_ACT_CH_,
             BR_ACT_F              = BR_ACT_F_,
             BR_ACT_M              = BR_ACT_M_,
             BR_ACT_MEDORGA        = BR_ACT_MEDORGA_,
             BR_ACT_DATEDOCA       = BR_ACT_DATEDOCA_,
             BR_ACT_NDOCA          = BR_ACT_NDOCA_,
             BR_ACT_FIOB           = BR_ACT_FIOB_,
             BR_ACT_DATEDOCB       = BR_ACT_DATEDOCB_,
             BR_ACT_NAMECOURT      = BR_ACT_NAMECOURT_,
             BR_ACT_DESCCOURT      = BR_ACT_DESCCOURT_,
             BR_ACT_DCOURT         = BR_ACT_DCOURT_,
             BR_ACT_FADFIRST_NAME  = BR_ACT_FADFIRST_NAME_,
             BR_ACT_FADLAST_NAME   = BR_ACT_FADLAST_NAME_,
             BR_ACT_FADMIDDLE_NAME = BR_ACT_FADMIDDLE_NAME_,
             BR_ACT_FADLOCATION    = BR_ACT_FADLOCATION_,
             BR_ACT_FADORG_NAME    = BR_ACT_FADORG_NAME_,
             BR_ACT_FADREG_ADR     = BR_ACT_FADREG_ADR_,
             BR_ACT_MERCER_ID      = BR_ACT_MERCER_ID_,
             BR_ACT_NUM            = BR_ACT_NUM_,
             BR_ACT_SERIA          = BR_ACT_SERIA_,
             BR_ACT_PATCER         = BR_ACT_PATCER_
       where t.br_act_id = CRID;
    exception
      when others then
        writelog(sqlerrm || chr(13) || chr(10) ||
                 DBMS_UTILITY.FORMAT_ERROR_BACKTRACE,
                 ret,
                 'EditBrnAct');
    end;
    return(ret);
  end;

  /*Возврат текущего документа, простой XML*/
  procedure RetXmls(ID number, error out varchar2, clob_ out clob) is
  begin
    clob_ := dbms_xmlgen.getxmltype(q'[
               select BR_ACT_ID, /*ИД акта о рождении*/
               to_char(BR_ACT_DATE, 'dd.mm.yyyy hh24:mi:ss') BR_ACT_DATE, /*Дата заведения*/
               BR_ACT_ZTP, /*Тип заявителя.F-физ.-J-юр.*/
               BR_ACT_BRCHCNT, /*Количество родившихся детей*/
               BR_ACT_LD, /*Живорожденный или мертворожденный*/
               BR_ACT_ZGID, /*Наименование ЗАГСа*/
               BR_ACT_USER, /*Пользователь*/
               BR_ACT_TGRABF, /*Тип основании сведении об отце*/
               to_char(BR_ACT_MZDATE, 'dd.mm.yyyy') BR_ACT_MZDATE, /*Дата заявления от матери, если (BR_ACT_TGRABF = V)*/
               BR_ACT_DBF, /*Документ подтверждающий факт орождении ребенка, тип*/
               BR_ACT_CH, /*Ссылка на ребенка*/
               BR_ACT_F, /*Ссылка на отца*/
               BR_ACT_M, /*Ссылка на мать*/
               BR_ACT_MEDORGA, /*Наименование мед. орг. выд. документ (A-Док. уст. формы)*/
               to_char(BR_ACT_DATEDOCA, 'dd.mm.yyyy') BR_ACT_DATEDOCA, /*Дата документа  (A-Док. уст. формы)*/
               BR_ACT_NDOCA, /*Номер документа  (A-Док. уст. формы)*/
               BR_ACT_FIOB, /*ФИО лица присутствовавшего во время родов (Б-Заявление)*/
               to_char(BR_ACT_DATEDOCB, 'dd.mm.yyyy') BR_ACT_DATEDOCB, /*Дата документа  (Б-Заявление)*/
               BR_ACT_NAMECOURT, /*Наимнование суда*/
               BR_ACT_DESCCOURT, /*Решение суда №*/
               to_char(BR_ACT_DCOURT, 'dd.mm.yyyy') BR_ACT_DCOURT, /*Дата решения суда*/
               BR_ACT_FADFIRST_NAME, /*Имя (Заявитель о рождении)*/
               BR_ACT_FADLAST_NAME, /*Фамилия (Заявитель о рождении)*/
               BR_ACT_FADMIDDLE_NAME, /*Отчество (Заявитель о рождении)*/
               BR_ACT_FADLOCATION, /*Место жительства (Заявитель о рождении)*/
               BR_ACT_FADORG_NAME, /*Наименование организации (Заявитель о рождении)*/
               BR_ACT_FADREG_ADR, /*Адрес регистрации (Заявитель о рождении)*/
               BR_ACT_MERCER_ID, /*Ссылка на свидетельство о заключении брака */
               BR_ACT_NUM, /*Номер (печать ЗАГСа)*/
               BR_ACT_SERIA, /*Серия (печать ЗАГСа)*/
               BR_ACT_PATCER /*Ссылка на установл. отц*/
FROM BRN_BIRTH_ACT where BRN_BIRTH_ACT.BR_ACT_ID = ]' || ID).GetClobVal();
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  procedure CompareXmls(ID    in number,
                        clob_ in clob,
                        error out varchar2,
                        res   out number) is
    cnt number;
    cursor compare(ID_ number, XML clob) is
      with xml as
       (SELECT BR_ACT_ID, /*ИД акта о рождении*/
               to_date(BR_ACT_DATE, 'dd.mm.yyyy hh24:mi:ss') BR_ACT_DATE, /*Дата заведения*/
               BR_ACT_ZTP, /*Тип заявителя.F-физ.-J-юр.*/
               BR_ACT_BRCHCNT, /*Количество родившихся детей*/
               BR_ACT_LD, /*Живорожденный или мертворожденный*/
               BR_ACT_ZGID, /*Наименование ЗАГСа*/
               BR_ACT_USER, /*Пользователь*/
               BR_ACT_TGRABF, /*Тип основании сведении об отце*/
               to_date(BR_ACT_MZDATE, 'dd.mm.yyyy') BR_ACT_MZDATE, /*Дата заявления от матери, если (BR_ACT_TGRABF = V)*/
               BR_ACT_DBF, /*Документ подтверждающий факт орождении ребенка, тип*/
               BR_ACT_CH, /*Ссылка на ребенка*/
               BR_ACT_F, /*Ссылка на отца*/
               BR_ACT_M, /*Ссылка на мать*/
               BR_ACT_MEDORGA, /*Наименование мед. орг. выд. документ (A-Док. уст. формы)*/
               to_date(BR_ACT_DATEDOCA, 'dd.mm.yyyy') BR_ACT_DATEDOCA, /*Дата документа  (A-Док. уст. формы)*/
               BR_ACT_NDOCA, /*Номер документа  (A-Док. уст. формы)*/
               BR_ACT_FIOB, /*ФИО лица присутствовавшего во время родов (Б-Заявление)*/
               to_date(BR_ACT_DATEDOCB, 'dd.mm.yyyy') BR_ACT_DATEDOCB, /*Дата документа  (Б-Заявление)*/
               BR_ACT_NAMECOURT, /*Наимнование суда*/
               BR_ACT_DESCCOURT, /*Решение суда №*/
               to_date(BR_ACT_DCOURT, 'dd.mm.yyyy') BR_ACT_DCOURT, /*Дата решения суда*/
               BR_ACT_FADFIRST_NAME, /*Имя (Заявитель о рождении)*/
               BR_ACT_FADLAST_NAME, /*Фамилия (Заявитель о рождении)*/
               BR_ACT_FADMIDDLE_NAME, /*Отчество (Заявитель о рождении)*/
               BR_ACT_FADLOCATION, /*Место жительства (Заявитель о рождении)*/
               BR_ACT_FADORG_NAME, /*Наименование организации (Заявитель о рождении)*/
               BR_ACT_FADREG_ADR, /*Адрес регистрации (Заявитель о рождении)*/
               BR_ACT_MERCER_ID, /*Ссылка на свидетельство о заключении брака */
               BR_ACT_NUM, /*Номер (печать ЗАГСа)*/
               BR_ACT_SERIA, /*Серия (печать ЗАГСа)*/
               BR_ACT_PATCER /*Ссылка на установл. отц*/
          FROM XMLTABLE('/ROWSET/ROW' PASSING xmltype(XML) COLUMNS BR_ACT_ID
                        NUMBER PATH 'BR_ACT_ID', /*ИД акта о рождении*/
                        BR_ACT_DATE VARCHAR2(100) PATH 'BR_ACT_DATE', /*Дата заведения*/
                        BR_ACT_ZTP VARCHAR2(1) PATH 'BR_ACT_ZTP', /*Тип заявителя.F-физ.-J-юр.*/
                        BR_ACT_BRCHCNT NUMBER PATH 'BR_ACT_BRCHCNT', /*Количество родившихся детей*/
                        BR_ACT_LD VARCHAR2(1) PATH 'BR_ACT_LD', /*Живорожденный или мертворожденный*/
                        BR_ACT_ZGID NUMBER PATH 'BR_ACT_ZGID', /*Наименование ЗАГСа*/
                        BR_ACT_USER VARCHAR2(100) PATH 'BR_ACT_USER', /*Пользователь*/
                        BR_ACT_TGRABF VARCHAR2(1) PATH 'BR_ACT_TGRABF', /*Тип основании сведении об отце*/
                        BR_ACT_MZDATE VARCHAR2(100) PATH 'BR_ACT_MZDATE', /*Дата заявления от матери, если (BR_ACT_TGRABF = V)*/
                        BR_ACT_DBF VARCHAR2(1) PATH 'BR_ACT_DBF', /*Документ подтверждающий факт орождении ребенка, тип*/
                        BR_ACT_CH NUMBER PATH 'BR_ACT_CH', /*Ссылка на ребенка*/
                        BR_ACT_F NUMBER PATH 'BR_ACT_F', /*Ссылка на отца*/
                        BR_ACT_M NUMBER PATH 'BR_ACT_M', /*Ссылка на мать*/
                        BR_ACT_MEDORGA VARCHAR2(200) PATH 'BR_ACT_MEDORGA', /*Наименование мед. орг. выд. документ (A-Док. уст. формы)*/
                        BR_ACT_DATEDOCA VARCHAR2(100) PATH 'BR_ACT_DATEDOCA', /*Дата документа  (A-Док. уст. формы)*/
                        BR_ACT_NDOCA VARCHAR2(200) PATH 'BR_ACT_NDOCA', /*Номер документа  (A-Док. уст. формы)*/
                        BR_ACT_FIOB VARCHAR2(200) PATH 'BR_ACT_FIOB', /*ФИО лица присутствовавшего во время родов (Б-Заявление)*/
                        BR_ACT_DATEDOCB VARCHAR2(100) PATH 'BR_ACT_DATEDOCB', /*Дата документа  (Б-Заявление)*/
                        BR_ACT_NAMECOURT VARCHAR2(200) PATH
                        'BR_ACT_NAMECOURT', /*Наимнование суда*/
                        BR_ACT_DESCCOURT VARCHAR2(200) PATH
                        'BR_ACT_DESCCOURT', /*Решение суда №*/
                        BR_ACT_DCOURT VARCHAR2(100) PATH 'BR_ACT_DCOURT', /*Дата решения суда*/
                        BR_ACT_FADFIRST_NAME VARCHAR2(100) PATH
                        'BR_ACT_FADFIRST_NAME', /*Имя (Заявитель о рождении)*/
                        BR_ACT_FADLAST_NAME VARCHAR2(100) PATH
                        'BR_ACT_FADLAST_NAME', /*Фамилия (Заявитель о рождении)*/
                        BR_ACT_FADMIDDLE_NAME VARCHAR2(100) PATH
                        'BR_ACT_FADMIDDLE_NAME', /*Отчество (Заявитель о рождении)*/
                        BR_ACT_FADLOCATION VARCHAR2(200) PATH
                        'BR_ACT_FADLOCATION', /*Место жительства (Заявитель о рождении)*/
                        BR_ACT_FADORG_NAME VARCHAR2(200) PATH
                        'BR_ACT_FADORG_NAME', /*Наименование организации (Заявитель о рождении)*/
                        BR_ACT_FADREG_ADR VARCHAR2(200) PATH
                        'BR_ACT_FADREG_ADR', /*Адрес регистрации (Заявитель о рождении)*/
                        BR_ACT_MERCER_ID NUMBER PATH 'BR_ACT_MERCER_ID', /*Ссылка на свидетельство о заключении брака */
                        BR_ACT_NUM VARCHAR2(100) PATH 'BR_ACT_NUM', /*Номер (печать ЗАГСа)*/
                        BR_ACT_SERIA VARCHAR2(100) PATH 'BR_ACT_SERIA', /*Серия (печать ЗАГСа)*/
                        BR_ACT_PATCER NUMBER PATH 'BR_ACT_PATCER' /*Ссылка на установл. отц*/) xmls),
      cur as
       (select BR_ACT_ID, /*ИД акта о рождении*/
               BR_ACT_DATE, /*Дата заведения*/
               BR_ACT_ZTP, /*Тип заявителя.F-физ.-J-юр.*/
               BR_ACT_BRCHCNT, /*Количество родившихся детей*/
               BR_ACT_LD, /*Живорожденный или мертворожденный*/
               BR_ACT_ZGID, /*Наименование ЗАГСа*/
               BR_ACT_USER, /*Пользователь*/
               BR_ACT_TGRABF, /*Тип основании сведении об отце*/
               BR_ACT_MZDATE, /*Дата заявления от матери, если (BR_ACT_TGRABF = V)*/
               BR_ACT_DBF, /*Документ подтверждающий факт орождении ребенка, тип*/
               BR_ACT_CH, /*Ссылка на ребенка*/
               BR_ACT_F, /*Ссылка на отца*/
               BR_ACT_M, /*Ссылка на мать*/
               BR_ACT_MEDORGA, /*Наименование мед. орг. выд. документ (A-Док. уст. формы)*/
               BR_ACT_DATEDOCA, /*Дата документа  (A-Док. уст. формы)*/
               BR_ACT_NDOCA, /*Номер документа  (A-Док. уст. формы)*/
               BR_ACT_FIOB, /*ФИО лица присутствовавшего во время родов (Б-Заявление)*/
               BR_ACT_DATEDOCB, /*Дата документа  (Б-Заявление)*/
               BR_ACT_NAMECOURT, /*Наимнование суда*/
               BR_ACT_DESCCOURT, /*Решение суда №*/
               BR_ACT_DCOURT, /*Дата решения суда*/
               BR_ACT_FADFIRST_NAME, /*Имя (Заявитель о рождении)*/
               BR_ACT_FADLAST_NAME, /*Фамилия (Заявитель о рождении)*/
               BR_ACT_FADMIDDLE_NAME, /*Отчество (Заявитель о рождении)*/
               BR_ACT_FADLOCATION, /*Место жительства (Заявитель о рождении)*/
               BR_ACT_FADORG_NAME, /*Наименование организации (Заявитель о рождении)*/
               BR_ACT_FADREG_ADR, /*Адрес регистрации (Заявитель о рождении)*/
               BR_ACT_MERCER_ID, /*Ссылка на свидетельство о заключении брака */
               BR_ACT_NUM, /*Номер (печать ЗАГСа)*/
               BR_ACT_SERIA, /*Серия (печать ЗАГСа)*/
               BR_ACT_PATCER /*Ссылка на установл. отц*/
          from BRN_BIRTH_ACT
         where BRN_BIRTH_ACT.BR_ACT_ID = ID_)
      select count(*) cnt
        from (select *
                from cur
              minus
              select *
                from xml
              union all
              select *
                from xml
              minus
              select *
                from cur);
  
  begin
  
    for r in compare(ID, clob_) loop
      cnt := r.cnt;
    end loop;
  
    if cnt > 0 then
      res := 1;
    else
      res := 0;
    end if;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

end BURN_UTIL;
/
grant execute on XXI.BURN_UTIL to ODB;


prompt
prompt Creating package body DBMS_SQL_ADD
prompt ==================================
prompt
CREATE OR REPLACE PACKAGE BODY XXI.DBMS_SQL_ADD IS

  FUNCTION EXEC_PARAM(params VARCHAR2) RETURN varchar2 IS
    v_ret varchar2(1000);
  BEGIN
    EXECUTE IMMEDIATE 'SELECT ' || REPLACE(params, '&') || ' FROM DUAL'
      INTO v_ret;
    RETURN v_ret;
  END;

  FUNCTION Open_Parse(cSQL IN VARCHAR2) RETURN INTEGER IS
    iOpen_Parse INTEGER;
  BEGIN
    iOpen_Parse := DBMS_SQL.Open_Cursor;
    DBMS_SQL.Parse(iOpen_Parse, cSQL, DBMS_SQL.Native);
    RETURN iOpen_Parse;
  END Open_Parse;

  PROCEDURE Execute_SQL(SQL_Str IN VARCHAR2) IS
    iCursor  INTEGER;
    iExecute INTEGER;
  BEGIN
    iCursor  := Open_Parse(SQL_Str);
    iExecute := DBMS_SQL.Execute(iCursor);
    DBMS_SQL.Close_Cursor(iCursor);
  EXCEPTION
    WHEN OTHERS THEN
      IF DBMS_SQL.Is_Open(iCursor) THEN
        DBMS_SQL.Close_Cursor(iCursor);
      END IF;
      RAISE;
  END Execute_SQL;

  PROCEDURE Commit IS
  BEGIN
    DBMS_TRANSACTION.Commit;
  END Commit;

  PROCEDURE RollBack IS
  BEGIN
    DBMS_TRANSACTION.RollBack;
  END RollBack;

END DBMS_SQL_ADD;
/
grant execute on XXI.DBMS_SQL_ADD to ODB;


prompt
prompt Creating package body DEATCH
prompt ============================
prompt
create or replace package body xxi.Deatch is

  procedure writelog(error VARCHAR2, ret out VARCHAR2, mackname varchar2) is
    pragma autonomous_transaction;
  begin
    insert into LOG_ERROR
      (CURDATE_, DESC_ERROR_, PACKNAME)
    values
      (sysdate, error, mackname);
    ret := 'error';
    commit;
  end;

  /*Добавить свидетельство о смерти*/
  function AddDeath(DC_NUMBER_         VARCHAR2, /*Выдано свидетельство номер*/
                    DC_SERIA_          VARCHAR2, /*Выдано свидетельство серия*/
                    DC_FADREG_ADR_     VARCHAR2, /*Адрес регистрации (Заявитель о смерти)*/
                    DC_FADORG_NAME_    VARCHAR2, /*Наименование организации (Заявитель о смерти)*/
                    DC_FADLOCATION_    VARCHAR2, /*Место жительства (Заявитель о смерти)*/
                    DC_FADMIDDLE_NAME_ VARCHAR2, /*Отчество (Заявитель о смерти)*/
                    DC_FADLAST_NAME_   VARCHAR2, /*Фамилия (Заявитель о смерти)*/
                    DC_FADFIRST_NAME_  VARCHAR2, /*Имя (Заявитель о смерти)*/
                    DC_ZTP_            VARCHAR2, /*Тип заявителя.F-физ.-J-юр.*/
                    DC_LLOC_           VARCHAR2, /*Последнее место жительства*/
                    DC_NRNAME_         VARCHAR2, /*V-Док. подтвержд. факт смерти-Необоснованно репрес.-Наименование органа выд. документ*/
                    DC_RCNAME_         VARCHAR2, /*B-Док. подтвержд. факт смерти-Решение суда-Наименование суда*/
                    DC_FMON_           VARCHAR2, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти-Наименование мед. организации*/
                    DC_FTYPE_          VARCHAR2, /*Док. подтвержд. факт смерти-Тип*/
                    DC_FD_             DATE, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Дата*/
                    DC_FNUM_           VARCHAR2, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Номер*/
                    DC_CD_             VARCHAR2, /*Причина смерти*/
                    DC_DPL_            VARCHAR2, /*Место смерти*/
                    DC_DD_             DATE, /*Дата смерти*/
                    DC_CUS_            NUMBER, /*Ссылка на cus*/
                    DC_ID_             OUT NUMBER /*ID*/) return varchar2 is
    ret    varchar2(10) := 'ok';
    Pknm   varchar2(100) := 'AddDeath';
    ZagsId number;
  begin
    begin
      --проверки
      if DC_DPL_ is null then
        writelog('Укажиет место смерти', ret, Pknm);
      end if;
      if DC_DD_ is null then
        writelog('Укажиет дату смерти', ret, Pknm);
      end if;
      if DC_CUS_ is null then
        writelog('Отсутствует ссылка на гражданина',
                 ret,
                 Pknm);
      end if;
      if DC_CD_ is null then
        writelog('Укажиет прицину смерти', ret, Pknm);
      end if;
    
      select nvl(zags_id, -1)
        into ZagsId
        from usr
       where usr.cusrlogname = user;
    
      if ZagsId = -1 then
        writelog('У пользователя "' || user ||
                 '" не проставлен номер ЗАГСа!',
                 ret,
                 Pknm);
        return ret;
      end if;
    
      insert into DEATH_CERT
        (DC_ZAGS, /*Наименование загса*/
         DC_NUMBER, /*Выдано свидетельство номер*/
         DC_SERIA, /*Выдано свидетельство серия*/
         DC_FADREG_ADR, /*Адрес регистрации (Заявитель о смерти)*/
         DC_FADORG_NAME, /*Наименование организации (Заявитель о смерти)*/
         DC_FADLOCATION, /*Место жительства (Заявитель о смерти)*/
         DC_FADMIDDLE_NAME, /*Отчество (Заявитель о смерти)*/
         DC_FADLAST_NAME, /*Фамилия (Заявитель о смерти)*/
         DC_FADFIRST_NAME, /*Имя (Заявитель о смерти)*/
         DC_ZTP, /*Тип заявителя.F-физ.-J-юр.*/
         DC_LLOC, /*Последнее место жительства*/
         DC_NRNAME, /*V-Док. подтвержд. факт смерти-Необоснованно репрес.-Наименование органа выд. документ*/
         DC_RCNAME, /*B-Док. подтвержд. факт смерти-Решение суда-Наименование суда*/
         DC_FMON, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти-Наименование мед. организации*/
         DC_FTYPE, /*Док. подтвержд. факт смерти-Тип*/
         DC_FD, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Дата*/
         DC_FNUM, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Номер*/
         DC_CD, /*Причина смерти*/
         DC_DPL, /*Место смерти*/
         DC_DD, /*Дата смерти*/
         DC_CUS /*Ссылка на cus*/)
      values
        (ZagsId, /*Наименование загса*/
         DC_NUMBER_, /*Выдано свидетельство номер*/
         DC_SERIA_, /*Выдано свидетельство серия*/
         DC_FADREG_ADR_, /*Адрес регистрации (Заявитель о смерти)*/
         DC_FADORG_NAME_, /*Наименование организации (Заявитель о смерти)*/
         DC_FADLOCATION_, /*Место жительства (Заявитель о смерти)*/
         DC_FADMIDDLE_NAME_, /*Отчество (Заявитель о смерти)*/
         DC_FADLAST_NAME_, /*Фамилия (Заявитель о смерти)*/
         DC_FADFIRST_NAME_, /*Имя (Заявитель о смерти)*/
         case DC_ZTP_ when 'Физ. лицо' then 'F' when 'Организация' then 'J' end, /*Тип заявителя.F-физ.-J-юр.*/
         DC_LLOC_, /*Последнее место жительства*/
         DC_NRNAME_, /*V-Док. подтвержд. факт смерти-Необоснованно репрес.-Наименование органа выд. документ*/
         DC_RCNAME_, /*B-Док. подтвержд. факт смерти-Решение суда-Наименование суда*/
         DC_FMON_, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти-Наименование мед. организации*/
         case DC_FTYPE_ when 'Документ установленной формы о смерти' then 'A' when
         'Решение суда об установлении факта о смерти' then 'B' when
         'Решение суда об установлении лица умершим' then 'B1' when
         'Документ о факте смерти лица, необоснованно репрессированного' then 'V' end, /*Док. подтвержд. факт смерти-Тип*/
         DC_FD_, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Дата*/
         DC_FNUM_, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Номер*/
         DC_CD_, /*Причина смерти*/
         DC_DPL_, /*Место смерти*/
         DC_DD_, /*Дата смерти*/
         DC_CUS_ /*Ссылка на cus*/)
      returning DC_ID into DC_ID_;
    
    exception
      when others then
        writelog(sqlerrm || chr(13) || chr(10) ||
                 DBMS_UTILITY.FORMAT_ERROR_BACKTRACE,
                 ret,
                 Pknm);
    end;
    return(ret);
  end;

  /*Редактировать свидетельство о смерти*/
  function EditDeath(DC_NUMBER_         VARCHAR2, /*Выдано свидетельство номер*/
                     DC_SERIA_          VARCHAR2, /*Выдано свидетельство серия*/
                     DC_FADREG_ADR_     VARCHAR2, /*Адрес регистрации (Заявитель о смерти)*/
                     DC_FADORG_NAME_    VARCHAR2, /*Наименование организации (Заявитель о смерти)*/
                     DC_FADLOCATION_    VARCHAR2, /*Место жительства (Заявитель о смерти)*/
                     DC_FADMIDDLE_NAME_ VARCHAR2, /*Отчество (Заявитель о смерти)*/
                     DC_FADLAST_NAME_   VARCHAR2, /*Фамилия (Заявитель о смерти)*/
                     DC_FADFIRST_NAME_  VARCHAR2, /*Имя (Заявитель о смерти)*/
                     DC_ZTP_            VARCHAR2, /*Тип заявителя.F-физ.-J-юр.*/
                     DC_LLOC_           VARCHAR2, /*Последнее место жительства*/
                     DC_NRNAME_         VARCHAR2, /*V-Док. подтвержд. факт смерти-Необоснованно репрес.-Наименование органа выд. документ*/
                     DC_RCNAME_         VARCHAR2, /*B-Док. подтвержд. факт смерти-Решение суда-Наименование суда*/
                     DC_FMON_           VARCHAR2, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти-Наименование мед. организации*/
                     DC_FTYPE_          VARCHAR2, /*Док. подтвержд. факт смерти-Тип*/
                     DC_FD_             DATE, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Дата*/
                     DC_FNUM_           VARCHAR2, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Номер*/
                     DC_CD_             VARCHAR2, /*Причина смерти*/
                     DC_DPL_            VARCHAR2, /*Место смерти*/
                     DC_DD_             DATE, /*Дата смерти*/
                     DC_CUS_            NUMBER, /*Ссылка на cus*/
                     DC_ID_             NUMBER /*ID*/) return varchar2 is
    ret    varchar2(10) := 'ok';
    Pknm   varchar2(100) := 'EditDeath';
    ZagsId number;
  begin
    begin
      --проверки
      if DC_DPL_ is null then
        writelog('Укажиет место смерти', ret, Pknm);
      end if;
      if DC_DD_ is null then
        writelog('Укажиет дату смерти', ret, Pknm);
      end if;
      if DC_CUS_ is null then
        writelog('Отсутствует ссылка на гражданина',
                 ret,
                 Pknm);
      end if;
      if DC_CD_ is null then
        writelog('Укажиет прицину смерти', ret, Pknm);
      end if;
    
      select nvl(zags_id, -1)
        into ZagsId
        from usr
       where usr.cusrlogname = user;
    
      if ZagsId = -1 then
        writelog('У пользователя "' || user ||
                 '" не проставлен номер ЗАГСа!',
                 ret,
                 Pknm);
        return ret;
      end if;
    
      update DEATH_CERT t
         set /*DC_ZAGS           = ZagsId, \*Наименование загса*\*/
             DC_NUMBER         = DC_NUMBER_, /*Выдано свидетельство номер*/
             DC_SERIA          = DC_SERIA_, /*Выдано свидетельство серия*/
             DC_FADREG_ADR     = DC_FADREG_ADR_, /*Адрес регистрации (Заявитель о смерти)*/
             DC_FADORG_NAME    = DC_FADORG_NAME_, /*Наименование организации (Заявитель о смерти)*/
             DC_FADLOCATION    = DC_FADLOCATION_, /*Место жительства (Заявитель о смерти)*/
             DC_FADMIDDLE_NAME = DC_FADMIDDLE_NAME_, /*Отчество (Заявитель о смерти)*/
             DC_FADLAST_NAME   = DC_FADLAST_NAME_, /*Фамилия (Заявитель о смерти)*/
             DC_FADFIRST_NAME  = DC_FADFIRST_NAME_, /*Имя (Заявитель о смерти)*/
             DC_ZTP            = case DC_ZTP_
                                   when 'Физ. лицо' then
                                    'F'
                                   when 'Организация' then
                                    'J'
                                 end, /*Тип заявителя.F-физ.-J-юр.*/
             DC_LLOC           = DC_LLOC_, /*Последнее место жительства*/
             DC_NRNAME         = DC_NRNAME_, /*V-Док. подтвержд. факт смерти-Необоснованно репрес.-Наименование органа выд. документ*/
             DC_RCNAME         = DC_RCNAME_, /*B-Док. подтвержд. факт смерти-Решение суда-Наименование суда*/
             DC_FMON           = DC_FMON_, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти-Наименование мед. организации*/
             DC_FTYPE          = case DC_FTYPE_
                                   when 'Документ установленной формы о смерти' then
                                    'A'
                                   when
                                    'Решение суда об установлении факта о смерти' then
                                    'B'
                                   when
                                    'Решение суда об установлении лица умершим' then
                                    'B1'
                                   when
                                    'Документ о факте смерти лица, необоснованно репрессированного' then
                                    'V'
                                 end, /*Док. подтвержд. факт смерти-Тип*/
             DC_FD             = DC_FD_, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Дата*/
             DC_FNUM           = DC_FNUM_, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Номер*/
             DC_CD             = DC_CD_, /*Причина смерти*/
             DC_DPL            = DC_DPL_, /*Место смерти*/
             DC_DD             = DC_DD_, /*Дата смерти*/
             DC_CUS            = DC_CUS_
       where t.DC_ID = DC_ID_;
    
    exception
      when others then
        writelog(sqlerrm || chr(13) || chr(10) ||
                 DBMS_UTILITY.FORMAT_ERROR_BACKTRACE,
                 ret,
                 Pknm);
    end;
    return(ret);
  end;

  /*Возврат текущего документа, простой XML*/
  procedure RetXmls(ID number, error out varchar2, clob_ out clob) is
  begin
    clob_ := dbms_xmlgen.getxmltype(q'[
 select 
               DC_ZAGS, /*Наименование загса*/
               to_char(DC_OPEN, 'dd.mm.yyyy hh24:mi:ss') DC_OPEN, /*Дата документа*/
               DC_USR, /*Пользователь*/
               DC_NUMBER, /*Выдано свидетельство номер*/
               DC_SERIA, /*Выдано свидетельство серия*/
               DC_FADREG_ADR, /*Адрес регистрации (Заявитель о смерти)*/
               DC_FADORG_NAME, /*Наименование организации (Заявитель о смерти)*/
               DC_FADLOCATION, /*Место жительства (Заявитель о смерти)*/
               DC_FADMIDDLE_NAME, /*Отчество (Заявитель о смерти)*/
               DC_FADLAST_NAME, /*Фамилия (Заявитель о смерти)*/
               DC_FADFIRST_NAME, /*Имя (Заявитель о смерти)*/
               DC_ZTP, /*Тип заявителя.F-физ.-J-юр.*/
               DC_LLOC, /*Последнее место жительства*/
               DC_NRNAME, /*V-Док. подтвержд. факт смерти-Необоснованно репрес.-Наименование органа выд. документ*/
               DC_RCNAME, /*B-Док. подтвержд. факт смерти-Решение суда-Наименование суда*/
               DC_FMON, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти-Наименование мед. организации*/
               DC_FTYPE, /*Док. подтвержд. факт смерти-Тип*/
               to_char(DC_FD, 'dd.mm.yyyy') DC_FD, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Дата*/
               DC_FNUM, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Номер*/
               DC_CD, /*Причина смерти*/
               DC_DPL, /*Место смерти*/
               to_char(DC_DD, 'dd.mm.yyyy') DC_DD, /*Дата смерти*/
               DC_CUS, /*Ссылка на cus*/
               DC_ID /*ID*/
FROM DEATH_CERT where DEATH_CERT.DC_ID = ]' || ID).GetClobVal();
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  procedure CompareXmls(ID    in number,
                        clob_ in clob,
                        error out varchar2,
                        res   out number) is
    cnt number;
    cursor compare(ID_ number, XML clob) is
      with xml as
       (SELECT DC_ZAGS, /*Наименование загса*/
               to_date(DC_OPEN, 'dd.mm.yyyy hh24:mi:ss') DC_OPEN, /*Дата документа*/
               DC_USR, /*Пользователь*/
               DC_NUMBER, /*Выдано свидетельство номер*/
               DC_SERIA, /*Выдано свидетельство серия*/
               DC_FADREG_ADR, /*Адрес регистрации (Заявитель о смерти)*/
               DC_FADORG_NAME, /*Наименование организации (Заявитель о смерти)*/
               DC_FADLOCATION, /*Место жительства (Заявитель о смерти)*/
               DC_FADMIDDLE_NAME, /*Отчество (Заявитель о смерти)*/
               DC_FADLAST_NAME, /*Фамилия (Заявитель о смерти)*/
               DC_FADFIRST_NAME, /*Имя (Заявитель о смерти)*/
               DC_ZTP, /*Тип заявителя.F-физ.-J-юр.*/
               DC_LLOC, /*Последнее место жительства*/
               DC_NRNAME, /*V-Док. подтвержд. факт смерти-Необоснованно репрес.-Наименование органа выд. документ*/
               DC_RCNAME, /*B-Док. подтвержд. факт смерти-Решение суда-Наименование суда*/
               DC_FMON, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти-Наименование мед. организации*/
               DC_FTYPE, /*Док. подтвержд. факт смерти-Тип*/
               to_date(DC_FD, 'dd.mm.yyyy') DC_FD, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Дата*/
               DC_FNUM, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Номер*/
               DC_CD, /*Причина смерти*/
               DC_DPL, /*Место смерти*/
               to_date(DC_DD, 'dd.mm.yyyy') DC_DD, /*Дата смерти*/
               DC_CUS, /*Ссылка на cus*/
               DC_ID /*ID*/
          FROM XMLTABLE('/ROWSET/ROW' PASSING xmltype(XML) COLUMNS DC_ID
                        NUMBER PATH 'DC_ID', /*ID*/
                        DC_CUS NUMBER PATH 'DC_CUS', /*Ссылка на cus*/
                        DC_DD VARCHAR2(200) PATH 'DC_DD', /*Дата смерти*/
                        DC_DPL VARCHAR2(200) PATH 'DC_DPL', /*Место смерти*/
                        DC_CD VARCHAR2(200) PATH 'DC_CD', /*Причина смерти*/
                        DC_FNUM VARCHAR2(200) PATH 'DC_FNUM', /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Номер*/
                        DC_FD VARCHAR2(200) PATH 'DC_FD', /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Дата*/
                        DC_FTYPE VARCHAR2(1) PATH 'DC_FTYPE', /*Док. подтвержд. факт смерти-Тип*/
                        DC_FMON VARCHAR2(200) PATH 'DC_FMON', /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти-Наименование мед. организации*/
                        DC_RCNAME VARCHAR2(200) PATH 'DC_RCNAME', /*B-Док. подтвержд. факт смерти-Решение суда-Наименование суда*/
                        DC_NRNAME VARCHAR2(200) PATH 'DC_NRNAME', /*V-Док. подтвержд. факт смерти-Необоснованно репрес.-Наименование органа выд. документ*/
                        DC_LLOC VARCHAR2(200) PATH 'DC_LLOC', /*Последнее место жительства*/
                        DC_ZTP VARCHAR2(1) PATH 'DC_ZTP', /*Тип заявителя.F-физ.-J-юр.*/
                        DC_FADFIRST_NAME VARCHAR2(200) PATH
                        'DC_FADFIRST_NAME', /*Имя (Заявитель о смерти)*/
                        DC_FADLAST_NAME VARCHAR2(200) PATH 'DC_FADLAST_NAME', /*Фамилия (Заявитель о смерти)*/
                        DC_FADMIDDLE_NAME VARCHAR2(200) PATH
                        'DC_FADMIDDLE_NAME', /*Отчество (Заявитель о смерти)*/
                        DC_FADLOCATION VARCHAR2(200) PATH 'DC_FADLOCATION', /*Место жительства (Заявитель о смерти)*/
                        DC_FADORG_NAME VARCHAR2(200) PATH 'DC_FADORG_NAME', /*Наименование организации (Заявитель о смерти)*/
                        DC_FADREG_ADR VARCHAR2(200) PATH 'DC_FADREG_ADR', /*Адрес регистрации (Заявитель о смерти)*/
                        DC_SERIA VARCHAR2(200) PATH 'DC_SERIA', /*Выдано свидетельство серия*/
                        DC_NUMBER VARCHAR2(200) PATH 'DC_NUMBER', /*Выдано свидетельство номер*/
                        DC_USR VARCHAR2(200) PATH 'DC_USR', /*Пользователь*/
                        DC_OPEN VARCHAR2(200) PATH 'DC_OPEN', /*Дата документа*/
                        DC_ZAGS NUMBER PATH 'DC_ZAGS' /*Наименование загса*/) xmls),
      cur as
       (select DC_ZAGS, /*Наименование загса*/
               DC_OPEN, /*Дата документа*/
               DC_USR, /*Пользователь*/
               DC_NUMBER, /*Выдано свидетельство номер*/
               DC_SERIA, /*Выдано свидетельство серия*/
               DC_FADREG_ADR, /*Адрес регистрации (Заявитель о смерти)*/
               DC_FADORG_NAME, /*Наименование организации (Заявитель о смерти)*/
               DC_FADLOCATION, /*Место жительства (Заявитель о смерти)*/
               DC_FADMIDDLE_NAME, /*Отчество (Заявитель о смерти)*/
               DC_FADLAST_NAME, /*Фамилия (Заявитель о смерти)*/
               DC_FADFIRST_NAME, /*Имя (Заявитель о смерти)*/
               DC_ZTP, /*Тип заявителя.F-физ.-J-юр.*/
               DC_LLOC, /*Последнее место жительства*/
               DC_NRNAME, /*V-Док. подтвержд. факт смерти-Необоснованно репрес.-Наименование органа выд. документ*/
               DC_RCNAME, /*B-Док. подтвержд. факт смерти-Решение суда-Наименование суда*/
               DC_FMON, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти-Наименование мед. организации*/
               DC_FTYPE, /*Док. подтвержд. факт смерти-Тип*/
               DC_FD, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Дата*/
               DC_FNUM, /*A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Номер*/
               DC_CD, /*Причина смерти*/
               DC_DPL, /*Место смерти*/
               DC_DD, /*Дата смерти*/
               DC_CUS, /*Ссылка на cus*/
               DC_ID /*ID*/
          from DEATH_CERT
         where DEATH_CERT.DC_ID = ID_)
      select count(*) cnt
        from (select *
                from cur
              minus
              select *
                from xml
              union all
              select *
                from xml
              minus
              select *
                from cur);
  
  begin
  
    for r in compare(ID, clob_) loop
      cnt := r.cnt;
    end loop;
  
    if cnt > 0 then
      res := 1;
    else
      res := 0;
    end if;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

end Deatch;
/
grant execute on XXI.DEATCH to ODB;


prompt
prompt Creating package body DIVORCE
prompt =============================
prompt
create or replace package body xxi.Divorce is

  sep varchar2(2) := chr(13) || chr(10);

  procedure AddDivorce(error           out varchar2,
                       DIVC_SERIA_     VARCHAR2, /*Выдано свидетельство серия*/
                       DIVC_NUM_       VARCHAR2, /*Выдано свидетельство номер*/
                       DIVC_MC_MERCER_ NUMBER, /*Ссылка на акт о заключении брака*/
                       DIVC_ZOSPRISON_ NUMBER, /*V-(Расторжение,приговор суда) приговор суда=лишение свободы (количество лет)*/
                       DIVC_ZOSFIO2_   VARCHAR2, /*V-(Расторжение,приговор суда) приговор суда=об осуждении ФИО*/
                       DIVC_ZOSCD2_    DATE, /*V-(Расторжение,приговор суда) приговор суда=дата (от)*/
                       DIVC_ZOSCN2_    VARCHAR2, /*V-(Расторжение,приговор суда) приговор суда=наименование суда */
                       DIVC_ZOSFIO_    VARCHAR2, /*V-(Расторжение,заявление одного из супругов) о признании ФИО*/
                       DIVC_ZOSCD_     DATE, /*V-(Расторжение,заявление одного из супругов) дата решения суда*/
                       DIVC_ZOSCN_     VARCHAR2, /*V-(Расторжение,заявление одного из супругов) наименование суда*/
                       DIVC_CAD_       DATE, /*B-(Расторжение,решение суда) дата*/
                       DIVC_CAN_       VARCHAR2, /*B-(Расторжение,решение суда) наименование суда*/
                       DIVC_TCHNUM_    VARCHAR2, /*A-(Расторжение, совместное заявление) номер*/
                       DIVC_TCHD_      DATE, /*A-(Расторжение, совместное заявление) дата*/
                       DIVC_TYPE_      VARCHAR2, /*Типы основании расторжения брака*/
                       DIVC_DT_        DATE, /*Дата прекращения брака*/
                       DIVC_SHE_LNAFT_ VARCHAR2, /*Фамилия ее после рб*/
                       DIVC_SHE_LNBEF_ VARCHAR2, /*Фамилия ее до рб*/
                       DIVC_HE_LNAFT_  VARCHAR2, /*Фамилия его после рб*/
                       DIVC_HE_LNBEF_  VARCHAR2, /*Фамилия его до рб*/
                       DIVC_SHE_       NUMBER, /*Она ссылка на CUS*/
                       DIVC_HE_        NUMBER, /*Он ссылка на CUS*/
                       DIVC_ZPLACE_    VARCHAR2, /*Заявитель Место жительства*/
                       DIVC_ZMNAME_    VARCHAR2, /*Заявитель Отчество*/
                       DIVC_ZАNAME_   VARCHAR2, /*Заявитель Имя*/
                       DIVC_ZLNAME_    VARCHAR2, /*Заявитель Фамилия*/
                       ID              out number) is
    ZagsId number;
  begin
  
    if DIVC_MC_MERCER_ is null then
        error := 'Не выбран документ о заключении брака!';
      return;
    end if;
    
    select nvl(zags_id, -1)
      into ZagsId
      from usr
     where usr.cusrlogname = user;
  
    if ZagsId = -1 then
      error := error || sep || 'У пользователя "' || user ||
               '" не проставлен номер ЗАГСа!';
      return;
    end if;
  
    insert into DIVORCE_CERT
      (DIVC_ZAGS, /*Ссылка на загс*/
       DIVC_SERIA, /*Выдано свидетельство серия*/
       DIVC_NUM, /*Выдано свидетельство номер*/
       DIVC_MC_MERCER, /*Ссылка на акт о заключении брака*/
       DIVC_ZOSPRISON, /*V-(Расторжение,приговор суда) приговор суда=лишение свободы (количество лет)*/
       DIVC_ZOSFIO2, /*V-(Расторжение,приговор суда) приговор суда=об осуждении ФИО*/
       DIVC_ZOSCD2, /*V-(Расторжение,приговор суда) приговор суда=дата (от)*/
       DIVC_ZOSCN2, /*V-(Расторжение,приговор суда) приговор суда=наименование суда */
       DIVC_ZOSFIO, /*V-(Расторжение,заявление одного из супругов) о признании ФИО*/
       DIVC_ZOSCD, /*V-(Расторжение,заявление одного из супругов) дата решения суда*/
       DIVC_ZOSCN, /*V-(Расторжение,заявление одного из супругов) наименование суда*/
       DIVC_CAD, /*B-(Расторжение,решение суда) дата*/
       DIVC_CAN, /*B-(Расторжение,решение суда) наименование суда*/
       DIVC_TCHNUM, /*A-(Расторжение, совместное заявление) номер*/
       DIVC_TCHD, /*A-(Расторжение, совместное заявление) дата*/
       DIVC_TYPE, /*Типы основании расторжения брака*/
       DIVC_DT, /*Дата прекращения брака*/
       DIVC_SHE_LNAFT, /*Фамилия ее после рб*/
       DIVC_SHE_LNBEF, /*Фамилия ее до рб*/
       DIVC_HE_LNAFT, /*Фамилия его после рб*/
       DIVC_HE_LNBEF, /*Фамилия его до рб*/
       DIVC_SHE, /*Она ссылка на CUS*/
       DIVC_HE, /*Он ссылка на CUS*/
       DIVC_ZPLACE, /*Заявитель Место жительства*/
       DIVC_ZMNAME, /*Заявитель Отчество*/
       DIVC_ZАNAME, /*Заявитель Имя*/
       DIVC_ZLNAME /*Заявитель Фамилия*/)
    values
      (ZagsId, /*Ссылка на загс*/
       DIVC_SERIA_, /*Выдано свидетельство серия*/
       DIVC_NUM_, /*Выдано свидетельство номер*/
       DIVC_MC_MERCER_, /*Ссылка на акт о заключении брака*/
       DIVC_ZOSPRISON_, /*V-(Расторжение,приговор суда) приговор суда=лишение свободы (количество лет)*/
       DIVC_ZOSFIO2_, /*V-(Расторжение,приговор суда) приговор суда=об осуждении ФИО*/
       DIVC_ZOSCD2_, /*V-(Расторжение,приговор суда) приговор суда=дата (от)*/
       DIVC_ZOSCN2_, /*V-(Расторжение,приговор суда) приговор суда=наименование суда */
       DIVC_ZOSFIO_, /*V-(Расторжение,заявление одного из супругов) о признании ФИО*/
       DIVC_ZOSCD_, /*V-(Расторжение,заявление одного из супругов) дата решения суда*/
       DIVC_ZOSCN_, /*V-(Расторжение,заявление одного из супругов) наименование суда*/
       DIVC_CAD_, /*B-(Расторжение,решение суда) дата*/
       DIVC_CAN_, /*B-(Расторжение,решение суда) наименование суда*/
       DIVC_TCHNUM_, /*A-(Расторжение, совместное заявление) номер*/
       DIVC_TCHD_, /*A-(Расторжение, совместное заявление) дата*/
       decode(DIVC_TYPE_,
              'Совместное заявление супругов, не имеющих общих детей, не достигших совершеннолетия',
              'A',
              'Решение суда о расторжении брака',
              'B',
              'Заявление одного из супругов и решение суда о признании безвестно отсутствующим',
              'V1',
              'Заявление одного из супругов и решение суда о признании недееспособным',
              'V2',
              'Приговор суда об осуждении и лишении свободы',
              'V3'), /*Типы основании расторжения брака*/
       DIVC_DT_, /*Дата прекращения брака*/
       DIVC_SHE_LNAFT_, /*Фамилия ее после рб*/
       DIVC_SHE_LNBEF_, /*Фамилия ее до рб*/
       DIVC_HE_LNAFT_, /*Фамилия его после рб*/
       DIVC_HE_LNBEF_, /*Фамилия его до рб*/
       DIVC_SHE_, /*Она ссылка на CUS*/
       DIVC_HE_, /*Он ссылка на CUS*/
       DIVC_ZPLACE_, /*Заявитель Место жительства*/
       DIVC_ZMNAME_, /*Заявитель Отчество*/
       DIVC_ZАNAME_, /*Заявитель Имя*/
       DIVC_ZLNAME_ /*Заявитель Фамилия*/)
    returning DIVC_ID into ID;
  
    update cus
       set cus.ccuslast_name = DIVC_SHE_LNAFT_
     where cus.icusnum = DIVC_SHE_;
  
    update cus
       set cus.ccuslast_name = DIVC_HE_LNAFT_
     where cus.icusnum = DIVC_HE_;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  procedure EditDivorce(error           out varchar2,
                        DIVC_SERIA_     VARCHAR2, /*Выдано свидетельство серия*/
                        DIVC_NUM_       VARCHAR2, /*Выдано свидетельство номер*/
                        DIVC_MC_MERCER_ NUMBER, /*Ссылка на акт о заключении брака*/
                        DIVC_ZOSPRISON_ NUMBER, /*V-(Расторжение,приговор суда) приговор суда=лишение свободы (количество лет)*/
                        DIVC_ZOSFIO2_   VARCHAR2, /*V-(Расторжение,приговор суда) приговор суда=об осуждении ФИО*/
                        DIVC_ZOSCD2_    DATE, /*V-(Расторжение,приговор суда) приговор суда=дата (от)*/
                        DIVC_ZOSCN2_    VARCHAR2, /*V-(Расторжение,приговор суда) приговор суда=наименование суда */
                        DIVC_ZOSFIO_    VARCHAR2, /*V-(Расторжение,заявление одного из супругов) о признании ФИО*/
                        DIVC_ZOSCD_     DATE, /*V-(Расторжение,заявление одного из супругов) дата решения суда*/
                        DIVC_ZOSCN_     VARCHAR2, /*V-(Расторжение,заявление одного из супругов) наименование суда*/
                        DIVC_CAD_       DATE, /*B-(Расторжение,решение суда) дата*/
                        DIVC_CAN_       VARCHAR2, /*B-(Расторжение,решение суда) наименование суда*/
                        DIVC_TCHNUM_    VARCHAR2, /*A-(Расторжение, совместное заявление) номер*/
                        DIVC_TCHD_      DATE, /*A-(Расторжение, совместное заявление) дата*/
                        DIVC_TYPE_      VARCHAR2, /*Типы основании расторжения брака*/
                        DIVC_DT_        DATE, /*Дата прекращения брака*/
                        DIVC_SHE_LNAFT_ VARCHAR2, /*Фамилия ее после рб*/
                        DIVC_SHE_LNBEF_ VARCHAR2, /*Фамилия ее до рб*/
                        DIVC_HE_LNAFT_  VARCHAR2, /*Фамилия его после рб*/
                        DIVC_HE_LNBEF_  VARCHAR2, /*Фамилия его до рб*/
                        DIVC_SHE_       NUMBER, /*Она ссылка на CUS*/
                        DIVC_HE_        NUMBER, /*Он ссылка на CUS*/
                        DIVC_ZPLACE_    VARCHAR2, /*Заявитель Место жительства*/
                        DIVC_ZMNAME_    VARCHAR2, /*Заявитель Отчество*/
                        DIVC_ZАNAME_   VARCHAR2, /*Заявитель Имя*/
                        DIVC_ZLNAME_    VARCHAR2, /*Заявитель Фамилия*/
                        ID              IN number) is
  begin
  
  if DIVC_MC_MERCER_ is null then
        error := 'Не выбран документ о заключении брака!';
      return;
    end if;
    
    update DIVORCE_CERT t
       set DIVC_SERIA     = DIVC_SERIA_,
           DIVC_NUM       = DIVC_NUM_,
           DIVC_MC_MERCER = DIVC_MC_MERCER_,
           DIVC_ZOSPRISON = DIVC_ZOSPRISON_,
           DIVC_ZOSFIO2   = DIVC_ZOSFIO2_,
           DIVC_ZOSCD2    = DIVC_ZOSCD2_,
           DIVC_ZOSCN2    = DIVC_ZOSCN2_,
           DIVC_ZOSFIO    = DIVC_ZOSFIO_,
           DIVC_ZOSCD     = DIVC_ZOSCD_,
           DIVC_ZOSCN     = DIVC_ZOSCN_,
           DIVC_CAD       = DIVC_CAD_,
           DIVC_CAN       = DIVC_CAN_,
           DIVC_TCHNUM    = DIVC_TCHNUM_,
           DIVC_TCHD      = DIVC_TCHD_,
           DIVC_TYPE      = decode(DIVC_TYPE_,
                                   'Совместное заявление супругов, не имеющих общих детей, не достигших совершеннолетия',
                                   'A',
                                   'Решение суда о расторжении брака',
                                   'B',
                                   'Заявление одного из супругов и решение суда о признании безвестно отсутствующим',
                                   'V1',
                                   'Заявление одного из супругов и решение суда о признании недееспособным',
                                   'V2',
                                   'Приговор суда об осуждении и лишении свободы',
                                   'V3'),
           DIVC_DT        = DIVC_DT_,
           DIVC_SHE_LNAFT = DIVC_SHE_LNAFT_,
           DIVC_SHE_LNBEF = DIVC_SHE_LNBEF_,
           DIVC_HE_LNAFT  = DIVC_HE_LNAFT_,
           DIVC_HE_LNBEF  = DIVC_HE_LNBEF_,
           DIVC_SHE       = DIVC_SHE_,
           DIVC_HE        = DIVC_HE_,
           DIVC_ZPLACE    = DIVC_ZPLACE_,
           DIVC_ZMNAME    = DIVC_ZMNAME_,
           DIVC_ZАNAME   = DIVC_ZАNAME_,
           DIVC_ZLNAME    = DIVC_ZLNAME_
     where t.divc_id = ID;
  
    update cus
       set cus.ccuslast_name = DIVC_SHE_LNAFT_
     where cus.icusnum = DIVC_SHE_;
  
    update cus
       set cus.ccuslast_name = DIVC_HE_LNAFT_
     where cus.icusnum = DIVC_HE_;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  /*Возврат текущего документа, простой XML*/
  procedure RetXmls(ID number, error out varchar2, clob_ out clob) is
  begin
    clob_ := dbms_xmlgen.getxmltype(q'[
select 
DIVC_ID, /*ID*/
               DIVC_HE, /*Он ссылка на CUS*/
               DIVC_SHE, /*Она ссылка на CUS*/
               DIVC_HE_LNBEF, /*Фамилия его до рб*/
               DIVC_HE_LNAFT, /*Фамилия его после рб*/
               DIVC_SHE_LNBEF, /*Фамилия ее до рб*/
               DIVC_SHE_LNAFT, /*Фамилия ее после рб*/
               to_char(DIVC_DATE, 'dd.mm.yyyy hh24:mi:ss') DIVC_DATE, /*Дата документа*/
               to_char(DIVC_DT, 'dd.mm.yyyy') DIVC_DT, /*Дата прекращения брака*/
               DIVC_USR, /*Пользователь*/
               DIVC_TYPE, /*Типы основании расторжения брака*/
               to_char(DIVC_TCHD, 'dd.mm.yyyy') DIVC_TCHD, /*A-(Расторжение, совместное заявление) дата*/
               DIVC_TCHNUM, /*A-(Расторжение, совместное заявление) номер*/
               DIVC_CAN, /*B-(Расторжение,решение суда) наименование суда*/
               to_char(DIVC_CAD, 'dd.mm.yyyy') DIVC_CAD, /*B-(Расторжение,решение суда) дата*/
               DIVC_ZOSCN, /*V-(Расторжение,заявление одного из супругов) наименование суда*/
               to_char(DIVC_ZOSCD, 'dd.mm.yyyy') DIVC_ZOSCD, /*V-(Расторжение,заявление одного из супругов) дата решения суда*/
               DIVC_ZOSFIO, /*V-(Расторжение,заявление одного из супругов) о признании ФИО*/
               DIVC_ZOSCN2, /*V-(Расторжение,приговор суда) приговор суда=наименование суда */
               to_char(DIVC_ZOSCD2, 'dd.mm.yyyy') DIVC_ZOSCD2, /*V-(Расторжение,приговор суда) приговор суда=дата (от)*/
               DIVC_ZOSFIO2, /*V-(Расторжение,приговор суда) приговор суда=об осуждении ФИО*/
               DIVC_ZOSPRISON, /*V-(Расторжение,приговор суда) приговор суда=лишение свободы (количество лет)*/
               DIVC_MC_MERCER, /*Ссылка на акт о заключении брака*/
               DIVC_NUM, /*Выдано свидетельство номер*/
               DIVC_SERIA, /*Выдано свидетельство серия*/
               DIVC_ZAGS, /*Ссылка на загс*/
               DIVC_ZLNAME, /*Заявитель Фамилия*/
               DIVC_ZАNAME, /*Заявитель Имя*/
               DIVC_ZMNAME, /*Заявитель Отчество*/
               DIVC_ZPLACE /*Заявитель Место жительства*/
from DIVORCE_CERT
where DIVORCE_CERT.DIVC_ID = ]' || ID).GetClobVal();
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  procedure CompareXmls(ID    in number,
                        clob_ in clob,
                        error out varchar2,
                        res   out number) is
    cnt number;
    cursor compare(ID_ number, XML clob) is
      with xml as
       (SELECT DIVC_ID, /*ID*/
               DIVC_HE, /*Он ссылка на CUS*/
               DIVC_SHE, /*Она ссылка на CUS*/
               DIVC_HE_LNBEF, /*Фамилия его до рб*/
               DIVC_HE_LNAFT, /*Фамилия его после рб*/
               DIVC_SHE_LNBEF, /*Фамилия ее до рб*/
               DIVC_SHE_LNAFT, /*Фамилия ее после рб*/
               to_date(DIVC_DATE, 'dd.mm.yyyy hh24:mi:ss') DIVC_DATE, /*Дата документа*/
               to_date(DIVC_DT, 'dd.mm.yyyy') DIVC_DT, /*Дата прекращения брака*/
               DIVC_USR, /*Пользователь*/
               DIVC_TYPE, /*Типы основании расторжения брака*/
               to_date(DIVC_TCHD, 'dd.mm.yyyy') DIVC_TCHD, /*A-(Расторжение, совместное заявление) дата*/
               DIVC_TCHNUM, /*A-(Расторжение, совместное заявление) номер*/
               DIVC_CAN, /*B-(Расторжение,решение суда) наименование суда*/
               to_date(DIVC_CAD, 'dd.mm.yyyy') DIVC_CAD, /*B-(Расторжение,решение суда) дата*/
               DIVC_ZOSCN, /*V-(Расторжение,заявление одного из супругов) наименование суда*/
               to_date(DIVC_ZOSCD, 'dd.mm.yyyy') DIVC_ZOSCD, /*V-(Расторжение,заявление одного из супругов) дата решения суда*/
               DIVC_ZOSFIO, /*V-(Расторжение,заявление одного из супругов) о признании ФИО*/
               DIVC_ZOSCN2, /*V-(Расторжение,приговор суда) приговор суда=наименование суда */
               to_date(DIVC_ZOSCD2, 'dd.mm.yyyy') DIVC_ZOSCD2, /*V-(Расторжение,приговор суда) приговор суда=дата (от)*/
               DIVC_ZOSFIO2, /*V-(Расторжение,приговор суда) приговор суда=об осуждении ФИО*/
               DIVC_ZOSPRISON, /*V-(Расторжение,приговор суда) приговор суда=лишение свободы (количество лет)*/
               DIVC_MC_MERCER, /*Ссылка на акт о заключении брака*/
               DIVC_NUM, /*Выдано свидетельство номер*/
               DIVC_SERIA, /*Выдано свидетельство серия*/
               DIVC_ZAGS, /*Ссылка на загс*/
               DIVC_ZLNAME, /*Заявитель Фамилия*/
               DIVC_ZАNAME, /*Заявитель Имя*/
               DIVC_ZMNAME, /*Заявитель Отчество*/
               DIVC_ZPLACE /*Заявитель Место жительства*/
          FROM XMLTABLE('/ROWSET/ROW' PASSING xmltype(XML) COLUMNS DIVC_ID
                        NUMBER PATH 'DIVC_ID', /*ID*/
                        DIVC_HE NUMBER PATH 'DIVC_HE', /*Он ссылка на CUS*/
                        DIVC_SHE NUMBER PATH 'DIVC_SHE', /*Она ссылка на CUS*/
                        DIVC_HE_LNBEF VARCHAR2(200) PATH 'DIVC_HE_LNBEF', /*Фамилия его до рб*/
                        DIVC_HE_LNAFT VARCHAR2(200) PATH 'DIVC_HE_LNAFT', /*Фамилия его после рб*/
                        DIVC_SHE_LNBEF VARCHAR2(200) PATH 'DIVC_SHE_LNBEF', /*Фамилия ее до рб*/
                        DIVC_SHE_LNAFT VARCHAR2(200) PATH 'DIVC_SHE_LNAFT', /*Фамилия ее после рб*/
                        DIVC_DATE VARCHAR2(100) PATH 'DIVC_DATE', /*Дата документа*/
                        DIVC_DT VARCHAR2(100) PATH 'DIVC_DT', /*Дата прекращения брака*/
                        DIVC_USR VARCHAR2(100) PATH 'DIVC_USR', /*Пользователь*/
                        DIVC_TYPE VARCHAR2(2) PATH 'DIVC_TYPE', /*Типы основании расторжения брака*/
                        DIVC_TCHD VARCHAR2(100) PATH 'DIVC_TCHD', /*A-(Расторжение, совместное заявление) дата*/
                        DIVC_TCHNUM VARCHAR2(50) PATH 'DIVC_TCHNUM', /*A-(Расторжение, совместное заявление) номер*/
                        DIVC_CAN VARCHAR2(200) PATH 'DIVC_CAN', /*B-(Расторжение,решение суда) наименование суда*/
                        DIVC_CAD VARCHAR2(100) PATH 'DIVC_CAD', /*B-(Расторжение,решение суда) дата*/
                        DIVC_ZOSCN VARCHAR2(200) PATH 'DIVC_ZOSCN', /*V-(Расторжение,заявление одного из супругов) наименование суда*/
                        DIVC_ZOSCD VARCHAR2(100) PATH 'DIVC_ZOSCD', /*V-(Расторжение,заявление одного из супругов) дата решения суда*/
                        DIVC_ZOSFIO VARCHAR2(100) PATH 'DIVC_ZOSFIO', /*V-(Расторжение,заявление одного из супругов) о признании ФИО*/
                        DIVC_ZOSCN2 VARCHAR2(200) PATH 'DIVC_ZOSCN2', /*V-(Расторжение,приговор суда) приговор суда=наименование суда */
                        DIVC_ZOSCD2 VARCHAR2(100) PATH 'DIVC_ZOSCD2', /*V-(Расторжение,приговор суда) приговор суда=дата (от)*/
                        DIVC_ZOSFIO2 VARCHAR2(100) PATH 'DIVC_ZOSFIO2', /*V-(Расторжение,приговор суда) приговор суда=об осуждении ФИО*/
                        DIVC_ZOSPRISON NUMBER PATH 'DIVC_ZOSPRISON', /*V-(Расторжение,приговор суда) приговор суда=лишение свободы (количество лет)*/
                        DIVC_MC_MERCER NUMBER PATH 'DIVC_MC_MERCER', /*Ссылка на акт о заключении брака*/
                        DIVC_NUM VARCHAR2(100) PATH 'DIVC_NUM', /*Выдано свидетельство номер*/
                        DIVC_SERIA VARCHAR2(100) PATH 'DIVC_SERIA', /*Выдано свидетельство серия*/
                        DIVC_ZAGS NUMBER PATH 'DIVC_ZAGS', /*Ссылка на загс*/
                        DIVC_ZLNAME VARCHAR2(100) PATH 'DIVC_ZLNAME', /*Заявитель Фамилия*/
                        DIVC_ZАNAME VARCHAR2(100) PATH 'DIVC_ZАNAME', /*Заявитель Имя*/
                        DIVC_ZMNAME VARCHAR2(100) PATH 'DIVC_ZMNAME', /*Заявитель Отчество*/
                        DIVC_ZPLACE VARCHAR2(100) PATH 'DIVC_ZPLACE' /*Заявитель Место жительства*/) xmls),
      cur as
       (select DIVC_ID, /*ID*/
               DIVC_HE, /*Он ссылка на CUS*/
               DIVC_SHE, /*Она ссылка на CUS*/
               DIVC_HE_LNBEF, /*Фамилия его до рб*/
               DIVC_HE_LNAFT, /*Фамилия его после рб*/
               DIVC_SHE_LNBEF, /*Фамилия ее до рб*/
               DIVC_SHE_LNAFT, /*Фамилия ее после рб*/
               DIVC_DATE, /*Дата документа*/
               DIVC_DT, /*Дата прекращения брака*/
               DIVC_USR, /*Пользователь*/
               DIVC_TYPE, /*Типы основании расторжения брака*/
               DIVC_TCHD, /*A-(Расторжение, совместное заявление) дата*/
               DIVC_TCHNUM, /*A-(Расторжение, совместное заявление) номер*/
               DIVC_CAN, /*B-(Расторжение,решение суда) наименование суда*/
               DIVC_CAD, /*B-(Расторжение,решение суда) дата*/
               DIVC_ZOSCN, /*V-(Расторжение,заявление одного из супругов) наименование суда*/
               DIVC_ZOSCD, /*V-(Расторжение,заявление одного из супругов) дата решения суда*/
               DIVC_ZOSFIO, /*V-(Расторжение,заявление одного из супругов) о признании ФИО*/
               DIVC_ZOSCN2, /*V-(Расторжение,приговор суда) приговор суда=наименование суда */
               DIVC_ZOSCD2, /*V-(Расторжение,приговор суда) приговор суда=дата (от)*/
               DIVC_ZOSFIO2, /*V-(Расторжение,приговор суда) приговор суда=об осуждении ФИО*/
               DIVC_ZOSPRISON, /*V-(Расторжение,приговор суда) приговор суда=лишение свободы (количество лет)*/
               DIVC_MC_MERCER, /*Ссылка на акт о заключении брака*/
               DIVC_NUM, /*Выдано свидетельство номер*/
               DIVC_SERIA, /*Выдано свидетельство серия*/
               DIVC_ZAGS, /*Ссылка на загс*/
               DIVC_ZLNAME, /*Заявитель Фамилия*/
               DIVC_ZАNAME, /*Заявитель Имя*/
               DIVC_ZMNAME, /*Заявитель Отчество*/
               DIVC_ZPLACE /*Заявитель Место жительства*/
          from DIVORCE_CERT
         where DIVORCE_CERT.DIVC_ID = ID_)
      select count(*) cnt
        from (select *
                from cur
              minus
              select *
                from xml
              union all
              select *
                from xml
              minus
              select *
                from cur);
  
  begin
  
    for r in compare(ID, clob_) loop
      cnt := r.cnt;
    end loop;
  
    if cnt > 0 then
      res := 1;
    else
      res := 0;
    end if;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

end Divorce;
/
grant execute on XXI.DIVORCE to ODB;


prompt
prompt Creating package body FRREPORT
prompt ==============================
prompt
CREATE OR REPLACE PACKAGE BODY XXI.frreport IS
  ---------------------------------------------------------------------------
  -- проверить наличие роли FR_DESIGNER
  ---------------------------------------------------------------------------
  function CheckDsgnRole return boolean is
  begin
    return dbms_session.is_role_enabled('FR_DESIGNER');
  end;

  ---------------------------------------------------------------------------
  -- клонирование отчета
  ---------------------------------------------------------------------------
  function CloneReport(source_RT_ID     ap_report_cat.report_type_id%type,
                       source_R_ID      ap_report_cat.report_id%type,
                       dest_R_ID        ap_report_cat.report_id%type,
                       dest_Report_UFS  ap_report_cat.report_ufs%type,
                       dest_REPORT_NAME ap_report_cat.report_name%type)
    return varchar is
    retval          varchar2(255) := 'OK';
    l_report_exists number := 0;
    report_exists   exception;
  begin
    -- проверим если отчет dest_R_ID уже существует
    select count(*)
      into l_report_exists
      from ap_report_cat
     where report_type_id = source_RT_ID
       and report_id = dest_R_ID;
    if l_report_exists <> 0 then
      raise report_exists;
    end if;
  
    -- клонируем отчет
    insert into ap_report_cat
      (REPORT_ID,
       REPORT_TYPE_ID,
       REPORT_NAME,
       REPORT_UFS,
       COPIES,
       REPORT_VIEWER,
       REPORT_COMMENT,
       EDIT_PARAM, /*OEM_DATA,*/
       REPORT_FILE)
      select dest_R_ID,
             REPORT_TYPE_ID,
             dest_REPORT_NAME,
             dest_Report_UFS,
             COPIES,
             REPORT_VIEWER,
             REPORT_COMMENT,
             EDIT_PARAM, /*OEM_DATA,*/
             REPORT_FILE
        from ap_report_cat
       where report_type_id = source_RT_ID
         and report_id = source_R_ID;
  
    -- привязываем курсоры
    insert into AP_CURSOR_ROLE
      (REPORT_ID,
       REPORT_TYPE_ID,
       CURSOR_NUM,
       CURSOR_ID,
       USE_SORT,
       CURSOR_LOOP,
       USE_RESET,
       USE_RESUME)
      select dest_R_ID,
             REPORT_TYPE_ID,
             CURSOR_NUM,
             CURSOR_ID,
             USE_SORT,
             CURSOR_LOOP,
             USE_RESET,
             USE_RESUME
        from AP_CURSOR_ROLE
       where report_type_id = source_RT_ID
         and report_id = source_R_ID;
  
    -- параметры отчета
    insert into AP_REPORT_CAT_PARAM
      (REPORT_ID, REPORT_TYPE_ID, IPARAMNUM, CPARAMDESCR, CPARAMDEFAULT)
      select dest_R_ID,
             REPORT_TYPE_ID,
             IPARAMNUM,
             CPARAMDESCR,
             CPARAMDEFAULT
        from AP_REPORT_CAT_PARAM
       where report_type_id = source_RT_ID
         and report_id = source_R_ID;
  
    -- дадим доступ на отчет разработчику
    AP_Grant2Report(source_RT_ID, dest_R_ID, USER);
  
    commit;
  
    return retval;
  
  exception
    when report_exists then
      raise_application_error(-20001,
                              'ERROR: ошибка клонирования отчета. Отчет ID=' ||
                              dest_R_ID || ' существует');
    when others then
      raise_application_error(SQLERRM,
                              'ERROR: ошибка клонирования отчета. Отчет ID=' ||
                              dest_R_ID);
  end;

  ---------------------------------------------------------------------------
  -- права доступа на отчет по пользователю
  ---------------------------------------------------------------------------
  procedure AP_Grant2Report(l_report_type_id ap_report_cat.report_type_id%type,
                            l_report_id      ap_report_cat.report_id%type,
                            l_user_logname   VARCHAR2) is
    l_user_id      NUMBER := 0;
    is_grant_exist NUMBER := 0;
  begin
    -- получаем user_id
    select usr.iusrid
      into l_user_id
      from usr
     where usr.cusrlogname = l_user_logname;
    -- проверим наличие прав
    select count(*)
      into is_grant_exist
      from ap_user_report_cat_role
     where user_id = l_user_id
       and report_type_id = l_report_type_id
       and report_id = l_report_id;
    -- если права еще небыли разданы
    if is_grant_exist = 0 then
      insert into ap_user_report_cat_role
        (user_id, report_type_id, report_id)
      values
        (l_user_id, l_report_type_id, l_report_id);
    end if;
  end;

  ---------------------------------------------------------------------------
  -- подбор свободного ID отчета альт.печати типа report_type_id
  ---------------------------------------------------------------------------
  function AP_GetNewReportID(l_report_type_id ap_report_cat.report_type_id%type)
    return number is
    l_report_id        number;
    MAX_REPORT_TYPE_ID number := '999999999999';
  
  begin
  
    select max(REPORT_ID) + 1
      into l_report_id
      from ap_report_cat
     where report_type_id = l_report_type_id;
  
    if l_report_id > MAX_REPORT_TYPE_ID then
      l_report_id := '-1';
    end if;
  
    return l_report_id;
  
  exception
    when others then
      return - 1;
  end;

  function AddQueryToStorage(REPORTID_p     number,
                             REPORTTYPEID_p number,
                             QUERYNAME_p    varchar2,
                             REPORTFILE_p   varchar2,
                             QUERYBODY_p    varchar2) return varchar2 is
    c CLOB;
  begin
    dbms_lob.createTemporary(c, true, dbms_lob.SESSION);
    dbms_lob.write(c, length(QUERYBODY_p), 1, QUERYBODY_p);
    insert into FR_QUERIES
      (REPORTID, REPORTTYPEID, QUERYNAME, REPORTFILE, QUERYBODY)
    values
      (REPORTID_p, REPORTTYPEID_p, QUERYNAME_p, REPORTFILE_p, c);
    dbms_lob.freetemporary(c);
    return 'true';
  exception
    when others then
      dbms_lob.freetemporary(c);
      return 'false';
  end;

  function GetQueryFromStorage(QUERYID_p number, FR_QUERY_p varchar2)
    return CLOB is
    retval CLOB;
  begin
    if FR_QUERY_p = 'true' then
      select QUERYBODY
        into retval
        from FR_QUERIES
       where FR_QUERIES.queryid = QUERYID_p;
    end if;
    if FR_QUERY_p = 'false' then
      select AP_CURSOR_TYPE.cursor_text
        into retval
        from AP_CURSOR_TYPE
       where AP_CURSOR_TYPE.cursor_id = QUERYID_p;
    end if;
    return retval;
  end;

  function PutQueryToStorage(QUERYID_p   number,
                             FR_QUERY_p  varchar2,
                             QUERYBODY_p varchar2) return varchar2 is
    c CLOB;
  begin
    dbms_lob.createtemporary(c, true, dbms_lob.SESSION);
    dbms_lob.write(c, length(QUERYBODY_p), 1, QUERYBODY_p);
    if FR_QUERY_p = 'true' then
      update FR_QUERIES
         set QUERYBODY = c
       where FR_QUERIES.queryid = QUERYID_p;
    end if;
    if FR_QUERY_p = 'false' then
      update AP_CURSOR_TYPE
         set AP_CURSOR_TYPE.cursor_text = c
       where AP_CURSOR_TYPE.cursor_id = QUERYID_p;
    end if;
    dbms_lob.freetemporary(c);
    return 'true';
  exception
    when others then
      return 'false';
  end;

END;
/
grant execute on XXI.FRREPORT to ODB;
grant execute, debug on XXI.FRREPORT to PUBLIC;


prompt
prompt Creating package body LOB2TABLE
prompt ===============================
prompt
create or replace package body xxi.lob2table
is
  subtype lvarchar2 is varchar2(32767 byte);
  subtype lraw is raw(32767);
  --
  e_finished exception;
  e_failed   exception;
  --
  bom constant varchar2(1 char) := unistr('\feff');
  --
  procedure free(p_clob in out nocopy clob) as
  begin
    if p_clob is not null and dbms_lob.istemporary(p_clob) = 1
    then
      dbms_lob.freetemporary(p_clob);
    end if;
  end free;
  --
  function get_clob(p_clob clob)
    return clob
  as
    l_clob clob;
  begin
    dbms_lob.createtemporary(l_clob, true, dbms_lob.call);
    dbms_lob.copy(
      dest_lob => l_clob,
      src_lob  => p_clob,
      amount   => dbms_lob.lobmaxsize);
    return l_clob;
  end get_clob;
  --
  function get_charset_name(p_charset_name varchar2) return varchar2
  as
    l_charset_id constant number :=
      nls_charset_id(nvl(upper(trim(p_charset_name)), 'CHAR_CS'));
  begin
    if l_charset_id is null
    then
      raise_application_error(-20000, 'Invalid charset ' || p_charset_name);
    else
      return upper(nls_charset_name(l_charset_id));
    end if;
  end get_charset_name;
  --
  function to_boolean(p_number number) return boolean
  as
  begin
    return(p_number != 0);
  end to_boolean;
  --
  function get_clob(p_blob blob, p_charset_name varchar2)
    return clob
  as
    l_charset_name constant lvarchar2 := get_charset_name(p_charset_name);
    l_dest_offset  integer := 1;
    l_src_offset   integer := 1;
    l_lang_context integer := dbms_lob.default_lang_ctx;
    l_warning      integer;
    l_clob         clob;
  begin
    dbms_lob.createtemporary(l_clob, true, dbms_lob.call);
    dbms_lob.converttoclob(
      dest_lob     => l_clob,
      src_blob     => p_blob,
      amount       => dbms_lob.lobmaxsize,
      dest_offset  => l_dest_offset,
      src_offset   => l_src_offset,
      blob_csid    => nls_charset_id(l_charset_name),
      lang_context => l_lang_context,
      warning      => l_warning);
    return l_clob;
  end get_clob;
  --
  function get_clob(p_bfile bfile, p_charset_name varchar2)
    return clob
  as
    l_charset_name lvarchar2;
    l_bfile        bfile := p_bfile;
    l_dest_offset  integer := 1;
    l_src_offset   integer := 1;
    l_lang_context integer := dbms_lob.default_lang_ctx;
    l_warning      integer;
    l_clob         clob;
    procedure finally is
    begin
      if l_bfile is not null and dbms_lob.isopen(l_bfile) = 1
      then
        dbms_lob.close(l_bfile);
      end if;
    end;
  begin
    l_charset_name := get_charset_name(p_charset_name);
    dbms_lob.createtemporary(l_clob, true, dbms_lob.call);
    dbms_lob.open(l_bfile);
    dbms_lob.loadclobfromfile(
      dest_lob       => l_clob,
      src_bfile      => l_bfile,
      amount         => dbms_lob.lobmaxsize,
      dest_offset    => l_dest_offset,
      src_offset     => l_src_offset,
      bfile_csid     => nls_charset_id(l_charset_name),
      lang_context   => l_lang_context,
      warning        => l_warning);
    finally();
    return l_clob;
  exception
    when others then finally(); raise;
  end get_clob;
  --
  function get_offset(p_string varchar2) return integer as
  begin
    return case when substr(p_string, 1, 1) = bom then 2 else 1 end;
  end get_offset;
  --
  function get_offset(p_clob clob) return integer as
  begin
    return case when dbms_lob.substr(p_clob, 1) = bom then 2 else 1 end;
  end get_offset;
  --
  function in_delimiter(p_string varchar2, p_delimiter varchar2)
    return boolean
  as
  begin
    return
      case
        when p_string is null or p_delimiter is null then false
        else mod((length(p_string) -
                    nvl(length(replace(p_string, p_delimiter, null)), 0))
                  / length(p_delimiter), 2) = 1
      end;
  end in_delimiter;
  --
  function get_chunk(p_clob clob, p_separator varchar2, p_delimiter varchar2,
                     p_offset in out integer)
    return varchar2
  as
    l_offset integer := p_offset;
    l_buffer lvarchar2;
    l_amount integer;
  begin
    loop
      l_offset := dbms_lob.instr(lob_loc => p_clob, pattern => p_separator,
                                 offset => l_offset);
      l_amount := case l_offset
                    when 0 then dbms_lob.getlength(p_clob) - p_offset + 1
                    else l_offset - p_offset
                  end;
      if l_amount >= 1
      then
        begin
          dbms_lob.read(lob_loc => p_clob, amount => l_amount,
                        offset => p_offset, buffer => l_buffer);
        exception
          when value_error or dbms_lob.invalid_argval then
            raise_application_error(
              -20001, 'Row error at offset = ' || p_offset || ', ' ||
                                   'amount = ' || l_amount);
        end;
      else
        l_buffer := null;
      end if;
      if l_offset = 0
      then
        p_offset := 0;
        exit;
      elsif in_delimiter(l_buffer, p_delimiter)
      then
        l_offset := l_offset + length(p_separator);
      else
        p_offset := l_offset + length(p_separator);
        exit;
      end if;
    end loop;
    return l_buffer;
  end get_chunk;
  --
  function get_chunk(p_string varchar2, p_separator varchar2, p_delimiter varchar2,
                     p_offset in out integer)
    return varchar2
  as
    l_offset integer := p_offset;
    l_buffer lvarchar2;
  begin
    loop
      l_offset := instr(p_string, p_separator, l_offset);
      l_buffer := case l_offset
                    when 0 then substr(p_string, p_offset)
                    else substr(p_string, p_offset, l_offset - p_offset)
                  end;
      if l_offset = 0
      then
        p_offset := 0;
        exit;
      elsif in_delimiter(l_buffer, p_delimiter)
      then
        l_offset := l_offset + length(p_separator);
      else
        p_offset := l_offset + length(p_separator);
        exit;
      end if;
    end loop;
    return l_buffer;
  end get_chunk;
  --
  function get_chunk(p_clob clob, p_width integer, p_offset in out integer)
    return varchar2
  as
    l_amount integer := p_width;
    l_buffer lvarchar2;
  begin
    begin
      dbms_lob.read(lob_loc => p_clob, amount => l_amount,
                    offset => p_offset, buffer => l_buffer);
    exception
      when value_error or dbms_lob.invalid_argval then
        raise_application_error(
          -20001, 'Row error at offset = ' || p_offset || ', ' ||
                               'amount = ' || l_amount);
    end;
    p_offset := case when l_amount < p_width then 0
                     else p_offset + l_amount end;
    return l_buffer;
  end get_chunk;
  --
  function get_chunk(p_string varchar2, p_width integer, p_offset in out integer)
    return varchar2
  as
    l_buffer lvarchar2;
  begin
    l_buffer := substr(p_string, p_offset, p_width);
    p_offset := case when length(p_string) < p_offset + p_width then 0
                     else p_offset + p_width end;
    return l_buffer;
  end get_chunk;
  --
  function remove_delimiter(p_string varchar2, p_delimiter varchar2)
    return varchar2
  as
    l_delimited    boolean := false;
    l_start_offset pls_integer := 1;
    l_next_offset  pls_integer;
    l_string       lvarchar2;
  begin
    loop
      l_next_offset := instr(p_string, p_delimiter, l_start_offset);
      if l_next_offset is null or l_next_offset = 0
      then
        l_string := l_string || substr(p_string, l_start_offset);
        exit;
      else
        l_string := l_string || substr(p_string, l_start_offset,
                                       l_next_offset - l_start_offset);
        if l_delimited and
           substr(p_string, l_next_offset + length(p_delimiter),
                  length(p_delimiter)) = p_delimiter
        then
          l_string := l_string || p_delimiter;
          l_start_offset := l_next_offset + 2 * length(p_delimiter);
        else
          l_delimited := not l_delimited;
          l_start_offset := l_next_offset + length(p_delimiter);
        end if;
      end if;
    end loop;
    return l_string;
  end remove_delimiter;
  --
  function truncate_string(p_string varchar2, p_bytes pls_integer := 4000)
    return varchar2
  as
    l_string lvarchar2 := substr(p_string, 1, p_bytes);
    l_count  pls_integer := length(l_string);
  begin
    while lengthb(l_string) > p_bytes
    loop
      l_count := l_count - 1;
      l_string := substr(p_string, 1, l_count);
    end loop;
    return l_string;
  end truncate_string;
  --
  function get_column(p_chunk varchar2, p_separator varchar2,
                      p_delimiter varchar2, p_truncate boolean,
                      p_offset in out pls_integer)
    return varchar2
  as
    l_offset pls_integer := p_offset;
    l_string lvarchar2;
  begin
    if p_offset > 0
    then
      if p_separator is null
      then
        l_string := p_chunk;
        p_offset := 0;
      else
        loop
          l_offset := instr(p_chunk, p_separator, l_offset);
          if l_offset = 0
          then
            l_string := substr(p_chunk, p_offset);
            p_offset := 0;
            exit;
          else
            l_string := substr(p_chunk, p_offset, l_offset - p_offset);
            if in_delimiter(l_string, p_delimiter)
            then
              l_offset := l_offset + length(p_separator);
            else
              p_offset := l_offset + length(p_separator);
              exit;
            end if;
          end if;
        end loop;
      end if;
      l_string := remove_delimiter(l_string, p_delimiter);
      if p_truncate then l_string := truncate_string(l_string); end if;
      return l_string;
    else
      raise e_finished;
    end if;
  end get_column;
  --
  function get_column(p_chunk varchar2, p_columns lob_columns,
                      p_truncate boolean, p_column in out pls_integer)
    return varchar2
  as
    l_offset pls_integer;
    l_width  pls_integer;
    l_string lvarchar2;
  begin
    if (p_column = 1 and p_columns is null) or p_columns.exists(p_column)
    then
      if p_column = 1 and p_columns is null
      then
        l_string := p_chunk;
      else
        l_offset := nvl(p_columns(p_column).offset, 1);
        l_width := p_columns(p_column).width;
        l_string := case
                      when l_width is null then substr(p_chunk, l_offset)
                      else substr(p_chunk, l_offset, l_width)
                    end;
      end if;
      if p_truncate then l_string := truncate_string(l_string); end if;
      p_column := p_column + 1;
      return l_string;
    else
      raise e_finished;
    end if;
  end get_column;
  --
  function get_row(p_row_no number, p_chunk varchar2, p_separator varchar2,
                   p_delimiter varchar2, p_truncate number := 0)
    return lob_row
  as
    l_trunc  constant boolean := to_boolean(p_truncate);
    l_row    lob_row := lob_row(p_row_no);
    l_offset pls_integer := 1;
  begin
    begin
      l_row.column1   := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column2   := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column3   := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column4   := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column5   := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column6   := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column7   := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column8   := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column9   := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column10  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column11  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column12  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column13  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column14  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column15  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column16  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column17  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column18  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column19  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column20  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column21  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column22  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column23  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column24  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column25  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column26  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column27  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column28  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column29  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column30  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column31  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column32  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column33  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column34  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column35  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column36  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column37  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column38  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column39  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column40  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column41  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column42  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column43  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column44  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column45  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column46  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column47  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column48  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column49  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column50  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column51  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column52  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column53  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column54  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column55  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column56  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column57  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column58  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column59  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column60  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column61  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column62  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column63  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column64  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column65  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column66  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column67  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column68  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column69  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column70  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column71  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column72  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column73  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column74  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column75  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column76  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column77  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column78  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column79  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column80  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column81  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column82  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column83  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column84  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column85  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column86  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column87  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column88  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column89  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column90  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column91  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column92  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column93  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column94  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column95  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column96  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column97  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column98  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column99  := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column100 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column101 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column102 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column103 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column104 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column105 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column106 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column107 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column108 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column109 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column110 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column111 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column112 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column113 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column114 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column115 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column116 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column117 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column118 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column119 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column120 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column121 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column122 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column123 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column124 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column125 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column126 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column127 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column128 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column129 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column130 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column131 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column132 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column133 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column134 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column135 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column136 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column137 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column138 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column139 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column140 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column141 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column142 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column143 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column144 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column145 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column146 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column147 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column148 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column149 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column150 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column151 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column152 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column153 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column154 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column155 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column156 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column157 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column158 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column159 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column160 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column161 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column162 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column163 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column164 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column165 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column166 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column167 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column168 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column169 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column170 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column171 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column172 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column173 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column174 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column175 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column176 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column177 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column178 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column179 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column180 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column181 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column182 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column183 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column184 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column185 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column186 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column187 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column188 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column189 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column190 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column191 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column192 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column193 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column194 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column195 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column196 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column197 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column198 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column199 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
      l_row.column200 := get_column(p_chunk, p_separator, p_delimiter, l_trunc, l_offset);
    exception
      when e_finished then null;
      when value_error then raise_application_error(-20002, 'Column error');
    end;
    return l_row;
  end get_row;
  --
  function get_row(p_row_no number, p_chunk varchar2, p_columns lob_columns,
                   p_truncate number := 0)
    return lob_row
  as
    l_trunc   constant boolean := to_boolean(p_truncate);
    l_row     lob_row := lob_row(p_row_no);
    l_column  pls_integer := 1;
  begin
    begin
      l_row.column1   := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column2   := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column3   := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column4   := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column5   := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column6   := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column7   := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column8   := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column9   := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column10  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column11  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column12  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column13  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column14  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column15  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column16  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column17  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column18  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column19  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column20  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column21  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column22  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column23  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column24  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column25  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column26  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column27  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column28  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column29  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column30  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column31  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column32  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column33  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column34  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column35  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column36  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column37  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column38  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column39  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column40  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column41  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column42  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column43  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column44  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column45  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column46  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column47  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column48  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column49  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column50  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column51  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column52  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column53  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column54  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column55  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column56  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column57  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column58  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column59  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column60  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column61  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column62  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column63  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column64  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column65  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column66  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column67  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column68  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column69  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column70  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column71  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column72  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column73  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column74  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column75  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column76  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column77  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column78  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column79  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column80  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column81  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column82  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column83  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column84  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column85  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column86  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column87  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column88  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column89  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column90  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column91  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column92  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column93  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column94  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column95  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column96  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column97  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column98  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column99  := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column100 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column101 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column102 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column103 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column104 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column105 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column106 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column107 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column108 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column109 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column110 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column111 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column112 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column113 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column114 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column115 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column116 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column117 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column118 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column119 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column120 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column121 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column122 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column123 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column124 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column125 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column126 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column127 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column128 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column129 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column130 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column131 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column132 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column133 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column134 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column135 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column136 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column137 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column138 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column139 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column140 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column141 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column142 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column143 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column144 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column145 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column146 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column147 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column148 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column149 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column150 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column151 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column152 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column153 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column154 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column155 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column156 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column157 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column158 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column159 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column160 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column161 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column162 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column163 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column164 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column165 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column166 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column167 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column168 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column169 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column170 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column171 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column172 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column173 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column174 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column175 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column176 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column177 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column178 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column179 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column180 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column181 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column182 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column183 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column184 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column185 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column186 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column187 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column188 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column189 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column190 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column191 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column192 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column193 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column194 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column195 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column196 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column197 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column198 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column199 := get_column(p_chunk, p_columns, l_trunc, l_column);
      l_row.column200 := get_column(p_chunk, p_columns, l_trunc, l_column);
    exception
      when e_finished then null;
      when value_error then raise_application_error(-20002, 'Column error');
    end;
    return l_row;
  end get_row;
  --
  function separatedcolumns(p_blob             blob,
                            p_row_separator    varchar2,
                            p_column_separator varchar2 := null,
                            p_character_set    varchar2 := null,
                            p_delimiter        varchar2 := null,
                            p_truncate_columns number := 0)
    return lob_rows pipelined
  as
    l_row_no integer := 1;
    l_offset integer;
    l_clob   clob;
    l_chunk  lvarchar2;
    procedure finally is begin free(l_clob); end;
  begin
    if p_blob is not null and dbms_lob.getlength(p_blob) > 0
    then
      l_clob := get_clob(p_blob, p_character_set);
      l_offset := get_offset(l_clob);
      loop
        l_chunk := get_chunk(l_clob, p_row_separator, p_delimiter, l_offset);
        if l_offset != 0 or l_chunk is not null
        then
          pipe row(get_row(l_row_no, l_chunk, p_column_separator, p_delimiter,
                           p_truncate_columns));
        end if;
        exit when l_offset = 0;
        l_row_no := l_row_no + 1;
      end loop;
    end if;
    finally();
    return;
  exception
    when no_data_needed then finally(); return;
    when others then finally(); raise;
  end separatedcolumns;
  --
  function separatedcolumns2(p_blob             blob,
                             p_row_separator    varchar2,
                             p_column_separator varchar2 := null,
                             p_character_set    varchar2 := null,
                             p_delimiter        varchar2 := null,
                             p_truncate_columns number := 0)
    return lob_rows
  as
    l_rows lob_rows;
  begin
    select value(d) bulk collect into l_rows
    from   table(separatedcolumns(p_blob, p_row_separator, p_column_separator,
                                  p_character_set, p_delimiter,
                                  p_truncate_columns)) d;
    return l_rows;
  end separatedcolumns2;
  --
  function fixedcolumns(p_blob             blob,
                        p_row_separator    varchar2,
                        p_fixed_columns    lob_columns := null,
                        p_character_set    varchar2 := null,
                        p_truncate_columns number := 0)
    return lob_rows pipelined
  as
    l_row_no integer := 1;
    l_offset integer;
    l_clob   clob;
    l_chunk  lvarchar2;
    procedure finally is begin free(l_clob); end;
  begin
    if p_blob is not null and dbms_lob.getlength(p_blob) > 0
    then
      l_clob := get_clob(p_blob, p_character_set);
      l_offset := get_offset(l_clob);
      loop
        l_chunk := get_chunk(l_clob, p_row_separator, null, l_offset);
        if l_offset != 0 or l_chunk is not null
        then
          pipe row(get_row(l_row_no, l_chunk, p_fixed_columns,
                           p_truncate_columns));
        end if;
        exit when l_offset = 0;
        l_row_no := l_row_no + 1;
      end loop;
    end if;
    finally();
    return;
  exception
    when no_data_needed then finally(); return;
    when others then finally(); raise;
  end fixedcolumns;
  --
  function fixedcolumns2(p_blob             blob,
                         p_row_separator    varchar2,
                         p_fixed_columns    lob_columns := null,
                         p_character_set    varchar2 := null,
                         p_truncate_columns number := 0)
    return lob_rows
  as
    l_rows lob_rows;
  begin
    select value(d) bulk collect into l_rows
    from   table(fixedcolumns(p_blob, p_row_separator, p_fixed_columns,
                              p_character_set, p_truncate_columns)) d;
    return l_rows;
  end fixedcolumns2;
  --
  function fixedcolumns(p_blob             blob,
                        p_row_width        number,
                        p_fixed_columns    lob_columns := null,
                        p_character_set    varchar2 := null,
                        p_truncate_columns number := 0)
    return lob_rows pipelined
  as
    l_row_no integer := 1;
    l_offset integer;
    l_clob   clob;
    l_chunk  lvarchar2;
    procedure finally is begin free(l_clob); end;
  begin
    if p_blob is not null and dbms_lob.getlength(p_blob) > 0
    then
      l_clob := get_clob(p_blob, p_character_set);
      l_offset := get_offset(l_clob);
      loop
        l_chunk := get_chunk(l_clob, p_row_width, l_offset);
        if l_offset != 0 or l_chunk is not null
        then
          pipe row(get_row(l_row_no, l_chunk, p_fixed_columns,
                           p_truncate_columns));
        end if;
        exit when l_offset = 0;
        l_row_no := l_row_no + 1;
      end loop;
    end if;
    finally();
    return;
  exception
    when no_data_needed then finally(); return;
    when others then finally(); raise;
  end fixedcolumns;
  --
  function fixedcolumns2(p_blob             blob,
                         p_row_width        number,
                         p_fixed_columns    lob_columns := null,
                         p_character_set    varchar2 := null,
                         p_truncate_columns number := 0)
    return lob_rows
  as
    l_rows lob_rows;
  begin
    select value(d) bulk collect into l_rows
    from   table(fixedcolumns(p_blob, p_row_width, p_fixed_columns,
                              p_character_set, p_truncate_columns)) d;
    return l_rows;
  end fixedcolumns2;
  --
  function separatedcolumns(p_bfile            bfile,
                            p_row_separator    varchar2,
                            p_column_separator varchar2 := null,
                            p_character_set    varchar2 := null,
                            p_delimiter        varchar2 := null,
                            p_truncate_columns number := 0)
    return lob_rows pipelined
  as
    l_row_no integer := 1;
    l_offset integer;
    l_bfile  bfile := p_bfile;
    l_clob   clob;
    l_chunk  lvarchar2;
    procedure finally is begin free(l_clob); end;
  begin
    if l_bfile is not null and dbms_lob.getlength(l_bfile) > 0
    then
      dbms_lob.open(l_bfile);
      l_clob := get_clob(l_bfile, p_character_set);
      l_offset := get_offset(l_clob);
      loop
        l_chunk := get_chunk(l_clob, p_row_separator, p_delimiter, l_offset);
        if l_offset != 0 or l_chunk is not null
        then
          pipe row(get_row(l_row_no, l_chunk, p_column_separator, p_delimiter,
                           p_truncate_columns));
        end if;
        exit when l_offset = 0;
        l_row_no := l_row_no + 1;
      end loop;
    end if;
    finally();
    return;
  exception
    when no_data_needed then finally(); return;
    when others then finally(); raise;
  end separatedcolumns;
  --
  function separatedcolumns2(p_bfile            bfile,
                             p_row_separator    varchar2,
                             p_column_separator varchar2 := null,
                             p_character_set    varchar2 := null,
                             p_delimiter        varchar2 := null,
                             p_truncate_columns number := 0)
    return lob_rows
  as
    l_rows lob_rows;
  begin
    select value(d) bulk collect into l_rows
    from   table(separatedcolumns(p_bfile, p_row_separator, p_column_separator,
                                  p_character_set, p_delimiter,
                                  p_truncate_columns)) d;
    return l_rows;
  end separatedcolumns2;
  --
  function fixedcolumns(p_bfile            bfile,
                        p_row_separator    varchar2,
                        p_fixed_columns    lob_columns := null,
                        p_character_set    varchar2 := null,
                        p_truncate_columns number := 0)
    return lob_rows pipelined
  as
    l_row_no integer := 1;
    l_offset integer;
    l_clob   clob;
    l_chunk  lvarchar2;
    procedure finally is begin free(l_clob); end;
  begin
    if p_bfile is not null and dbms_lob.getlength(p_bfile) > 0
    then
      l_clob := get_clob(p_bfile, p_character_set);
      l_offset := get_offset(l_clob);
      loop
        l_chunk := get_chunk(l_clob, p_row_separator, null, l_offset);
        if l_offset != 0 or l_chunk is not null
        then
          pipe row(get_row(l_row_no, l_chunk, p_fixed_columns,
                           p_truncate_columns));
        end if;
        exit when l_offset = 0;
        l_row_no := l_row_no + 1;
      end loop;
    end if;
    finally();
    return;
  exception
    when no_data_needed then finally(); return;
    when others then finally(); raise;
  end fixedcolumns;
  --
  function fixedcolumns2(p_bfile            bfile,
                         p_row_separator    varchar2,
                         p_fixed_columns    lob_columns := null,
                         p_character_set    varchar2 := null,
                         p_truncate_columns number := 0)
    return lob_rows
  as
  l_rows lob_rows;
  begin
    select value(d) bulk collect into l_rows
    from   table(fixedcolumns(p_bfile, p_row_separator, p_fixed_columns,
                              p_character_set, p_truncate_columns)) d;
    return l_rows;
  end fixedcolumns2;
  --
  function fixedcolumns(p_bfile            bfile,
                        p_row_width        number,
                        p_fixed_columns    lob_columns := null,
                        p_character_set    varchar2 := null,
                        p_truncate_columns number := 0)
    return lob_rows pipelined
  as
    l_row_no integer := 1;
    l_offset integer;
    l_clob   clob;
    l_chunk  lvarchar2;
    procedure finally is begin free(l_clob); end;
  begin
    if p_bfile is not null and dbms_lob.getlength(p_bfile) > 0
    then
      l_clob := get_clob(p_bfile, p_character_set);
      l_offset := get_offset(l_clob);
      loop
        l_chunk := get_chunk(l_clob, p_row_width, l_offset);
        if l_offset != 0 or l_chunk is not null
        then
          pipe row(get_row(l_row_no, l_chunk, p_fixed_columns,
                           p_truncate_columns));
        end if;
        exit when l_offset = 0;
        l_row_no := l_row_no + 1;
      end loop;
    end if;
    finally();
    return;
  exception
    when no_data_needed then finally(); return;
    when others then finally(); raise;
  end fixedcolumns;
  --
  function fixedcolumns2(p_bfile            bfile,
                         p_row_width        number,
                         p_fixed_columns    lob_columns := null,
                         p_character_set    varchar2 := null,
                         p_truncate_columns number := 0)
    return lob_rows
  as
  l_rows lob_rows;
  begin
    select value(d) bulk collect into l_rows
    from   table(fixedcolumns(p_bfile, p_row_width, p_fixed_columns,
                              p_character_set, p_truncate_columns)) d;
    return l_rows;
  end fixedcolumns2;
  --
  function separatedcolumns(p_clob             clob,
                            p_row_separator    varchar2,
                            p_column_separator varchar2 := null,
                            p_delimiter        varchar2 := null,
                            p_truncate_columns number := 0)
    return lob_rows pipelined
  as
    l_row_no integer := 1;
    l_offset integer;
    l_clob   clob;
    l_chunk  lvarchar2;
    procedure finally is begin free(l_clob); end;
  begin
    if p_clob is not null and dbms_lob.getlength(p_clob) > 0
    then
      l_clob := get_clob(p_clob);
      l_offset := get_offset(l_clob);
      loop
        l_chunk := get_chunk(l_clob, p_row_separator, p_delimiter, l_offset);
        if l_offset != 0 or l_chunk is not null
        then
          pipe row(get_row(l_row_no, l_chunk, p_column_separator,
                           p_delimiter, p_truncate_columns));
        end if;
        exit when l_offset = 0;
        l_row_no := l_row_no + 1;
      end loop;
    end if;
    finally();
    return;
  exception
    when no_data_needed then finally(); return;
    when others then finally(); raise;
  end separatedcolumns;
  --
  function separatedcolumns2(p_clob             clob,
                             p_row_separator    varchar2,
                             p_column_separator varchar2 := null,
                             p_delimiter        varchar2 := null,
                             p_truncate_columns number := 0)
    return lob_rows
  as
    l_rows lob_rows;
  begin
    select value(d) bulk collect into l_rows
    from   table(separatedcolumns(p_clob, p_row_separator, p_column_separator,
                                  p_delimiter, p_truncate_columns)) d;
    return l_rows;
  end separatedcolumns2;
  --
  function fixedcolumns(p_clob             clob,
                        p_row_separator    varchar2,
                        p_fixed_columns    lob_columns := null,
                        p_truncate_columns number := 0)
    return lob_rows pipelined
  as
    l_row_no integer := 1;
    l_offset integer;
    l_clob   clob;
    l_chunk  lvarchar2;
    procedure finally is begin free(l_clob); end;
  begin
    if p_clob is not null and dbms_lob.getlength(p_clob) > 0
    then
      l_clob := get_clob(p_clob);
      l_offset := get_offset(l_clob);
      loop
        l_chunk := get_chunk(l_clob, p_row_separator, null, l_offset);
        if l_offset != 0 or l_chunk is not null
        then
          pipe row(get_row(l_row_no, l_chunk, p_fixed_columns,
                           p_truncate_columns));
        end if;
        exit when l_offset = 0;
        l_row_no := l_row_no + 1;
      end loop;
    end if;
    return;
  exception
    when no_data_needed then finally(); return;
    when others then finally(); raise;
  end fixedcolumns;
  --
  function fixedcolumns2(p_clob             clob,
                         p_row_separator    varchar2,
                         p_fixed_columns    lob_columns := null,
                         p_truncate_columns number := 0)
    return lob_rows
  as
    l_rows lob_rows;
  begin
    select value(d) bulk collect into l_rows
    from   table(fixedcolumns(p_clob, p_row_separator, p_fixed_columns,
                              p_truncate_columns)) d;
    return l_rows;
  end fixedcolumns2;
  --
  function fixedcolumns(p_clob             clob,
                        p_row_width        number,
                        p_fixed_columns    lob_columns := null,
                        p_truncate_columns number := 0)
    return lob_rows pipelined
  as
    l_row_no integer := 1;
    l_offset integer;
    l_clob   clob;
    l_chunk  lvarchar2;
    procedure finally is begin free(l_clob); end;
  begin
    if p_clob is not null and dbms_lob.getlength(p_clob) > 0
    then
      l_clob := get_clob(p_clob);
      l_offset := get_offset(l_clob);
      loop
        l_chunk := get_chunk(l_clob, p_row_width, l_offset);
        if l_offset != 0 or l_chunk is not null
        then
          pipe row(get_row(l_row_no, l_chunk, p_fixed_columns,
                           p_truncate_columns));
        end if;
        exit when l_offset = 0;
        l_row_no := l_row_no + 1;
      end loop;
    end if;
    return;
  exception
    when no_data_needed then finally(); return;
    when others then finally(); raise;
  end fixedcolumns;
  --
  function fixedcolumns2(p_clob             clob,
                         p_row_width        number,
                         p_fixed_columns    lob_columns := null,
                         p_truncate_columns number := 0)
    return lob_rows
  as
    l_rows lob_rows;
  begin
    select value(d) bulk collect into l_rows
    from   table(fixedcolumns(p_clob, p_row_width, p_fixed_columns,
                              p_truncate_columns)) d;
    return l_rows;
  end fixedcolumns2;
  --
  function separatedcolumns(p_string           varchar2,
                            p_row_separator    varchar2,
                            p_column_separator varchar2 := null,
                            p_delimiter        varchar2 := null,
                            p_truncate_columns number := 0)
    return lob_rows pipelined
  as
    l_row_no integer := 1;
    l_offset integer;
    l_chunk  lvarchar2;
  begin
    if p_string is not null
    then
      l_offset := get_offset(p_string);
      loop
        l_chunk := get_chunk(p_string, p_row_separator, p_delimiter, l_offset);
        if l_offset != 0 or l_chunk is not null
        then
          pipe row(get_row(l_row_no, l_chunk, p_column_separator,
                           p_delimiter, p_truncate_columns));
        end if;
        exit when l_offset = 0;
        l_row_no := l_row_no + 1;
      end loop;
    end if;
    return;
  exception
    when no_data_needed then return;
  end separatedcolumns;
  --
  function separatedcolumns2(p_string           varchar2,
                             p_row_separator    varchar2,
                             p_column_separator varchar2 := null,
                             p_delimiter        varchar2 := null,
                             p_truncate_columns number := 0)
    return lob_rows
  as
    l_rows   lob_rows := lob_rows();
    l_offset integer;
    l_chunk  lvarchar2;
  begin
    if p_string is not null
    then
      l_offset := get_offset(p_string);
      loop
        l_chunk := get_chunk(p_string, p_row_separator, p_delimiter, l_offset);
        if l_offset != 0 or l_chunk is not null
        then
          l_rows.extend;
          l_rows(l_rows.last) := get_row(l_rows.last, l_chunk, p_column_separator,
                                         p_delimiter, p_truncate_columns);
        end if;
        exit when l_offset = 0;
      end loop;
    end if;
    return l_rows;
  end separatedcolumns2;
  --
  function fixedcolumns(p_string           varchar2,
                        p_row_separator    varchar2,
                        p_fixed_columns    lob_columns := null,
                        p_truncate_columns number := 0)
    return lob_rows pipelined
  as
    l_row_no integer := 1;
    l_offset integer;
    l_chunk  lvarchar2;
  begin
    if p_string is not null
    then
      l_offset := get_offset(p_string);
      loop
        l_chunk := get_chunk(p_string, p_row_separator, null, l_offset);
        if l_offset != 0 or l_chunk is not null
        then
          pipe row(get_row(l_row_no, l_chunk, p_fixed_columns,
                           p_truncate_columns));
        end if;
        exit when l_offset = 0;
        l_row_no := l_row_no + 1;
      end loop;
    end if;
    return;
  exception
    when no_data_needed then return;
  end fixedcolumns;
  --
  function fixedcolumns2(p_string           varchar2,
                         p_row_separator    varchar2,
                         p_fixed_columns    lob_columns := null,
                         p_truncate_columns number := 0)
    return lob_rows
  as
    l_rows   lob_rows := lob_rows();
    l_offset integer;
    l_chunk  lvarchar2;
  begin
    if p_string is not null
    then
      l_offset := get_offset(p_string);
      loop
        l_chunk := get_chunk(p_string, p_row_separator, null, l_offset);
        if l_offset != 0 or l_chunk is not null
        then
          l_rows.extend;
          l_rows(l_rows.last) := get_row(l_rows.last, l_chunk, p_fixed_columns,
                                         p_truncate_columns);
        end if;
        exit when l_offset = 0;
      end loop;
    end if;
    return l_rows;
  end fixedcolumns2;
  --
  function fixedcolumns(p_string           varchar2,
                        p_row_width        number,
                        p_fixed_columns    lob_columns := null,
                        p_truncate_columns number := 0)
    return lob_rows pipelined
  as
    l_row_no integer := 1;
    l_offset integer;
    l_chunk  lvarchar2;
  begin
    if p_string is not null
    then
      l_offset := get_offset(p_string);
      loop
        l_chunk := get_chunk(p_string, p_row_width, l_offset);
        if l_offset != 0 or l_chunk is not null
        then
          pipe row(get_row(l_row_no, l_chunk, p_fixed_columns,
                           p_truncate_columns));
        end if;
        exit when l_offset = 0;
        l_row_no := l_row_no + 1;
      end loop;
    end if;
    return;
  exception
    when no_data_needed then return;
  end fixedcolumns;
  --
  function fixedcolumns2(p_string           varchar2,
                         p_row_width        number,
                         p_fixed_columns    lob_columns := null,
                         p_truncate_columns number := 0)
    return lob_rows
  as
    l_rows   lob_rows := lob_rows();
    l_offset integer;
    l_chunk  lvarchar2;
  begin
    if p_string is not null
    then
      l_offset := get_offset(p_string);
      loop
        l_chunk := get_chunk(p_string, p_row_width, l_offset);
        if l_offset != 0 or l_chunk is not null
        then
          l_rows.extend;
          l_rows(l_rows.last) := get_row(l_rows.last, l_chunk, p_fixed_columns,
                                         p_truncate_columns);
        end if;
        exit when l_offset = 0;
      end loop;
    end if;
    return l_rows;
  end fixedcolumns2;
  --
  function get_number_internal(p_string varchar2, p_format varchar2 := null, p_nls varchar2 := null)
    return number
  as
  begin
    return case when p_format is not null and p_nls is not null then to_number(p_string, p_format, p_nls)
                when p_format is not null then to_number(p_string, p_format)
                else to_number(p_string)
           end;
  exception
    when others then
      if sqlcode in(-6502, -1722, -1426) then raise e_failed; else raise; end if;
  end get_number_internal;
  --
  function get_number(p_string   varchar2,
                      p_on_error number := null,
                      p_format   varchar2 := null,
                      p_nls      varchar2 := null,
                      p_format2  varchar2 := null,
                      p_nls2     varchar2 := null,
                      p_format3  varchar2 := null,
                      p_nls3     varchar2 := null)
    return number
  as
  begin
    begin
      if p_format is not null then return get_number_internal(p_string, p_format, p_nls); end if;
    exception
      when e_failed then null;
    end;
    begin
      if p_format2 is not null then return get_number_internal(p_string, p_format2, p_nls2); end if;
    exception
      when e_failed then null;
    end;
    begin
      if p_format2 is not null then return get_number_internal(p_string, p_format3, p_nls3); end if;
    exception
      when e_failed then null;
    end;
    begin
      if p_format is null and p_format2 is null and p_format3 is null then return get_number_internal(p_string); end if;
    exception
      when e_failed then null;
    end;
    return p_on_error;
  end get_number;
  --
  function get_date_internal(p_string varchar2, p_format varchar2 := null, p_nls varchar2 := null)
    return date
  as
  begin
    return case when p_format is not null and p_nls is not null then to_date(p_string, p_format, p_nls)
                when p_format is not null then to_date(p_string, p_format)
                else to_date(p_string)
           end;
  exception
    when others then
      if sqlcode between -1865 and -1830 then raise e_failed; else raise; end if;
  end get_date_internal;
  --
  function get_date(p_string   varchar2,
                    p_on_error date := null,
                    p_format   varchar2 := null,
                    p_nls      varchar2 := null,
                    p_format2  varchar2 := null,
                    p_nls2     varchar2 := null,
                    p_format3  varchar2 := null,
                    p_nls3     varchar2 := null)
    return date
  as
  begin
    begin
      if p_format is not null then return get_date_internal(p_string, p_format, p_nls); end if;
    exception
      when e_failed then null;
    end;
    begin
      if p_format2 is not null then return get_date_internal(p_string, p_format2, p_nls2); end if;
    exception
      when e_failed then null;
    end;
    begin
      if p_format2 is not null then return get_date_internal(p_string, p_format3, p_nls3); end if;
    exception
      when e_failed then null;
    end;
    begin
      if p_format is null and p_format2 is null and p_format3 is null then return get_date_internal(p_string); end if;
    exception
      when e_failed then null;
    end;
    return p_on_error;
  end get_date;
  --
  function get_timestamp_internal(p_string varchar2, p_format varchar2 := null, p_nls varchar2 := null)
    return timestamp_unconstrained
  as
  begin
    return case when p_format is not null and p_nls is not null then to_timestamp(p_string, p_format, p_nls)
                when p_format is not null then to_timestamp(p_string, p_format)
                else to_timestamp(p_string)
           end;
  exception
    when others then
      if sqlcode between -1865 and -1830 or sqlcode in(-1880, -1879) then raise e_failed; else raise; end if;
  end get_timestamp_internal;

  --
  function get_timestamp(p_string   varchar2,
                         p_on_error timestamp_unconstrained := null,
                         p_format   varchar2 := null,
                         p_nls      varchar2 := null,
                         p_format2  varchar2 := null,
                         p_nls2     varchar2 := null,
                         p_format3  varchar2 := null,
                         p_nls3     varchar2 := null)
    return timestamp_unconstrained
  as
  begin
    begin
      if p_format is not null then return get_timestamp_internal(p_string, p_format, p_nls); end if;
    exception
      when e_failed then null;
    end;
    begin
      if p_format2 is not null then return get_timestamp_internal(p_string, p_format2, p_nls2); end if;
    exception
      when e_failed then null;
    end;
    begin
      if p_format2 is not null then return get_timestamp_internal(p_string, p_format3, p_nls3); end if;
    exception
      when e_failed then null;
    end;
    begin
      if p_format is null and p_format2 is null and p_format3 is null then return get_timestamp_internal(p_string); end if;
    exception
      when e_failed then null;
    end;
    return p_on_error;
  end get_timestamp;
  --
  function get_timestamp_tz_internal(p_string varchar2, p_format varchar2 := null, p_nls varchar2 := null)
    return timestamp_tz_unconstrained
  as
  begin
    return case when p_format is not null and p_nls is not null then to_timestamp_tz(p_string, p_format, p_nls)
                when p_format is not null then to_timestamp_tz(p_string, p_format)
                else to_timestamp_tz(p_string)
           end;
  exception
    when others then
      if sqlcode between -1865 and -1830 or sqlcode in(-1882, -1880, -1879, -1875, -1874, -1857)
      then raise e_failed;
      else raise;
      end if;
  end get_timestamp_tz_internal;
  --
  function get_timestamp_tz(p_string   varchar2,
                            p_on_error timestamp_tz_unconstrained := null,
                            p_format   varchar2 := null,
                            p_nls      varchar2 := null,
                            p_format2  varchar2 := null,
                            p_nls2     varchar2 := null,
                            p_format3  varchar2 := null,
                            p_nls3     varchar2 := null)
    return timestamp_tz_unconstrained
  as
  begin
    begin
      if p_format is not null then return get_timestamp_tz_internal(p_string, p_format, p_nls); end if;
    exception
      when e_failed then null;
    end;
    begin
      if p_format2 is not null then return get_timestamp_tz_internal(p_string, p_format2, p_nls2); end if;
    exception
      when e_failed then null;
    end;
    begin
      if p_format2 is not null then return get_timestamp_tz_internal(p_string, p_format3, p_nls3); end if;
    exception
      when e_failed then null;
    end;
    begin
      if p_format is null and p_format2 is null and p_format3 is null then return get_timestamp_tz_internal(p_string); end if;
    exception
      when e_failed then null;
    end;
    return p_on_error;
  end get_timestamp_tz;
  --
  function get_dsinterval(p_string   varchar2,
                          p_on_error dsinterval_unconstrained := null)
    return dsinterval_unconstrained
  as
  begin
    return to_dsinterval(p_string);
  exception
    when others then
      if sqlcode in(-1873, -1867, -1852, -1851, -1850) then return p_on_error; else raise; end if;
  end get_dsinterval;
  --
  function get_yminterval(p_string   varchar2,
                          p_on_error yminterval_unconstrained := null)
    return yminterval_unconstrained
  as
  begin
    return to_yminterval(p_string);
  exception
    when others then
      if sqlcode in(-1873, -1867, -1843) then return p_on_error; else raise; end if;
  end get_yminterval;
  --
  function string_to_hex(p_string varchar2) return varchar2 as
  begin
    return rawtohex(utl_i18n.string_to_raw(p_string, 'AL32UTF8'));
  end;
  --
  function hex_to_string(p_hex varchar2) return varchar2 as
  begin
    return utl_i18n.raw_to_char(hextoraw(p_hex), 'AL32UTF8');
  end;
end lob2table;
/
grant execute on XXI.LOB2TABLE to ODB;


prompt
prompt Creating package body LOCKS
prompt ===========================
prompt
CREATE OR REPLACE PACKAGE BODY XXI.LOCKS IS
  --РАЗДЕЛИТЕЛЬ
  DELIM VARCHAR2(2) := CHR(13) || CHR(10);

  --
  --ДОБАВИТЬ ЗАПИСЬ О БЛОКИРОВКЕ СТРОКИ ТАБЛИЦЫ
  --
  PROCEDURE LOCK_ROW_ADD(TBL_NAME IN VARCHAR2,
                         USR      IN VARCHAR2,
                         KEY      IN NUMBER,
                         ERROR    OUT VARCHAR2) IS
    PRAGMA AUTONOMOUS_TRANSACTION;
    EX NUMBER;
  BEGIN
    /*SELECT COUNT(*)
      INTO EX
      FROM LOCK_ROW LR
     WHERE LR.TABLE_NAME = TBL_NAME
       AND LR.OPER = USR
       AND LR.PK_KEY = KEY;
    
    IF EX > 0 THEN
      DELETE FROM LOCK_ROW LR
       WHERE LR.TABLE_NAME = TBL_NAME
         AND LR.OPER = USR
         AND LR.PK_KEY = KEY;
      COMMIT;
    END IF;
    
    INSERT INTO LOCK_ROW
      (TABLE_NAME, OPER, PK_KEY, LOCK_START)
    VALUES
      (TBL_NAME, USR, KEY, SYSDATE);
    COMMIT;*/
    NULL;
  EXCEPTION
    WHEN OTHERS THEN
      ERROR := SQLERRM || DELIM || DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  END;

  --
  --ВОЗВРАТ ДАННЫХ ПОЛЬЗОВАТЕЛЯ ЗАЛОЧИВШЕГО СТРОКУ
  --
  PROCEDURE LOCK_ROW_VIEW(TBL_NAME IN VARCHAR2,
                          KEY      IN NUMBER,
                          USR_INFO OUT VARCHAR2,
                          ERROR    OUT VARCHAR2) IS
    US_OP VARCHAR2(200);
    LK_DT DATE;
  BEGIN
    SELECT USR.CUSRNAME, LR.LOCK_START
      INTO US_OP, LK_DT
      FROM LOCK_ROW LR, USR
     WHERE LR.TABLE_NAME = TBL_NAME
       AND LR.PK_KEY = KEY
       AND USR.CUSRLOGNAME(+) = LR.OPER;
    USR_INFO := ' Пользователем "' || US_OP || '", Начало "' ||
                TO_CHAR(LK_DT, 'dd.mm.yyyy hh24:mi:ss') || '", прошло "' ||
                TO_CHAR(ROUND((SYSDATE - LK_DT) * 24 * 60, 2),
                        'FM99999999999999990D99999') || '" минут.';
  EXCEPTION
    WHEN OTHERS THEN
      ERROR := SQLERRM || DELIM || DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  END;

  --
  --УДАЛЕНИЕ ДАННЫХ О СТРОКЕ
  --
  PROCEDURE LOCK_ROW_DEL(TBL_NAME IN VARCHAR2,
                         USR_ID   IN VARCHAR2,
                         KEY      IN NUMBER,
                         ERROR    OUT VARCHAR2) IS
    PRAGMA AUTONOMOUS_TRANSACTION;
  BEGIN
    /*DELETE FROM LOCK_ROW LR
     WHERE LR.TABLE_NAME = TBL_NAME
       AND LR.OPER = USR_ID
       AND LR.PK_KEY = KEY;
    COMMIT;*/
    NULL;
  EXCEPTION
    WHEN OTHERS THEN
      ERROR := SQLERRM || DELIM || DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  END;

END LOCKS;
/
grant execute on XXI.LOCKS to ODB;


prompt
prompt Creating package body MERCER
prompt ============================
prompt
create or replace package body xxi.Mercer is

  sep varchar2(2) := chr(13) || chr(10);

  procedure AddMercer(error              out varchar2,
                      id                 out number,
                      MERCER_OTHER_      VARCHAR2, /*Иные сведения и служебные пометки*/
                      MERCER_DIEHE_      NUMBER, /*Свид. о смерти его пред. брака (Ссылка)*/
                      MERCER_DIESHE_     NUMBER, /*Свид. о смерти её пред. брака (Ссылка)*/
                      MERCER_SERIA_      VARCHAR2, /*Серия свид.*/
                      MERCER_NUM_        VARCHAR2, /*Номер свид.*/
                      MERCER_DSPMT_HE_   VARCHAR2, /*Тип докум. подтв. прекр. пред. брака, Он*/
                      MERCER_DIVHE_      NUMBER, /*Свидетельство расторжения брака Он (ссылка)*/
                      MERCER_DIVSHE_     NUMBER, /*Свидетельство расторжения брака Она (ссылка)*/
                      MERCER_SHEAGE_     NUMBER, /*Она лет*/
                      MERCER_HEAGE_      NUMBER, /*Он лет*/
                      MERCER_SHE_LNBAFT_ VARCHAR2, /*Фамиля ее после зб*/
                      MERCER_SHE_LNBEF_  VARCHAR2, /*Фамиля ее до зб*/
                      MERCER_HE_LNAFT_   VARCHAR2, /*Фамиля его после зб*/
                      MERCER_HE_LNBEF_   VARCHAR2, /*Фамиля его до зб*/
                      MERCER_SHE_        NUMBER, /*Она ссылка на CUS*/
                      MERCER_HE_         NUMBER, /*Он ссылка на CUS*/
                      MERCER_DSPMT_SHE_  VARCHAR2, /*Тип докум. подтв. прекр. пред. брака, Она*/
                      MC_DATE_ date) is
    ZagsId number;
  begin
    select nvl(zags_id, -1)
      into ZagsId
      from usr
     where usr.cusrlogname = user;
  
    if ZagsId = -1 then
      error := error || sep || 'У пользователя "' || user ||
               '" не проставлен номер ЗАГСа!';
      return;
    end if;
  
    insert into MC_MERCER
      (MERCER_OTHER, /*Иные сведения и служебные пометки*/
       MERCER_DIEHE, /*Свид. о смерти его пред. брака (Ссылка)*/
       MERCER_DIESHE, /*Свид. о смерти её пред. брака (Ссылка)*/
       MERCER_SERIA, /*Серия свид.*/
       MERCER_NUM, /*Номер свид.*/
       MERCER_DSPMT_HE, /*Тип докум. подтв. прекр. пред. брака, Он*/
       MERCER_DIVHE, /*Свидетельство расторжения брака Он (ссылка)*/
       MERCER_DIVSHE, /*Свидетельство расторжения брака Она (ссылка)*/
       MERCER_ZAGS, /*Наименование ЗАГСа*/
       MERCER_SHEAGE, /*Она лет*/
       MERCER_HEAGE, /*Он лет*/
       MERCER_SHE_LNBAFT, /*Фамиля ее после зб*/
       MERCER_SHE_LNBEF, /*Фамиля ее до зб*/
       MERCER_HE_LNAFT, /*Фамиля его после зб*/
       MERCER_HE_LNBEF, /*Фамиля его до зб*/
       MERCER_SHE, /*Она ссылка на CUS*/
       MERCER_HE, /*Он ссылка на CUS*/
       MERCER_DSPMT_SHE, /*Тип докум. подтв. прекр. пред. брака, Она*/
       MC_DATE)
    values
      (MERCER_OTHER_, /*Иные сведения и служебные пометки*/
       MERCER_DIEHE_, /*Свид. о смерти его пред. брака (Ссылка)*/
       MERCER_DIESHE_, /*Свид. о смерти её пред. брака (Ссылка)*/
       MERCER_SERIA_, /*Серия свид.*/
       MERCER_NUM_, /*Номер свид.*/
       decode(MERCER_DSPMT_HE_,
              'Свидетельство о расторжении брака',
              'A',
              'Свидетельство о смерти',
              'B'), /*Тип докум. подтв. прекр. пред. брака, Он*/
       MERCER_DIVHE_, /*Свидетельство расторжения брака Он (ссылка)*/
       MERCER_DIVSHE_, /*Свидетельство расторжения брака Она (ссылка)*/
       ZagsId, /*Наименование ЗАГСа*/
       MERCER_SHEAGE_, /*Она лет*/
       MERCER_HEAGE_, /*Он лет*/
       MERCER_SHE_LNBAFT_, /*Фамиля ее после зб*/
       MERCER_SHE_LNBEF_, /*Фамиля ее до зб*/
       MERCER_HE_LNAFT_, /*Фамиля его после зб*/
       MERCER_HE_LNBEF_, /*Фамиля его до зб*/
       MERCER_SHE_, /*Она ссылка на CUS*/
       MERCER_HE_, /*Он ссылка на CUS*/
       decode(MERCER_DSPMT_SHE_,
              'Свидетельство о расторжении брака',
              'A',
              'Свидетельство о смерти',
              'B'), /*Тип докум. подтв. прекр. пред. брака, Она*/
       MC_DATE_)
    returning MERCER_ID into id;
  
    update cus
       set cus.ccuslast_name = MERCER_SHE_LNBAFT_
     where cus.icusnum = MERCER_SHE_;
  
    update cus
       set cus.ccuslast_name = MERCER_HE_LNAFT_
     where cus.icusnum = MERCER_HE_;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  procedure EditMercer(error              out varchar2,
                       id                 in number,
                       MERCER_OTHER_      VARCHAR2, /*Иные сведения и служебные пометки*/
                       MERCER_DIEHE_      NUMBER, /*Свид. о смерти его пред. брака (Ссылка)*/
                       MERCER_DIESHE_     NUMBER, /*Свид. о смерти её пред. брака (Ссылка)*/
                       MERCER_SERIA_      VARCHAR2, /*Серия свид.*/
                       MERCER_NUM_        VARCHAR2, /*Номер свид.*/
                       MERCER_DSPMT_HE_   VARCHAR2, /*Тип докум. подтв. прекр. пред. брака, Он*/
                       MERCER_DIVHE_      NUMBER, /*Свидетельство расторжения брака Он (ссылка)*/
                       MERCER_DIVSHE_     NUMBER, /*Свидетельство расторжения брака Она (ссылка)*/
                       MERCER_SHEAGE_     NUMBER, /*Она лет*/
                       MERCER_HEAGE_      NUMBER, /*Он лет*/
                       MERCER_SHE_LNBAFT_ VARCHAR2, /*Фамиля ее после зб*/
                       MERCER_SHE_LNBEF_  VARCHAR2, /*Фамиля ее до зб*/
                       MERCER_HE_LNAFT_   VARCHAR2, /*Фамиля его после зб*/
                       MERCER_HE_LNBEF_   VARCHAR2, /*Фамиля его до зб*/
                       MERCER_SHE_        NUMBER, /*Она ссылка на CUS*/
                       MERCER_HE_         NUMBER, /*Он ссылка на CUS*/
                       MERCER_DSPMT_SHE_  VARCHAR2, /*Тип докум. подтв. прекр. пред. брака, Она*/
                       MC_DATE_ date) is
  begin
  
    update MC_MERCER h
       set MERCER_OTHER      = MERCER_OTHER_,
           MERCER_DIEHE      = MERCER_DIEHE_,
           MERCER_DIESHE     = MERCER_DIESHE_,
           MERCER_SERIA      = MERCER_SERIA_,
           MERCER_NUM        = MERCER_NUM_,
           MERCER_DSPMT_HE   = decode(MERCER_DSPMT_HE_,
                                      'Свидетельство о расторжении брака',
                                      'A',
                                      'Свидетельство о смерти',
                                      'B'),
           MERCER_DIVHE      = MERCER_DIVHE_,
           MERCER_DIVSHE     = MERCER_DIVSHE_,
           MERCER_SHEAGE     = MERCER_SHEAGE_,
           MERCER_HEAGE      = MERCER_HEAGE_,
           MERCER_SHE_LNBAFT = MERCER_SHE_LNBAFT_,
           MERCER_SHE_LNBEF  = MERCER_SHE_LNBEF_,
           MERCER_HE_LNAFT   = MERCER_HE_LNAFT_,
           MERCER_HE_LNBEF   = MERCER_HE_LNBEF_,
           MERCER_SHE        = MERCER_SHE_,
           MERCER_HE         = MERCER_HE_,
           MERCER_DSPMT_SHE  = decode(MERCER_DSPMT_SHE_,
                                      'Свидетельство о расторжении брака',
                                      'A',
                                      'Свидетельство о смерти',
                                      'B'),
           MC_DATE = MC_DATE_
     where h.mercer_id = id;
  
    update cus
       set cus.ccuslast_name = MERCER_SHE_LNBAFT_
     where cus.icusnum = MERCER_SHE_;
  
    update cus
       set cus.ccuslast_name = MERCER_HE_LNAFT_
     where cus.icusnum = MERCER_HE_;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  /*Возврат текущего документа, простой XML*/
  procedure RetXmls(ID number, error out varchar2, clob_ out clob) is
  begin
    clob_ := dbms_xmlgen.getxmltype(q'[
               select 
               MERCER_ID, /*ID*/
               MERCER_HE, /*Он ссылка на CUS*/
               MERCER_SHE, /*Она ссылка на CUS*/
               MERCER_HE_LNBEF, /*Фамиля его до зб*/
               MERCER_HE_LNAFT, /*Фамиля его после зб*/
               MERCER_SHE_LNBEF, /*Фамиля ее до зб*/
               MERCER_SHE_LNBAFT, /*Фамиля ее после зб*/
               MERCER_HEAGE, /*Он лет*/
               MERCER_SHEAGE, /*Она лет*/
               to_char(MERCER_DATE, 'dd.mm.yyyy hh24:mi:ss') MERCER_DATE, /*Дата заведения*/
               MERCER_USR, /*пользователь*/
               MERCER_ZAGS, /*Наименование ЗАГСа*/
               MERCER_DIVSHE, /*Свидетельство расторжения брака Она (ссылка)*/
               MERCER_DIVHE, /*Свидетельство расторжения брака Он (ссылка)*/
               MERCER_DSPMT_HE, /*Тип докум. подтв. прекр. пред. брака, Он*/
               MERCER_NUM, /*Номер свид.*/
               MERCER_SERIA, /*Серия свид.*/
               MERCER_DIESHE, /*Свид. о смерти её пред. брака (Ссылка)*/
               MERCER_DIEHE, /*Свид. о смерти его пред. брака (Ссылка)*/
               MERCER_OTHER, /*Иные сведения и служебные пометки*/
               MERCER_DSPMT_SHE /*Тип докум. подтв. прекр. пред. брака, Она*/
FROM mc_mercer 
where mc_mercer.mercer_id = ]' || ID).GetClobVal();
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  procedure CompareXmls(ID    in number,
                        clob_ in clob,
                        error out varchar2,
                        res   out number) is
    cnt number;
    cursor compare(ID_ number, XML clob) is
      with xml as
       (SELECT MERCER_ID, /*ID*/
               MERCER_HE, /*Он ссылка на CUS*/
               MERCER_SHE, /*Она ссылка на CUS*/
               MERCER_HE_LNBEF, /*Фамиля его до зб*/
               MERCER_HE_LNAFT, /*Фамиля его после зб*/
               MERCER_SHE_LNBEF, /*Фамиля ее до зб*/
               MERCER_SHE_LNBAFT, /*Фамиля ее после зб*/
               MERCER_HEAGE, /*Он лет*/
               MERCER_SHEAGE, /*Она лет*/
               to_date(MERCER_DATE, 'dd.mm.yyyy hh24:mi:ss') MERCER_DATE, /*Дата заведения*/
               MERCER_USR, /*пользователь*/
               MERCER_ZAGS, /*Наименование ЗАГСа*/
               MERCER_DIVSHE, /*Свидетельство расторжения брака Она (ссылка)*/
               MERCER_DIVHE, /*Свидетельство расторжения брака Он (ссылка)*/
               MERCER_DSPMT_HE, /*Тип докум. подтв. прекр. пред. брака, Он*/
               MERCER_NUM, /*Номер свид.*/
               MERCER_SERIA, /*Серия свид.*/
               MERCER_DIESHE, /*Свид. о смерти её пред. брака (Ссылка)*/
               MERCER_DIEHE, /*Свид. о смерти его пред. брака (Ссылка)*/
               MERCER_OTHER, /*Иные сведения и служебные пометки*/
               MERCER_DSPMT_SHE /*Тип докум. подтв. прекр. пред. брака, Она*/
          FROM XMLTABLE('/ROWSET/ROW' PASSING xmltype(XML) COLUMNS MERCER_ID
                        NUMBER PATH 'MERCER_ID', /*ID*/
                        MERCER_HE NUMBER PATH 'MERCER_HE', /*Он ссылка на CUS*/
                        MERCER_SHE NUMBER PATH 'MERCER_SHE', /*Она ссылка на CUS*/
                        MERCER_HE_LNBEF VARCHAR2(200) PATH 'MERCER_HE_LNBEF', /*Фамиля его до зб*/
                        MERCER_HE_LNAFT VARCHAR2(200) PATH 'MERCER_HE_LNAFT', /*Фамиля его после зб*/
                        MERCER_SHE_LNBEF VARCHAR2(200) PATH
                        'MERCER_SHE_LNBEF', /*Фамиля ее до зб*/
                        MERCER_SHE_LNBAFT VARCHAR2(200) PATH
                        'MERCER_SHE_LNBAFT', /*Фамиля ее после зб*/
                        MERCER_HEAGE NUMBER PATH 'MERCER_HEAGE', /*Он лет*/
                        MERCER_SHEAGE NUMBER PATH 'MERCER_SHEAGE', /*Она лет*/
                        MERCER_DATE VARCHAR2(200) PATH 'MERCER_DATE', /*Дата заведения*/
                        MERCER_USR VARCHAR2(100) PATH 'MERCER_USR', /*пользователь*/
                        MERCER_ZAGS NUMBER PATH 'MERCER_ZAGS', /*Наименование ЗАГСа*/
                        MERCER_DIVSHE NUMBER PATH 'MERCER_DIVSHE', /*Свидетельство расторжения брака Она (ссылка)*/
                        MERCER_DIVHE NUMBER PATH 'MERCER_DIVHE', /*Свидетельство расторжения брака Он (ссылка)*/
                        MERCER_DSPMT_HE VARCHAR2(1) PATH 'MERCER_DSPMT_HE', /*Тип докум. подтв. прекр. пред. брака, Он*/
                        MERCER_NUM VARCHAR2(100) PATH 'MERCER_NUM', /*Номер свид.*/
                        MERCER_SERIA VARCHAR2(100) PATH 'MERCER_SERIA', /*Серия свид.*/
                        MERCER_DIESHE NUMBER PATH 'MERCER_DIESHE', /*Свид. о смерти её пред. брака (Ссылка)*/
                        MERCER_DIEHE NUMBER PATH 'MERCER_DIEHE', /*Свид. о смерти его пред. брака (Ссылка)*/
                        MERCER_OTHER VARCHAR2(400) PATH 'MERCER_OTHER', /*Иные сведения и служебные пометки*/
                        MERCER_DSPMT_SHE VARCHAR2(1) PATH 'MERCER_DSPMT_SHE' /*Тип докум. подтв. прекр. пред. брака, Она*/) xmls),
      cur as
       (select MERCER_ID, /*ID*/
               MERCER_HE, /*Он ссылка на CUS*/
               MERCER_SHE, /*Она ссылка на CUS*/
               MERCER_HE_LNBEF, /*Фамиля его до зб*/
               MERCER_HE_LNAFT, /*Фамиля его после зб*/
               MERCER_SHE_LNBEF, /*Фамиля ее до зб*/
               MERCER_SHE_LNBAFT, /*Фамиля ее после зб*/
               MERCER_HEAGE, /*Он лет*/
               MERCER_SHEAGE, /*Она лет*/
               MERCER_DATE, /*Дата заведения*/
               MERCER_USR, /*пользователь*/
               MERCER_ZAGS, /*Наименование ЗАГСа*/
               MERCER_DIVSHE, /*Свидетельство расторжения брака Она (ссылка)*/
               MERCER_DIVHE, /*Свидетельство расторжения брака Он (ссылка)*/
               MERCER_DSPMT_HE, /*Тип докум. подтв. прекр. пред. брака, Он*/
               MERCER_NUM, /*Номер свид.*/
               MERCER_SERIA, /*Серия свид.*/
               MERCER_DIESHE, /*Свид. о смерти её пред. брака (Ссылка)*/
               MERCER_DIEHE, /*Свид. о смерти его пред. брака (Ссылка)*/
               MERCER_OTHER, /*Иные сведения и служебные пометки*/
               MERCER_DSPMT_SHE /*Тип докум. подтв. прекр. пред. брака, Она*/
          from mc_mercer
         where mc_mercer.mercer_id = ID_)
      select count(*) cnt
        from (select *
                from cur
              minus
              select *
                from xml
              union all
              select *
                from xml
              minus
              select *
                from cur);
  
  begin
  
    for r in compare(ID, clob_) loop
      cnt := r.cnt;
    end loop;
  
    if cnt > 0 then
      res := 1;
    else
      res := 0;
    end if;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

end Mercer;
/
grant execute on XXI.MERCER to ODB;


prompt
prompt Creating package body MJCUS
prompt ===========================
prompt
create or replace package body xxi.MJCUS is

  procedure writelog(error VARCHAR2, ret out VARCHAR2, mackname varchar2) is
    pragma autonomous_transaction;
  begin
    insert into LOG_ERROR
      (CURDATE_, DESC_ERROR_, PACKNAME)
    values
      (sysdate, error, mackname);
    ret := 'error';
    commit;
  end;

  procedure DelLog(PACKNAME VARCHAR2) is
    pragma autonomous_transaction;
  begin
    delete from LOG_ERROR l where l.packname = PACKNAME;
    commit;
  end;

  procedure DelCus(error out VARCHAR2, cusid_n in number) is
    pragma autonomous_transaction;
  
    brn_birth_act_cnt   number;
    PATERN_CERT_cnt     number;
    mc_mercer_cnt       number;
    DIVORCE_CERT_cnt    number;
    DEATH_CERT_cnt      number;
    UPD_NAME_cnt        number;
    UPDATE_ABH_NAME_cnt number;
    UPD_NAT_cnt         number;
    ADOPT_cnt           number;
  
  begin
    --рождение
    select count(*)
      into brn_birth_act_cnt
      from brn_birth_act g
     where g.br_act_ch = cusid_n;
  
    if brn_birth_act_cnt > 0 then
      delete from brn_birth_act where br_act_ch = cusid_n;
    end if;
  
    --уст. отцовства
    select count(*)
      into PATERN_CERT_cnt
      from PATERN_CERT j
     where j.pс_ch = cusid_n;
  
    if PATERN_CERT_cnt > 0 then
      delete from PATERN_CERT where pс_ch = cusid_n;
    end if;
  
    --закл. брака
    select count(*)
      into mc_mercer_cnt
      from mc_mercer g
     where g.MERCER_HE = cusid_n
        or g.MERCER_SHE = cusid_n;
  
    if mc_mercer_cnt > 0 then
      delete from mc_mercer
       where MERCER_HE = cusid_n
          or MERCER_SHE = cusid_n;
    end if;
  
    --растр. брака
    select count(*)
      into DIVORCE_CERT_cnt
      from DIVORCE_CERT g
     where g.DIVC_HE = cusid_n
        or g.DIVC_SHE = cusid_n;
    if DIVORCE_CERT_cnt > 0 then
      delete from DIVORCE_CERT
       where DIVC_HE = cusid_n
          or DIVC_SHE = cusid_n;
    end if;
  
    --уст. смерти
    select count(*)
      into DEATH_CERT_cnt
      from DEATH_CERT g
     where g.DC_CUS = cusid_n;
  
    if DEATH_CERT_cnt > 0 then
      delete from DEATH_CERT where DC_CUS = cusid_n;
    end if;
  
    --изменение имени
    select count(*)
      into UPD_NAME_cnt
      from update_name g
     where g.CUSID = cusid;
  
    if UPD_NAME_cnt > 0 then
      delete from update_name where CUSID = cusid_n;
    end if;
  
    --восст. абх. фамилии
    select count(*)
      into UPDATE_ABH_NAME_cnt
      from UPDATE_ABH_NAME g
     where g.CUSID = cusid;
    if UPDATE_ABH_NAME_cnt > 0 then
      delete from UPDATE_ABH_NAME where CUSID = cusid_n;
    end if;
  
    --смена национальности
    select count(*) into UPD_NAT_cnt from UPD_NAT g where g.CUSID = cusid;
  
    if UPD_NAT_cnt > 0 then
      delete from UPD_NAT where CUSID = cusid_n;
    end if;
  
    --усыновление(удочерение)
    select count(*)
      into ADOPT_cnt
      from ADOPTOIN g
     where g.CUSID_CH = cusid_n;
    if ADOPT_cnt > 0 then
      delete from ADOPTOIN where CUSID_CH = cusid_n;
    end if;
  
    --сам гражданин
    delete from cus where cus.icusnum = cusid_n;
  
    commit;
  exception
    when others then
      rollback;
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  procedure DelTempDocCit is
    pragma autonomous_transaction;
  begin
    delete from CUS_CITIZEN_TEMP;
    delete from CUS_DOCUM_TEMP;
    commit;
  end;

  procedure DelTempDocCit(CUS_CITIZEN_TEMP_ out number,
                          CUS_DOCUM_TEMP_   out number) is
    pragma autonomous_transaction;
  begin
    delete from CUS_CITIZEN_TEMP;
    delete from CUS_DOCUM_TEMP;
    commit;
    select count(*) into CUS_CITIZEN_TEMP_ from CUS_CITIZEN_TEMP;
    select count(*) into CUS_DOCUM_TEMP_ from CUS_DOCUM_TEMP;
  end;

  /*
  procedure From1cData(error out varchar2, xml in clob) is
    cursor osn_data is
      select LAST_NAME,
             FIRST_NAME,
             MIDDLE_NAME,
             BIRTH_DATE,
             SEX_CODE,
             TEL_1,
             TEL_2
        FROM XMLTABLE('/РодительскийЭлемент/ПерсональныеДанные' PASSING
                      xmltype(xml) COLUMNS last_name varchar2(500) PATH
                      '@Фамилия',
                      first_name varchar2(500) PATH '@Имя',
                      middle_name varchar2(500) PATH '@Отчество',
                      birth_date varchar2(500) PATH '@ДатаРождения',
                      sex_code varchar2(500) PATH '@КодПола',
                      tel_1 varchar2(500) PATH '@Телефон1',
                      tel_2 varchar2(500) PATH '@Телефон2');
  begin
  for r in 
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;
  */

  function ADD_CUS_CITIZEN_TEMP(COUNTRY_CODE_ VARCHAR2, /*Страна*/
                                COUNTRY_NAME_ VARCHAR2, /*Полное наименование*/
                                osn_          VARCHAR2)
  
   return varchar2 is
    pragma autonomous_transaction;
    ret      varchar2(10) := 'ok';
    cnt      number;
    RecId    number;
    pref_cnt number;
  begin
    begin
    
      select count(*)
        into pref_cnt
        from CUS_CITIZEN_TEMP
       where CUS_CITIZEN_TEMP.OSN = 'Y';
    
      if (pref_cnt > 1 and osn_ = 'Y') or (pref_cnt = 1 and osn_ = 'Y') then
        writelog('Основным может быть только одго гражданство!',
                 ret,
                 'MJCUS_CITIZEN_TEMP');
        return(ret);
      end if;
    
      insert into CUS_CITIZEN_TEMP
        (COUNTRY_CODE, COUNTRY_NAME, OSN)
      values
        (COUNTRY_CODE_, COUNTRY_NAME_, decode(osn_, 'Y', 'Y', 'N', 'N'))
      returning ID into RecId;
    
      select count(*) into cnt from CUS_CITIZEN_TEMP;
    
      commit;
    
    exception
      when others then
        rollback;
        writelog('<Count_rows> = <' || cnt || '>' || chr(13) || chr(10) ||
                 '<COUNTRY_CODE> = <' || COUNTRY_CODE_ || '>' || chr(13) ||
                 chr(10) || '<COUNTRY_NAME> = <' || COUNTRY_NAME_ || '>' ||
                 chr(13) || chr(10) || '<RecId> = <' || RecId || '>' ||
                 chr(13) || chr(10) || sqlerrm || chr(13) || chr(10) ||
                 DBMS_UTILITY.FORMAT_ERROR_BACKTRACE,
                 ret,
                 'MJCUS_CITIZEN_TEMP');
    end;
    return(ret);
  end;

  function ADD_CUS_CITIZEN(COUNTRY_CODE_ VARCHAR2, /*Страна*/
                           COUNTRY_NAME_ VARCHAR2, /*Полное наименование*/
                           CUSID         number,
                           osn_          VARCHAR2) return varchar2 is
    ret      varchar2(10) := 'ok';
    pref_cnt number;
  begin
    begin
    
      select count(*)
        into pref_cnt
        from CUS_CITIZEN
       where icusnum = CUSID
         and CUS_CITIZEN.OSN = 'Y';
    
      if (pref_cnt > 1 and osn_ = 'Y') or (pref_cnt = 1 and osn_ = 'Y') then
        writelog('Основным может быть только одго гражданство!',
                 ret,
                 'MJCUS_CITIZEN');
        return(ret);
      end if;
    
      insert into CUS_CITIZEN t
        (COUNTRY_CODE, COUNTRY_NAME, ICUSNUM, OSN)
      values
        (COUNTRY_CODE_,
         COUNTRY_NAME_,
         CUSID,
         decode(osn_, 'Y', 'Y', 'N', 'N'));
    exception
      when others then
        writelog(sqlerrm || chr(13) || chr(10) ||
                 DBMS_UTILITY.FORMAT_ERROR_BACKTRACE,
                 ret,
                 'MJCUS_CITIZEN');
    end;
    return(ret);
  end;

  function EDIT_CUS_CITIZEN(COUNTRY_CODE_ VARCHAR2, /*Страна*/
                            COUNTRY_NAME_ VARCHAR2 /*Полное наименование*/,
                            ID_           number, /*ID*/
                            osn_          VARCHAR2) return varchar2 is
    ret      varchar2(10) := 'ok';
    pref_cnt number;
  begin
    begin
    
      select count(*)
        into pref_cnt
        from CUS_CITIZEN
       where CUS_CITIZEN.ICUSNUM =
             (select CUS_CITIZEN.ICUSNUM
                from CUS_CITIZEN
               where CUS_CITIZEN.ID = ID_)
         and CUS_CITIZEN.OSN = 'Y';
    
      if (pref_cnt > 1 and osn_ = 'Y') or (pref_cnt = 1 and osn_ = 'Y') then
        writelog('Основным может быть только одно гражданство!',
                 ret,
                 'MJCUS_CITIZEN');
        return(ret);
      end if;
    
      update CUS_CITIZEN t
         set COUNTRY_CODE = COUNTRY_CODE_,
             COUNTRY_NAME = COUNTRY_NAME_,
             OSN          = decode(osn_, 'Y', 'Y', 'N', 'N')
       where t.id = ID_;
    exception
      when others then
        writelog(sqlerrm || chr(13) || chr(10) ||
                 DBMS_UTILITY.FORMAT_ERROR_BACKTRACE,
                 ret,
                 'MJCUS_CITIZEN');
    end;
    return(ret);
  end;

  function EDIT_CUS_CITIZEN_TEMP(COUNTRY_CODE_ VARCHAR2, /*Страна*/
                                 COUNTRY_NAME_ VARCHAR2 /*Полное наименование*/,
                                 ID_           number, /*ID*/
                                 osn_          VARCHAR2) return varchar2 is
    pragma autonomous_transaction;
    ret      varchar2(10) := 'ok';
    pref_cnt number;
  begin
    begin
      select count(*)
        into pref_cnt
        from CUS_CITIZEN_TEMP
       where CUS_CITIZEN_TEMP.OSN = 'Y';
    
      if (pref_cnt > 1 and osn_ = 'Y') or (pref_cnt = 1 and osn_ = 'Y') then
        writelog('Основным может быть только одно гражданство!',
                 ret,
                 'MJCUS_CITIZEN_TEMP');
        return(ret);
      end if;
      update CUS_CITIZEN_TEMP t
         set COUNTRY_CODE = COUNTRY_CODE_,
             COUNTRY_NAME = COUNTRY_NAME_,
             OSN          = decode(osn_, 'Y', 'Y', 'N', 'N')
       where t.id = ID_;
      commit;
    
    exception
      when others then
        rollback;
        writelog(sqlerrm || chr(13) || chr(10) ||
                 DBMS_UTILITY.FORMAT_ERROR_BACKTRACE,
                 ret,
                 'MJCUS_CITIZEN_TEMP');
    end;
    return(ret);
  end;

  function ADD_CUS_DOCUM_TEMP(PREF_       VARCHAR2, /*  Признак основного документа-"Y" */
                              ID_DOC_TP_  NUMBER, /*  ID типа документа */
                              DOC_NUM_    VARCHAR2, /*  Номер документа */
                              DOC_SER_    VARCHAR2, /*  Серия документа */
                              DOC_DATE_   DATE, /*  Дата выдачи */
                              DOC_AGENCY_ VARCHAR2, /*  Выдавший орган  */
                              DOC_PERIOD_ DATE, /*  Период действия */
                              DOC_SUBDIV_ VARCHAR2 /*  Код подразделение, выдавшего документ */)
    return varchar2 is
    pragma autonomous_transaction;
    ret      varchar2(10) := 'ok';
    pref_cnt number;
  begin
    begin
    
      select count(*)
        into pref_cnt
        from CUS_DOCUM_TEMP
       where CUS_DOCUM_TEMP.PREF = 'Y';
    
      if pref_cnt >= 1 and PREF_ = 'Y' then
        writelog('Основным может быть только один документ!',
                 ret,
                 'CUS_DOCUM_TEMP');
        return(ret);
      end if;
    
      insert into CUS_DOCUM_TEMP
        (PREF,
         ID_DOC_TP,
         DOC_NUM,
         DOC_SER,
         DOC_DATE,
         DOC_AGENCY,
         DOC_PERIOD,
         DOC_SUBDIV)
      values
        (PREF_,
         ID_DOC_TP_,
         DOC_NUM_,
         DOC_SER_,
         DOC_DATE_,
         DOC_AGENCY_,
         DOC_PERIOD_,
         DOC_SUBDIV_);
      commit;
    exception
      when others then
        rollback;
        writelog(sqlerrm || chr(13) || chr(10) ||
                 DBMS_UTILITY.FORMAT_ERROR_BACKTRACE,
                 ret,
                 'CUS_DOCUM_TEMP');
    end;
    return(ret);
  end;

  function ADD_CUS_DOCUM(PREF_       VARCHAR2, /*  Признак основного документа-"Y" */
                         ID_DOC_TP_  NUMBER, /*  ID типа документа */
                         DOC_NUM_    VARCHAR2, /*  Номер документа */
                         DOC_SER_    VARCHAR2, /*  Серия документа */
                         DOC_DATE_   DATE, /*  Дата выдачи */
                         DOC_AGENCY_ VARCHAR2, /*  Выдавший орган  */
                         DOC_PERIOD_ DATE, /*  Период действия */
                         DOC_SUBDIV_ VARCHAR2, /*  Код подразделение, выдавшего документ */
                         CUSID       number) return varchar2 is
    ret      varchar2(10) := 'ok';
    pref_cnt number;
  begin
    begin
    
      select count(*)
        into pref_cnt
        from CUS_DOCUM
       where icusnum = CUSID
         and CUS_DOCUM.PREF = 'Y';
    
      if pref_cnt >= 1 and PREF_ = 'Y' then
        writelog('Основным может быть только один документ!',
                 ret,
                 'CUS_DOCUM');
        return(ret);
      end if;
    
      insert into CUS_DOCUM
        (PREF,
         ID_DOC_TP,
         DOC_NUM,
         DOC_SER,
         DOC_DATE,
         DOC_AGENCY,
         DOC_PERIOD,
         DOC_SUBDIV,
         icusnum,
         SYS_GUID)
      values
        (PREF_,
         ID_DOC_TP_,
         DOC_NUM_,
         DOC_SER_,
         DOC_DATE_,
         DOC_AGENCY_,
         DOC_PERIOD_,
         DOC_SUBDIV_,
         CUSID,
         REGEXP_REPLACE(SYS_GUID(),
                        '(.{8})(.{4})(.{4})(.{4})(.{12})',
                        '\1-\2-\3-\4-\5'));
    exception
      when others then
        writelog(sqlerrm || chr(13) || chr(10) ||
                 DBMS_UTILITY.FORMAT_ERROR_BACKTRACE,
                 ret,
                 'CUS_DOCUM');
    end;
    return(ret);
  end;

  function EDIT_CUS_DOCUM_TEMP(PREF_       VARCHAR2, /*  Признак основного документа-"Y" */
                               ID_DOC_TP_  NUMBER, /*  ID типа документа */
                               DOC_NUM_    VARCHAR2, /*  Номер документа */
                               DOC_SER_    VARCHAR2, /*  Серия документа */
                               DOC_DATE_   DATE, /*  Дата выдачи */
                               DOC_AGENCY_ VARCHAR2, /*  Выдавший орган  */
                               DOC_PERIOD_ DATE, /*  Период действия */
                               DOC_SUBDIV_ VARCHAR2, /*  Код подразделение, выдавшего документ */
                               ID_DOC_     number /*ID документа*/)
    return varchar2 is
    pragma autonomous_transaction;
    ret      varchar2(10) := 'ok';
    pref_cnt number;
  begin
    begin
    
      update CUS_DOCUM_TEMP g
         set PREF       = PREF_,
             ID_DOC_TP  = ID_DOC_TP_,
             DOC_NUM    = DOC_NUM_,
             DOC_SER    = DOC_SER_,
             DOC_DATE   = DOC_DATE_,
             DOC_AGENCY = DOC_AGENCY_,
             DOC_PERIOD = DOC_PERIOD_,
             DOC_SUBDIV = DOC_SUBDIV_
       where g.id_doc = ID_DOC_;
      commit;
    exception
      when others then
        rollback;
        writelog(sqlerrm || chr(13) || chr(10) ||
                 DBMS_UTILITY.FORMAT_ERROR_BACKTRACE,
                 ret,
                 'CUS_DOCUM_TEMP');
    end;
    return(ret);
  end;

  function EDIT_CUS_DOCUM(PREF_       VARCHAR2, /*  Признак основного документа-"Y" */
                          ID_DOC_TP_  NUMBER, /*  ID типа документа */
                          DOC_NUM_    VARCHAR2, /*  Номер документа */
                          DOC_SER_    VARCHAR2, /*  Серия документа */
                          DOC_DATE_   DATE, /*  Дата выдачи */
                          DOC_AGENCY_ VARCHAR2, /*  Выдавший орган  */
                          DOC_PERIOD_ DATE, /*  Период действия */
                          DOC_SUBDIV_ VARCHAR2, /*  Код подразделение, выдавшего документ */
                          ID_DOC_     number /*ID документа*/)
    return varchar2 is
    ret varchar2(10) := 'ok';
  begin
    begin
    
      update CUS_DOCUM g
         set PREF       = PREF_,
             ID_DOC_TP  = ID_DOC_TP_,
             DOC_NUM    = DOC_NUM_,
             DOC_SER    = DOC_SER_,
             DOC_DATE   = DOC_DATE_,
             DOC_AGENCY = DOC_AGENCY_,
             DOC_PERIOD = DOC_PERIOD_,
             DOC_SUBDIV = DOC_SUBDIV_
       where g.id_doc = ID_DOC_;
    
    exception
      when others then
        writelog(sqlerrm || chr(13) || chr(10) ||
                 DBMS_UTILITY.FORMAT_ERROR_BACKTRACE,
                 ret,
                 'CUS_DOCUM');
    end;
    return(ret);
  end;

  function ADD_CUS(DCUSBIRTHDAY_    DATE, /*  Дата рождения (для физ.лиц) */
                   CCUSLAST_NAME_   VARCHAR2, /*  Фамилия */
                   CCUSFIRST_NAME_  VARCHAR2, /*  Имя */
                   CCUSMIDDLE_NAME_ VARCHAR2, /*  Отчество  */
                   CCUSNATIONALITY_ VARCHAR2, /*  Национальность  */
                   CCUSSEX_         VARCHAR2, /*  Пол */
                   CCUSPLACE_BIRTH_ VARCHAR2, /*  Место рождения  */
                   ICUSOTD_         VARCHAR2, /*  ссылка на номер отделения */
                   CCUS_OK_SM_      number, /*  Код страны рождения */
                   COUNTRY_         number, /*  Страна  */
                   AREA_            VARCHAR2, /*  Район */
                   CITY_            VARCHAR2, /*  Город */
                   PUNCT_NAME_      VARCHAR2, /*  Нас. пункт  */
                   INFR_NAME_       VARCHAR2, /*  Инфраструктура  */
                   DOM_             VARCHAR2, /*  Дом */
                   KORP_            VARCHAR2, /*  Корпус  */
                   KV_              VARCHAR2, /*  Квартира  */
                   ID               out number,
                   AB_FIRST_NAME_   VARCHAR2,
                   AB_MIDDLE_NAME_  VARCHAR2,
                   AB_LAST_NAME_    VARCHAR2,
                   AB_PLACE_BIRTH_  VARCHAR2) return varchar2 is
    ret             varchar2(10) := 'ok';
    CCUSNAME_       varchar2(500);
    CCUSNAME_SH_    varchar2(500);
    otd             number;
    CUS_NUM         number;
    adrid           number;
    natid           number;
    TempDocsCnt     number;
    TemзCitizenCnt number;
    Pknm            varchar2(100) := 'ADD_CUS';
    punctcnt        number;
  begin
    begin
    
      if PUNCT_NAME_ is not null then
        select count(*)
          into punctcnt
          from nas_punkt t
         where t.name = PUNCT_NAME_;
        if punctcnt = 0 then
          writelog('Населенный пункт должен быть из справочника!',
                   ret,
                   Pknm);
        end if;
      end if;
    
      if DCUSBIRTHDAY_ is null then
        writelog('Укажите дату рождения', ret, Pknm);
      end if;
      if CCUSLAST_NAME_ is null then
        writelog('Не указана Фамилия', ret, Pknm);
      end if;
      if CCUSFIRST_NAME_ is null then
        writelog('Не указано имя', ret, Pknm);
      end if;
      if CCUSMIDDLE_NAME_ is null then
        writelog('Не указано отчество', ret, Pknm);
      end if;
      if CCUSNATIONALITY_ is null then
        writelog('Укажите национальность', ret, Pknm);
      end if;
      if CCUSSEX_ is null then
        writelog('Укажите пол', ret, Pknm);
      end if;
      if CCUSPLACE_BIRTH_ is null then
        writelog('Не указано место рождения',
                 ret,
                 Pknm);
      end if;
      if ICUSOTD_ is null then
        writelog('Не указано подразделение',
                 ret,
                 Pknm);
      end if;
      if CCUS_OK_SM_ is null then
        writelog('Укажите страну рождения', ret, Pknm);
      end if;
      if COUNTRY_ is null then
        writelog('Укажите страну в адресе', ret, Pknm);
      end if;
      if AREA_ is null then
        writelog('Укажите район в адресе', ret, Pknm);
      end if;
    
      /*Проверка кол-ва документов во временной таблице*/
      select count(*) into TempDocsCnt from CUS_DOCUM_TEMP;
      if TempDocsCnt = 0 then
        writelog('Не указаны паспортные данные, запись невозможна...',
                 ret,
                 Pknm);
      end if;
    
      /*Проверка кол-ва документов во временной таблице*/
      select count(*)
        into TempDocsCnt
        from CUS_DOCUM_TEMP t
       where t.pref = 'Y';
      if TempDocsCnt = 0 then
        writelog('Один из паспортов должен быть основным, запись невозможна...',
                 ret,
                 Pknm);
      end if;
    
      /*Проверка количества гражданств во временной таблице*/
      select count(*) into TemзCitizenCnt from CUS_CITIZEN_TEMP;
      if TemзCitizenCnt = 0 then
        writelog('Не указано гражданство, запись невозможна...',
                 ret,
                 Pknm);
      end if;
    
      /*Проверка количества гражданств во временной таблице*/
      select count(*)
        into TemзCitizenCnt
        from CUS_CITIZEN_TEMP t
       where t.osn = 'Y';
      if TemзCitizenCnt = 0 then
        writelog('Одно из гражданств должно быть основным, запись невозможна...',
                 ret,
                 Pknm);
      end if;
    
      /*Если влидация не прошла*/
      if ret != 'ok' then
        return(ret);
      end if;
    
      select t.id
        into natid
        from nationality t
       where name = CCUSNATIONALITY_;
    
      /*CUS*/
      if ICUSOTD_ is not null then
        select t.iotdnum into otd from otd t where t.cotdname = ICUSOTD_;
      end if;
    
      CCUSNAME_ := CCUSLAST_NAME_ || ' ' || CCUSFIRST_NAME_ || ' ' ||
                   CCUSMIDDLE_NAME_;
    
      CCUSNAME_SH_ := CCUSLAST_NAME_ || ' ' || case
                        when substr(CCUSFIRST_NAME_, 1, 1) is null then
                         ''
                        else
                         substr(CCUSFIRST_NAME_, 1, 1) || '.'
                      end || case
                        when substr(CCUSMIDDLE_NAME_, 1, 1) is null then
                         ''
                        else
                         substr(CCUSMIDDLE_NAME_, 1, 1) || '.'
                      end;
    
      insert into CUS
        (DCUSBIRTHDAY, /*  Дата рождения (для физ.лиц) */
         CCUSLAST_NAME, /*  Фамилия */
         CCUSFIRST_NAME, /*  Имя */
         CCUSMIDDLE_NAME, /*  Отчество  */
         CCUSNATIONALITY, /*  Национальность  */
         CCUSSEX, /*  Пол */
         CCUSPLACE_BIRTH, /*  Место рождения  */
         ICUSOTD, /*  ссылка на номер отделения */
         CCUS_OK_SM, /*  Код страны рождения */
         CCUSNAME, /*  Название  */
         CCUSNAME_SH /*  Краткое наименование клиента  */,
         AB_FIRST_NAME,
         AB_MIDDLE_NAME,
         AB_LAST_NAME,
         AB_PLACE_BIRTH)
      values
        (DCUSBIRTHDAY_, /*  Дата рождения (для физ.лиц) */
         CCUSLAST_NAME_, /*  Фамилия */
         CCUSFIRST_NAME_, /*  Имя */
         CCUSMIDDLE_NAME_, /*  Отчество  */
         natid, /*  Национальность  */
         decode(CCUSSEX_, 'Мужской', 1, 'Женский', 2), /*  Пол */
         CCUSPLACE_BIRTH_, /*  Место рождения  */
         nvl(otd, 0), /*  ссылка на номер отделения */
         CCUS_OK_SM_, /*  Код страны рождения */
         CCUSNAME_, /*  Название  */
         CCUSNAME_SH_ /*  Краткое наименование клиента  */,
         AB_FIRST_NAME_,
         AB_MIDDLE_NAME_,
         AB_LAST_NAME_,
         AB_PLACE_BIRTH_)
      returning ICUSNUM into CUS_NUM;
      /******************/
      /*CUS_ADDR*/
      insert into CUS_ADDR
        (ICUSNUM, /*  Идентификатор контрагента */
         ADDR_TYPE, /*  Тип адреса  */
         COUNTRY, /*  Страна  */
         AREA, /*  Район */
         CITY, /*  Город */
         PUNCT_NAME, /*  Нас. пункт  */
         INFR_NAME, /*  Инфраструктура  */
         DOM, /*  Дом */
         KORP, /*  Корпус  */
         KV /*  Квартира  */)
      values
        (CUS_NUM, /*  Идентификатор контрагента */
         0, /*  Тип адреса  */
         COUNTRY_, /*  Страна  */
         AREA_, /*  Район */
         CITY_, /*  Город */
         PUNCT_NAME_, /*  Нас. пункт  */
         INFR_NAME_, /*  Инфраструктура  */
         DOM_, /*  Дом */
         KORP_, /*  Корпус  */
         KV_ /*  Квартира  */)
      returning ID_ADDR into adrid;
      /************/
    
      /*CUS_DOCUM*/
      for r in (select PREF,
                       ID_DOC_TP,
                       DOC_NUM,
                       DOC_SER,
                       DOC_DATE,
                       DOC_AGENCY,
                       DOC_PERIOD,
                       DOC_SUBDIV,
                       nvl(GUID,
                           REGEXP_REPLACE(SYS_GUID(),
                                          '(.{8})(.{4})(.{4})(.{4})(.{12})',
                                          '\1-\2-\3-\4-\5')) GUID
                  from CUS_DOCUM_TEMP) loop
        insert into CUS_DOCUM
          (ICUSNUM,
           PREF,
           ID_DOC_TP,
           DOC_NUM,
           DOC_SER,
           DOC_DATE,
           DOC_AGENCY,
           DOC_PERIOD,
           DOC_SUBDIV,
           SYS_GUID)
        values
          (CUS_NUM,
           r.PREF,
           r.ID_DOC_TP,
           r.DOC_NUM,
           r.DOC_SER,
           r.DOC_DATE,
           r.DOC_AGENCY,
           r.DOC_PERIOD,
           r.DOC_SUBDIV,
           r.GUID);
      end loop;
    
      /*******************/
    
      /*CUS_CITIZEN*/
      for r in (select * from CUS_CITIZEN_TEMP) loop
        insert into CUS_CITIZEN
          (country_code, country_name, icusnum, osn)
        values
          (r.country_code, r.country_name, CUS_NUM, r.osn);
      end loop;
      /*******************/
      ID := CUS_NUM;
    exception
      when others then
        writelog(sqlerrm || chr(13) || chr(10) ||
                 DBMS_UTILITY.FORMAT_ERROR_BACKTRACE,
                 ret,
                 Pknm);
    end;
    return(ret);
  end;

  function UPDATE_CUS(DCUSBIRTHDAY_    DATE, /*  Дата рождения (для физ.лиц) */
                      CCUSLAST_NAME_   VARCHAR2, /*  Фамилия */
                      CCUSFIRST_NAME_  VARCHAR2, /*  Имя */
                      CCUSMIDDLE_NAME_ VARCHAR2, /*  Отчество  */
                      CCUSNATIONALITY_ VARCHAR2, /*  Национальность  */
                      CCUSSEX_         VARCHAR2, /*  Пол */
                      CCUSPLACE_BIRTH_ VARCHAR2, /*  Место рождения  */
                      ICUSOTD_         VARCHAR2, /*  ссылка на номер отделения */
                      CCUS_OK_SM_      number, /*  Код страны рождения */
                      COUNTRY_         number, /*  Страна  */
                      AREA_            VARCHAR2, /*  Район */
                      CITY_            VARCHAR2, /*  Город */
                      PUNCT_NAME_      VARCHAR2, /*  Нас. пункт  */
                      INFR_NAME_       VARCHAR2, /*  Инфраструктура  */
                      DOM_             VARCHAR2, /*  Дом */
                      KORP_            VARCHAR2, /*  Корпус  */
                      KV_              VARCHAR2, /*  Квартира  */
                      CUSID            number,
                      AB_FIRST_NAME_   VARCHAR2,
                      AB_MIDDLE_NAME_  VARCHAR2,
                      AB_LAST_NAME_    VARCHAR2,
                      AB_PLACE_BIRTH_  VARCHAR2) return varchar2 is
    ret          varchar2(10) := 'ok';
    CCUSNAME_    varchar2(500);
    CCUSNAME_SH_ varchar2(500);
    otd          number;
    natid        number;
    Pknm         varchar2(100) := 'EDIT_CUS';
    DocsCnt      number;
    CitizenCnt   number;
    punctcnt     number;
  begin
    begin
      if PUNCT_NAME_ is not null then
        select count(*)
          into punctcnt
          from nas_punkt t
         where t.name = PUNCT_NAME_;
        if punctcnt = 0 then
          writelog('Населенный пункт должен быть из справочника!',
                   ret,
                   Pknm);
        end if;
      end if;
    
      if DCUSBIRTHDAY_ is null then
        writelog('Укажите дату рождения', ret, Pknm);
      end if;
      if CCUSLAST_NAME_ is null then
        writelog('Не указана Фамилия', ret, Pknm);
      end if;
      if CCUSFIRST_NAME_ is null then
        writelog('Не указано имя', ret, Pknm);
      end if;
      if CCUSMIDDLE_NAME_ is null then
        writelog('Не указано отчество', ret, Pknm);
      end if;
      if CCUSNATIONALITY_ is null then
        writelog('Укажите национальность', ret, Pknm);
      end if;
      if CCUSSEX_ is null then
        writelog('Укажите пол', ret, Pknm);
      end if;
      if CCUSPLACE_BIRTH_ is null then
        writelog('Не указано место рождения',
                 ret,
                 Pknm);
      end if;
      if ICUSOTD_ is null then
        writelog('Не указано подразделение',
                 ret,
                 Pknm);
      end if;
      if CCUS_OK_SM_ is null then
        writelog('Укажите страну рождения', ret, Pknm);
      end if;
      if COUNTRY_ is null then
        writelog('Укажите страну в адресе', ret, Pknm);
      end if;
      if AREA_ is null then
        writelog('Укажите район в адресе', ret, Pknm);
      end if;
    
      /*Проверка кол-ва документов*/
      /*      select count(*)
        into DocsCnt
        from CUS_DOCUM t
       where t.icusnum = CUSID;
      if DocsCnt = 0 then
        writelog('Не указаны паспортные данные, запись невозможна...',
                 ret,
                 Pknm);
      end if;*/
    
      /*Проверка кол-ва документов*/
      /*      select count(*)
        into DocsCnt
        from CUS_DOCUM t
       where t.pref = 'Y'
         and t.icusnum = CUSID;
      if DocsCnt = 0 then
        writelog('Один из паспортов должен быть основным, запись невозможна...',
                 ret,
                 Pknm);
      end if;*/
    
      /*Проверка количества гражданств*/
      select count(*)
        into CitizenCnt
        from CUS_CITIZEN t
       where t.icusnum = CUSID;
      if CitizenCnt = 0 then
        writelog('Не указано гражданство, запись невозможна...',
                 ret,
                 Pknm);
      end if;
    
      /*Проверка количества гражданств*/
      select count(*)
        into CitizenCnt
        from CUS_CITIZEN t
       where t.osn = 'Y'
         and t.icusnum = CUSID;
      if CitizenCnt = 0 then
        writelog('Одно из гражданств должно быть основным, запись невозможна...',
                 ret,
                 Pknm);
      end if;
    
      /*Если влидация не прошла*/
      if ret != 'ok' then
        return(ret);
      end if;
    
      select t.id
        into natid
        from nationality t
       where name = CCUSNATIONALITY_;
      /*CUS*/
      if ICUSOTD_ is not null then
        select t.iotdnum into otd from otd t where t.cotdname = ICUSOTD_;
      end if;
    
      CCUSNAME_ := CCUSLAST_NAME_ || ' ' || CCUSFIRST_NAME_ || ' ' ||
                   CCUSMIDDLE_NAME_;
    
      CCUSNAME_SH_ := CCUSLAST_NAME_ || ' ' || case
                        when substr(CCUSFIRST_NAME_, 1, 1) is null then
                         ''
                        else
                         substr(CCUSFIRST_NAME_, 1, 1) || '.'
                      end || case
                        when substr(CCUSMIDDLE_NAME_, 1, 1) is null then
                         ''
                        else
                         substr(CCUSMIDDLE_NAME_, 1, 1) || '.'
                      end;
      /*CUS*/
      update CUS
         set DCUSBIRTHDAY    = DCUSBIRTHDAY_, /*  Дата рождения (для физ.лиц) */
             CCUSLAST_NAME   = CCUSLAST_NAME_, /*  Фамилия */
             CCUSFIRST_NAME  = CCUSFIRST_NAME_, /*  Имя */
             CCUSMIDDLE_NAME = CCUSMIDDLE_NAME_, /*  Отчество  */
             CCUSNATIONALITY = natid, /*  Национальность  */
             CCUSSEX         = decode(CCUSSEX_,
                                      'Мужской',
                                      1,
                                      'Женский',
                                      2), /*  Пол */
             CCUSPLACE_BIRTH = CCUSPLACE_BIRTH_, /*  Место рождения  */
             ICUSOTD         = nvl(otd, 0), /*  ссылка на номер отделения */
             CCUS_OK_SM      = CCUS_OK_SM_, /*  Код страны рождения */
             CCUSNAME        = CCUSNAME_, /*  Название  */
             CCUSNAME_SH     = CCUSNAME_SH_ /*  Краткое наименование клиента  */,
             AB_FIRST_NAME   = AB_FIRST_NAME_,
             AB_MIDDLE_NAME  = AB_MIDDLE_NAME_,
             AB_LAST_NAME    = AB_LAST_NAME_,
             AB_PLACE_BIRTH  = AB_PLACE_BIRTH_
      
       where CUS.ICUSNUM = CUSID;
      /******************/
      /*CUS_ADDR*/
      update CUS_ADDR
         set COUNTRY    = COUNTRY_, /*  Страна  */
             AREA       = AREA_, /*  Район */
             CITY       = CITY_, /*  Город */
             PUNCT_NAME = PUNCT_NAME_, /*  Нас. пункт  */
             INFR_NAME  = INFR_NAME_, /*  Инфраструктура  */
             DOM        = DOM_, /*  Дом */
             KORP       = KORP_, /*  Корпус  */
             KV         = KV_ /*  Квартира  */
       where CUS_ADDR.ICUSNUM = CUSID;
      /************/
    
    exception
      when others then
        writelog(sqlerrm || chr(13) || chr(10) ||
                 DBMS_UTILITY.FORMAT_ERROR_BACKTRACE,
                 ret,
                 Pknm);
    end;
    return(ret);
  end;

  procedure RetXmls(cusid       number,
                    error       out varchar2,
                    cus         out clob,
                    cus_addr    out clob,
                    cus_citizen out clob,
                    cus_docum   out clob) is
  begin
  
    cus         := dbms_xmlgen.getxmltype(q'[
select 
ICUSNUM, /*Уникальный номер клиента*/
to_char(DCUSOPEN,'dd.mm.yyyy hh24:mi:ss') DCUSOPEN, /*Дата заведения*/
to_char(DCUSEDIT,'dd.mm.yyyy') DCUSEDIT, /*Дата окончания срока действия документов*/
CCUSIDOPEN, /*Кто завел*/
CCUSNAME, /*Название*/
CCUSCOUNTRY1, /*Страна местонахождения/Гражданство*/
CCUSNAME_SH, /*Краткое наименование клиента*/
to_char(DCUSBIRTHDAY,'dd.mm.yyyy') DCUSBIRTHDAY, /*Дата рождения (для физ.лиц)*/
CCUSLAST_NAME, /*Фамилия*/
CCUSFIRST_NAME, /*Имя*/
CCUSMIDDLE_NAME, /*Отчество*/
CCUSSEX, /*Пол*/
CCUSPLACE_BIRTH, /*Место рождения*/
ICUSOTD, /*ссылка на номер отделения*/
CCUS_OK_SM, /*Код страны рождения*/
CCUSNATIONALITY /*Национальность*/,
AB_FIRST_NAME,
AB_MIDDLE_NAME,
AB_LAST_NAME,
AB_PLACE_BIRTH
FROM CUS where cus.icusnum = ]' || cusid).GetClobVal();
    cus_addr    := dbms_xmlgen.getxmltype(q'[select * FROM cus_addr where icusnum = ]' || cusid).GetClobVal();
    cus_citizen := dbms_xmlgen.getxmltype(q'[select * FROM cus_citizen where icusnum = ]' || cusid).GetClobVal();
    cus_docum   := dbms_xmlgen.getxmltype(q'[select DOC_SUBDIV, /*Код подразделение, выдавшего документ*/
to_char(DOC_PERIOD,'dd.mm.yyyy')DOC_PERIOD, /*Период действия*/
DOC_AGENCY, /*Выдавший орган*/
to_char(DOC_DATE,'dd.mm.yyyy')DOC_DATE, /*Дата выдачи*/
DOC_SER, /*Серия документа*/
DOC_NUM, /*Номер документа*/
ID_DOC_TP, /*ID типа документа*/
PREF, /*Признак основного документа-"Y"*/
ICUSNUM, /*Идентификатор контрагента*/
ID_DOC /*ID документа*/
 FROM cus_docum where icusnum = ]' || cusid).GetClobVal();
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  procedure CompareXmls(cusid       in number,
                        cus         in clob,
                        cus_addr    in clob,
                        cus_citizen in clob,
                        cus_docum   in clob,
                        error       out varchar2,
                        res         out number) is
  
    cus_cnt         number;
    cus_addr_cnt    number;
    cus_citizen_cnt number;
    cus_docum_cnt   number;
  
    cursor cus_compare(CUSNUM number, XML clob) is
      with xmlcus as
       (select CCUSNATIONALITY, /*Национальность*/
               to_number(CCUS_OK_SM), /*Код страны рождения*/
               ICUSOTD, /*Ссылка на номер отделения*/
               CCUSPLACE_BIRTH, /*Место рождения*/
               CCUSSEX, /*Пол*/
               CCUSMIDDLE_NAME, /*Отчество*/
               CCUSFIRST_NAME, /*Имя*/
               CCUSLAST_NAME, /*Фамилия*/
               TO_DATE(DCUSBIRTHDAY, 'DD.MM.YYYY') DCUSBIRTHDAY, /*Дата рождения (для физ.лиц)*/
               CCUSNAME_SH, /*Краткое наименование клиента*/
               CCUSCOUNTRY1, /*Страна местонахождения/Гражданство*/
               CCUSNAME, /*Название*/
               CCUSIDOPEN, /*Кто завел*/
               TO_DATE(DCUSEDIT, 'DD.MM.YYYY') DCUSEDIT, /*Дата окончания срока действия документов*/
               TO_DATE(DCUSOPEN, 'DD.MM.YYYY HH24:MI:SS') DCUSOPEN, /*Дата заведения*/
               ICUSNUM,
               AB_FIRST_NAME,
               AB_MIDDLE_NAME,
               AB_LAST_NAME,
               AB_PLACE_BIRTH
          FROM XMLTABLE('/ROWSET/ROW' PASSING xmltype(XML) COLUMNS ICUSNUM
                        NUMBER PATH 'ICUSNUM', /*Уникальный номер клиента*/
                        DCUSOPEN VARCHAR2(250) PATH 'DCUSOPEN', /*Дата заведения*/
                        DCUSEDIT VARCHAR2(250) PATH 'DCUSEDIT', /*Дата окончания срока действия документов*/
                        CCUSIDOPEN VARCHAR2(30) PATH 'CCUSIDOPEN', /*Кто завел*/
                        CCUSNAME VARCHAR2(500) PATH 'CCUSNAME', /*Название*/
                        CCUSCOUNTRY1 VARCHAR2(3) PATH 'CCUSCOUNTRY1', /*Страна местонахождения/Гражданство*/
                        CCUSNAME_SH VARCHAR2(250) PATH 'CCUSNAME_SH', /*Краткое наименование клиента*/
                        DCUSBIRTHDAY VARCHAR2(250) PATH 'DCUSBIRTHDAY', /*Дата рождения (для физ.лиц)*/
                        CCUSLAST_NAME VARCHAR2(100) PATH 'CCUSLAST_NAME', /*Фамилия*/
                        CCUSFIRST_NAME VARCHAR2(30) PATH 'CCUSFIRST_NAME', /*Имя*/
                        CCUSMIDDLE_NAME VARCHAR2(30) PATH 'CCUSMIDDLE_NAME', /*Отчество*/
                        
                        AB_FIRST_NAME VARCHAR2(100) PATH 'AB_FIRST_NAME', /*Дата рождения (для физ.лиц)*/
                        AB_MIDDLE_NAME VARCHAR2(100) PATH 'AB_MIDDLE_NAME', /*Фамилия*/
                        AB_LAST_NAME VARCHAR2(100) PATH 'AB_LAST_NAME', /*Имя*/
                        AB_PLACE_BIRTH VARCHAR2(100) PATH 'AB_PLACE_BIRTH', /*Отчество*/
                        
                        CCUSSEX NUMBER PATH 'CCUSSEX', /*Пол*/
                        CCUSPLACE_BIRTH VARCHAR2(250) PATH 'CCUSPLACE_BIRTH', /*Место рождения*/
                        ICUSOTD NUMBER PATH 'ICUSOTD', /*ссылка на номер отделения*/
                        CCUS_OK_SM VARCHAR2(100) PATH 'CCUS_OK_SM', /*Код страны рождения*/
                        CCUSNATIONALITY NUMBER PATH 'CCUSNATIONALITY' /*Национальность*/) xml),
      curcus as
       (select CCUSNATIONALITY, /*Национальность*/
               CCUS_OK_SM, /*Код страны рождения*/
               ICUSOTD, /*ссылка на номер отделения*/
               CCUSPLACE_BIRTH, /*Место рождения*/
               CCUSSEX, /*Пол*/
               CCUSMIDDLE_NAME, /*Отчество*/
               CCUSFIRST_NAME, /*Имя*/
               CCUSLAST_NAME, /*Фамилия*/
               DCUSBIRTHDAY, /*Дата рождения (для физ.лиц)*/
               CCUSNAME_SH, /*Краткое наименование клиента*/
               CCUSCOUNTRY1, /*Страна местонахождения/Гражданство*/
               CCUSNAME, /*Название*/
               CCUSIDOPEN, /*Кто завел*/
               DCUSEDIT, /*Дата окончания срока действия документов*/
               DCUSOPEN, /*Дата заведения*/
               ICUSNUM /*Уникальный номер клиента*/,
               AB_FIRST_NAME,
               AB_MIDDLE_NAME,
               AB_LAST_NAME,
               AB_PLACE_BIRTH
          from cus
         where cus.icusnum = CUSNUM)
      select count(*) cnt
        from (select *
                from curcus
              minus
              select *
                from xmlcus
              union all
              select *
                from xmlcus
              minus
              select *
                from curcus);
  
    cursor cus_addr_compare(CUSNUM number, XML clob) is
      with xml_cus_addr as
       (SELECT STROY_TYPE, /*Признак строения*/
               ADDRESS_INLINE, /*Адрес для нерезидентов(не по КЛАДР) в одну строку.*/
               OKSM_CODE, /*Код ОКСМ территории*/
               PORCH, /*Подъезд*/
               OFFICE, /*Офис*/
               KV, /*Квартира*/
               INFR_TYPE, /*Тип инфраструктуры*/
               STROY, /*Строение*/
               KORP, /*Корпус*/
               PUNCT_TYPE, /*Тип НП*/
               DOM, /*Дом*/
               INFR_NAME, /*Инфраструктура*/
               AREA_TYPE, /*Тип района*/
               CITY_TYPE, /*Тип города*/
               PUNCT_NAME, /*Нас. пункт*/
               CITY, /*Город*/
               REG_NAME, /*Наименование региона*/
               AREA, /*Район*/
               REG_NUM, /*Номер региона*/
               POST_INDEX, /*Почтовый индекс*/
               COUNTRY, /*Страна*/
               CODE, /*Код адреса*/
               ADDR_TYPE, /*Тип адреса*/
               ICUSNUM, /*Идентификатор контрагента*/
               ID_ADDR /*ID адреса*/
          FROM XMLTABLE('/ROWSET/ROW' PASSING xmltype(XML) COLUMNS ID_ADDR
                        NUMBER PATH 'ID_ADDR', /*ID адреса*/
                        ICUSNUM NUMBER PATH 'ICUSNUM', /*Идентификатор контрагента*/
                        ADDR_TYPE NUMBER PATH 'ADDR_TYPE', /*Тип адреса*/
                        CODE VARCHAR2(20) PATH 'CODE', /*Код адреса*/
                        COUNTRY NUMBER PATH 'COUNTRY', /*Страна*/
                        POST_INDEX VARCHAR2(10) PATH 'POST_INDEX', /*Почтовый индекс*/
                        REG_NUM NUMBER PATH 'REG_NUM', /*Номер региона*/
                        AREA VARCHAR2(256) PATH 'AREA', /*Район*/
                        REG_NAME VARCHAR2(51) PATH 'REG_NAME', /*Наименование региона*/
                        CITY VARCHAR2(100) PATH 'CITY', /*Город*/
                        PUNCT_NAME VARCHAR2(100) PATH 'PUNCT_NAME', /*Нас. пункт*/
                        CITY_TYPE VARCHAR2(10) PATH 'CITY_TYPE', /*Тип города*/
                        AREA_TYPE VARCHAR2(10) PATH 'AREA_TYPE', /*Тип района*/
                        INFR_NAME VARCHAR2(100) PATH 'INFR_NAME', /*Инфраструктура*/
                        DOM VARCHAR2(20) PATH 'DOM', /*Дом*/
                        PUNCT_TYPE VARCHAR2(10) PATH 'PUNCT_TYPE', /*Тип НП*/
                        KORP VARCHAR2(20) PATH 'KORP', /*Корпус*/
                        STROY VARCHAR2(10) PATH 'STROY', /*Строение*/
                        INFR_TYPE VARCHAR2(10) PATH 'INFR_TYPE', /*Тип инфраструктуры*/
                        KV VARCHAR2(10) PATH 'KV', /*Квартира*/
                        OFFICE VARCHAR2(60) PATH 'OFFICE', /*Офис*/
                        PORCH VARCHAR2(10) PATH 'PORCH', /*Подъезд*/
                        OKSM_CODE VARCHAR2(5) PATH 'OKSM_CODE', /*Код ОКСМ территории*/
                        ADDRESS_INLINE VARCHAR2(210) PATH 'ADDRESS_INLINE', /*Адрес для нерезидентов(не по КЛАДР) в одну строку.*/
                        STROY_TYPE NUMBER PATH 'STROY_TYPE' /*Признак строения*/) s),
      cur_cus_addr as
       (select STROY_TYPE, /*Признак строения*/
               ADDRESS_INLINE, /*Адрес для нерезидентов(не по КЛАДР) в одну строку.*/
               OKSM_CODE, /*Код ОКСМ территории*/
               PORCH, /*Подъезд*/
               OFFICE, /*Офис*/
               KV, /*Квартира*/
               INFR_TYPE, /*Тип инфраструктуры*/
               STROY, /*Строение*/
               KORP, /*Корпус*/
               PUNCT_TYPE, /*Тип НП*/
               DOM, /*Дом*/
               INFR_NAME, /*Инфраструктура*/
               AREA_TYPE, /*Тип района*/
               CITY_TYPE, /*Тип города*/
               PUNCT_NAME, /*Нас. пункт*/
               CITY, /*Город*/
               REG_NAME, /*Наименование региона*/
               AREA, /*Район*/
               REG_NUM, /*Номер региона*/
               POST_INDEX, /*Почтовый индекс*/
               COUNTRY, /*Страна*/
               CODE, /*Код адреса*/
               ADDR_TYPE, /*Тип адреса*/
               ICUSNUM, /*Идентификатор контрагента*/
               ID_ADDR /*ID адреса*/
          from cus_addr
         where cus_addr.icusnum = CUSNUM)
      select count(*) cnt
        from (select *
                from cur_cus_addr
              minus
              select *
                from xml_cus_addr
              union all
              select *
                from xml_cus_addr
              minus
              select *
                from cur_cus_addr);
  
    cursor cus_citizen_compare(CUSNUM number, XML clob) is
      with xml_cus_citizen as
       (SELECT ID, /*ID*/
               to_number(COUNTRY_CODE) COUNTRY_CODE, /*Страна*/
               COUNTRY_NAME, /*Полное наименование*/
               ICUSNUM, /*Идентификатор контрагента*/
               OSN /*Основной*/
          FROM XMLTABLE('/ROWSET/ROW' PASSING xmltype(XML) COLUMNS ID NUMBER PATH 'ID', /*ID*/
                        COUNTRY_CODE VARCHAR2(3) PATH 'COUNTRY_CODE', /*Страна*/
                        COUNTRY_NAME VARCHAR2(100) PATH 'COUNTRY_NAME', /*Полное наименование*/
                        ICUSNUM NUMBER PATH 'ICUSNUM', /*Идентификатор контрагента*/
                        OSN VARCHAR2(1) PATH 'OSN' /*Основной*/) s),
      cur_cus_citizen as
       (select ID, /*ID*/
               COUNTRY_CODE, /*Страна*/
               COUNTRY_NAME, /*Полное наименование*/
               ICUSNUM, /*Идентификатор контрагента*/
               OSN /*Основной*/
          from cus_citizen
         where cus_citizen.icusnum = CUSNUM)
      select count(*) cnt
        from (select *
                from cur_cus_citizen
              minus
              select *
                from xml_cus_citizen
              union all
              select *
                from xml_cus_citizen
              minus
              select *
                from cur_cus_citizen);
  
    cursor cus_docum_compare(CUSNUM number, XML clob) is
      with xml_cus_docum as
       (SELECT DOC_SUBDIV, /*Код подразделение, выдавшего документ*/
               to_date(DOC_PERIOD, 'DD.MM.YYYY') DOC_PERIOD, /*Период действия*/
               DOC_AGENCY, /*Выдавший орган*/
               to_date(DOC_DATE, 'DD.MM.YYYY') DOC_DATE, /*Дата выдачи*/
               DOC_SER, /*Серия документа*/
               DOC_NUM, /*Номер документа*/
               ID_DOC_TP, /*ID типа документа*/
               PREF, /*Признак основного документа-"Y"*/
               ICUSNUM, /*Идентификатор контрагента*/
               ID_DOC /*ID документа*/
          FROM XMLTABLE('/ROWSET/ROW' PASSING xmltype(XML) COLUMNS ID_DOC
                        NUMBER PATH 'ID_DOC', /*ID документа*/
                        ICUSNUM NUMBER PATH 'ICUSNUM', /*Идентификатор контрагента*/
                        PREF VARCHAR2(1) PATH 'PREF', /*Признак основного документа-"Y"*/
                        ID_DOC_TP NUMBER PATH 'ID_DOC_TP', /*ID типа документа*/
                        DOC_NUM VARCHAR2(20) PATH 'DOC_NUM', /*Номер документа*/
                        DOC_SER VARCHAR2(10) PATH 'DOC_SER', /*Серия документа*/
                        DOC_DATE VARCHAR2(10) PATH 'DOC_DATE', /*Дата выдачи*/
                        DOC_AGENCY VARCHAR2(210) PATH 'DOC_AGENCY', /*Выдавший орган*/
                        DOC_PERIOD VARCHAR2(10) PATH 'DOC_PERIOD', /*Период действия*/
                        DOC_SUBDIV VARCHAR2(15) PATH 'DOC_SUBDIV' /*Код подразделение, выдавшего документ*/) s),
      cur_cus_docum as
       (select DOC_SUBDIV, /*Код подразделение, выдавшего документ*/
               DOC_PERIOD, /*Период действия*/
               DOC_AGENCY, /*Выдавший орган*/
               DOC_DATE, /*Дата выдачи*/
               DOC_SER, /*Серия документа*/
               DOC_NUM, /*Номер документа*/
               ID_DOC_TP, /*ID типа документа*/
               PREF, /*Признак основного документа-"Y"*/
               ICUSNUM, /*Идентификатор контрагента*/
               ID_DOC /*ID документа*/
          from cus_docum
         where cus_docum.icusnum = CUSNUM)
      select count(*) cnt
        from (select *
                from cur_cus_docum
              minus
              select *
                from xml_cus_docum
              union all
              select *
                from xml_cus_docum
              minus
              select *
                from cur_cus_docum);
  begin
  
    for r in cus_compare(cusid, cus) loop
      cus_cnt := r.cnt;
    end loop;
  
    for r in cus_addr_compare(cusid, cus_addr) loop
      cus_addr_cnt := r.cnt;
    end loop;
  
    for r in cus_citizen_compare(cusid, cus_citizen) loop
      cus_citizen_cnt := r.cnt;
    end loop;
  
    for r in cus_docum_compare(cusid, cus_docum) loop
      cus_docum_cnt := r.cnt;
    end loop;
  
    if cus_cnt > 0 or cus_addr_cnt > 0 or cus_citizen_cnt > 0 or
       cus_docum_cnt > 0 then
      res := 1;
    else
      res := 0;
    end if;
  
    dbms_output.put_line('cus_cnt=' || cus_cnt);
    dbms_output.put_line('cus_addr_cnt=' || cus_addr_cnt);
    dbms_output.put_line('cus_citizen_cnt=' || cus_citizen_cnt);
    dbms_output.put_line('cus_docum_cnt=' || cus_docum_cnt);
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  --Нахождение адреса гражданина
  function Address(ID number) return varchar2 is
    address varchar2(500);
  begin
    select trim(BOTH ',' from case
                  when AREA is not null and AREA = 'г. Сухум' then
                   AREA || ','
                  when AREA is not null and AREA != 'г. Сухум' then
                   AREA || ' р-н,'
                  else
                   ''
                end || case
                  when CITY is not null then
                   'г. ' || CITY || ','
                  else
                   ''
                end || case
                  when PUNCT_NAME is not null then
                   PUNCT_NAME || ','
                  else
                   ''
                end || case
                  when INFR_NAME is not null then
                   'ул. ' || INFR_NAME || ','
                  else
                   ''
                end || case
                  when DOM is not null then
                   'дом № ' || DOM || ','
                  else
                   ''
                end || case
                  when KORP is not null then
                   'корп. ' || KORP || ','
                  else
                   ''
                end || case
                  when KV is not null then
                   'кв. ' || KV || ','
                  else
                   ''
                end)
      into address
      from CUS_ADDR cs_ad
     where cs_ad.icusnum = ID;
    return(address);
  end;

  -- нахождение гражданина по методу Лаши
  function CheckBeforeAdd(last_name   in varchar2,
                          first_name  in varchar2,
                          middle_name in varchar2,
                          birth_date  in date) return T_TabCusList
    PIPELINED IS
  
    T_TAB T_TabCusList;
  
    query varchar2(4000);
  
    function Fnk1 return number is
      ret number;
    begin
      select count(*)
        into ret
        from cus
       where lower(cus.ccuslast_name) = lower(last_name)
         and lower(cus.ccusfirst_name) = lower(first_name)
         and lower(cus.ccusmiddle_name) = lower(middle_name);
      return ret;
    end Fnk1;
  
    function Fnk2 return number is
      ret number;
    begin
      select count(*)
        into ret
        from cus
       where lower(substr(cus.ccuslast_name, 1, 3)) =
             lower(substr(last_name, 1, 3))
         and lower(substr(cus.ccusfirst_name, 1, 3)) =
             lower(substr(first_name, 1, 3))
         and lower(substr(cus.ccusmiddle_name, 1, 3)) =
             lower(substr(middle_name, 1, 3));
      return ret;
    end Fnk2;
  
    function Fnk3 return number is
      ret number;
    begin
      select count(*)
        into ret
        from cus
       where lower(substr(cus.ccuslast_name, 1, 3)) =
             lower(substr(last_name, 1, 3))
         and lower(substr(cus.ccusfirst_name, 1, 1)) =
             lower(substr(first_name, 1, 1))
         and lower(substr(cus.ccusmiddle_name, 1, 1)) =
             lower(substr(middle_name, 1, 1));
      return ret;
    end Fnk3;
  
    function Fnk4 return number is
      ret number;
    begin
      select count(*)
        into ret
        from cus
       where cus.dcusbirthday = birth_date;
      return ret;
    end Fnk4;
  
  begin
  
    query := q'[
select CCUSNATIONALITY, /*Национальность*/
       CCUS_OK_SM, /*Код страны рождения*/
       ICUSOTD, /*ссылка на номер отделения*/
       CCUSPLACE_BIRTH, /*Место рождения*/
       CCUSSEX, /*Пол*/
       CCUSMIDDLE_NAME, /*Отчество*/
       CCUSFIRST_NAME, /*Имя*/
       CCUSLAST_NAME, /*Фамилия*/
       DCUSBIRTHDAY, /*Дата рождения (для физ.лиц)*/
       CCUSNAME_SH, /*Краткое наименование клиента*/
       CCUSCOUNTRY1, /*Страна местонахождения/Гражданство*/
       CCUSNAME, /*Название*/
       CCUSIDOPEN, /*Кто завел*/
       DCUSEDIT, /*Дата окончания срока действия документов*/
       DCUSOPEN, /*Дата заведения*/
       ICUSNUM /*Уникальный номер клиента*/
  from cus
]';
  
    if Fnk1 > 0 then
      query := query || q'[
 where lower(cus.ccuslast_name) = lower(:last_name)
   and lower(cus.ccusfirst_name) = lower(:first_name)
   and lower(cus.ccusmiddle_name) = lower(:middle_name)
]';
      EXECUTE IMMEDIATE query BULK COLLECT
        INTO T_TAB
        USING last_name, first_name, middle_name;
    elsif Fnk1 = 0 then
    
      if Fnk2 > 0 then
        query := query || q'[
 where lower(substr(cus.ccuslast_name, 1, 3)) =
             lower(substr(:last_name, 1, 3))
         and lower(substr(cus.ccusfirst_name, 1, 3)) =
             lower(substr(:first_name, 1, 3))
         and lower(substr(cus.ccusmiddle_name, 1, 3)) =
             lower(substr(:middle_name, 1, 3))
]';
        EXECUTE IMMEDIATE query BULK COLLECT
          INTO T_TAB
          USING last_name, first_name, middle_name;
      elsif Fnk2 = 0 then
      
        if Fnk3 > 0 then
          query := query || q'[
 where lower(substr(cus.ccuslast_name, 1, 3)) =
             lower(substr(:last_name, 1, 3))
         and lower(substr(cus.ccusfirst_name, 1, 1)) =
             lower(substr(:first_name, 1, 1))
         and lower(substr(cus.ccusmiddle_name, 1, 1)) =
             lower(substr(:middle_name, 1, 1))
]';
          EXECUTE IMMEDIATE query BULK COLLECT
            INTO T_TAB
            USING last_name, first_name, middle_name;
        elsif Fnk3 = 0 then
          /*query := query || q'[
           where cus.dcusbirthday = :birth_date
          ]';*/
          query := query || q'[
 where 1!=1
]';
          EXECUTE IMMEDIATE query BULK COLLECT
            INTO T_TAB;
        end if;
      end if;
    end if;
  
    FOR indx IN 1 .. T_TAB.COUNT LOOP
      pipe row(T_TAB(indx));
    END LOOP;
    RETURN;
  end;

end MJCUS;
/
grant execute on XXI.MJCUS to ODB;


prompt
prompt Creating package body MJUSERS
prompt =============================
prompt
create or replace package body xxi.MJUsers is

  E_USR EXCEPTION;
  PRAGMA EXCEPTION_INIT(E_USR, -20572);
  E_NO_USER EXCEPTION;
  PRAGMA EXCEPTION_INIT(E_NO_USER, -01918);

  E_USER_ALREADY_EXISTS EXCEPTION;
  PRAGMA EXCEPTION_INIT(E_USER_ALREADY_EXISTS, -01920);

  E_User_Does_Not_Exist exception;
  pragma Exception_Init(E_User_Does_Not_Exist, -01918);

  procedure writelog(error VARCHAR2, ret out VARCHAR2, packname varchar2) is
    pragma autonomous_transaction;
  begin
    insert into LOG_ERROR
      (CURDATE_, DESC_ERROR_, PACKNAME)
    values
      (sysdate, error, packname);
    ret := 'error';
    commit;
  end;

  function UpdateUser(IUSRBRANCH_           number,
                      CUSRPOSITION_         varchar2,
                      DUSRHIRE_             date,
                      DUSRFIRE_             date,
                      ZAGS_                 number,
                      IUSRPWD_LENGTH_       number,
                      IUSRCHR_QUANTITY_     number,
                      IUSRNUM_QUANTITY_     number,
                      IUSRSPEC_QUANTITY_    number,
                      MUST_CHANGE_PASSWORD_ CHAR,
                      USRID                 number,
                      CUSRNAME_             varchar2,
                      NOTARY_               number,
                      ACC_LEVEL             varchar2,
                      FIO_SH_               varchar2,
                      FIO_ABH_SH_           varchar2,
                      FIO_ABH_              varchar2) return varchar2 is
    ret varchar2(4000) := 'ok';
  begin
    update usr
       set IUSRBRANCH           = IUSRBRANCH_,
           CUSRPOSITION         = CUSRPOSITION_,
           DUSRHIRE             = DUSRHIRE_,
           DUSRFIRE             = DUSRFIRE_,
           ZAGS_ID              = ZAGS_,
           IUSRPWD_LENGTH       = IUSRPWD_LENGTH_,
           IUSRCHR_QUANTITY     = IUSRCHR_QUANTITY_,
           IUSRNUM_QUANTITY     = IUSRNUM_QUANTITY_,
           IUSRSPEC_QUANTITY    = IUSRSPEC_QUANTITY_,
           MUST_CHANGE_PASSWORD = MUST_CHANGE_PASSWORD_,
           CUSRNAME             = CUSRNAME_,
           USR.NOTARY_ID        = NOTARY_,
           usr.access_level     = ACC_LEVEL,
           FIO_SH               = FIO_SH_,
           FIO_ABH_SH           = FIO_ABH_SH_,
           FIO_ABH              = FIO_ABH_
    
     where usr.iusrid = USRID;
    return(ret);
  exception
    when others then
      writelog(sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE,
               ret,
               'UpdateUser');
  end;

  function createusr(CUSRLOGNAME_ VARCHAR2,
                     CUSRNAME_    VARCHAR2,
                     PASS         VARCHAR2) return varchar2 is
    ret            varchar2(10) := 'ok';
    cnt_usrlogname number;
    usrid          number;
    er             boolean := false;
    lv_stmt        varchar2(500);
    lv_stmt_role   varchar2(500);
  begin
    begin
      if nvl(CUSRLOGNAME_, 'null') = 'null' then
        er := true;
        writelog('Логин пустой', ret, 'CreateUser');
      elsif nvl(CUSRNAME_, 'null') = 'null' then
        er := true;
        writelog('ФИО пусто', ret, 'CreateUser');
      elsif nvl(PASS, 'null') = 'null' then
        er := true;
        writelog('Пароль пустой', ret, 'CreateUser');
      end if;
    
      if er then
        return 'error';
      end if;
    
      select count(*)
        into cnt_usrlogname
        from usr
       where usr.cusrlogname = CUSRLOGNAME_;
      if cnt_usrlogname = 0 then
        insert into usr
          (cusrlogname, cusrname)
        values
          (CUSRLOGNAME_, CUSRNAME_)
        returning IUSRID into usrid;
      
        /*Создание пользователя в базе*/
        lv_stmt := 'CREATE USER ' || CUSRLOGNAME_ || ' IDENTIFIED BY ' || PASS;
        EXECUTE IMMEDIATE (lv_stmt);
        /*Добавим базовую роль ODB*/
        lv_stmt_role := 'GRANT odb TO ' || CUSRLOGNAME_;
        EXECUTE IMMEDIATE (lv_stmt_role);
      
      else
        writelog('Пользователь с таким логином уже существует!',
                 ret,
                 'CreateUser');
      end if;
    exception
      when others then
        writelog(sqlerrm || chr(13) || chr(10) ||
                 DBMS_UTILITY.FORMAT_ERROR_BACKTRACE,
                 ret,
                 'CreateUser');
    end;
    return ret;
  end;

  FUNCTION Get_User_Info(ErrMsg out VARCHAR2, Log_Name IN VARCHAR2)
    RETURN NUMBER IS
    Usr_ID USR.IUSRID%TYPE;
    CURSOR C_USER_INFO IS
      SELECT IUSRID FROM USR WHERE CUSRLOGNAME = Upper(Log_Name);
  BEGIN
  
    OPEN C_USER_INFO;
    FETCH C_USER_INFO
      INTO Usr_ID;
    CLOSE C_USER_INFO;
  
    IF Usr_ID IS NULL THEN
      ErrMsg := ErrMsg || 'Пользователь ' || Log_Name ||
                ' не авторизован для работы ' || ' в системе';
    END IF;
  
    RETURN Usr_ID;
  
  EXCEPTION
    WHEN OTHERS THEN
      ErrMsg := ErrMsg || 'Get_User_Info: ' || SqlErrM;
      RETURN NULL;
  END Get_User_Info;

  FUNCTION Check_Password_Quality(ErrMsg         out varchar2,
                                  New_Pwd        IN VARCHAR2,
                                  pPwd_Length    IN INTEGER,
                                  pChr_Quantity  IN INTEGER,
                                  pNum_Quantity  IN INTEGER,
                                  pSpec_Quantity in Integer default null)
    RETURN BOOLEAN IS
    Pwd_Length    INTEGER;
    Chr_Quantity  INTEGER;
    Num_Quantity  INTEGER;
    Spec_Quantity INTEGER;
    cSymbol       CHAR(1);
  
    Liters        VARCHAR2(64) := 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
    Digits        VARCHAR2(10) := '0123456789';
    Special       VarChar2(30) default '!"#$%&()*+,-/:;<=>?_';
    Liters_Count  NUMBER := 0;
    Digits_Count  NUMBER := 0;
    Special_Count Number default 0;
  
    function Check_Restricted return Boolean is
      vPassword VarChar2(100) default New_Pwd;
    begin
      vPassword := Trim(Translate(vPassword,
                                  Liters,
                                  LPad(' ', Length(Liters))));
      vPassword := Trim(Translate(vPassword,
                                  Digits,
                                  LPad(' ', Length(Digits))));
      vPassword := Trim(Translate(vPassword,
                                  Special,
                                  LPad(' ', Length(Special))));
      return vPassword is not null;
    end;
  
  BEGIN
  
    IF New_Pwd IS NULL THEN
      ErrMsg := ErrMsg || 'Не задан пароль';
      RAISE E_USR;
    END IF;
  
    Pwd_Length    := NVL(pPwd_Length, C_DEF_PWD_LENGTH);
    Chr_Quantity  := NVL(pChr_Quantity, C_DEF_PWD_CHR_QUANTITY);
    Num_Quantity  := NVL(pNum_Quantity, C_DEF_PWD_NUM_QUANTITY);
    Spec_Quantity := NVL(pSpec_Quantity, C_DEF_PWD_SPEC_QUANTITY);
  
    IF Length(New_Pwd) < Pwd_Length THEN
      ErrMsg := ErrMsg ||
                'Слишком короткий пароль, минимальная длина пароля ' ||
                Pwd_Length;
      RAISE E_USR;
    END IF;
  
    FOR i IN 1 .. Length(New_Pwd) LOOP
      cSymbol := Upper(SubStr(New_Pwd, i, 1));
      IF Instr(Liters, cSymbol) > 0 THEN
        Liters_Count := Liters_Count + 1;
      ELSIF Instr(Digits, cSymbol) > 0 THEN
        Digits_Count := Digits_Count + 1;
      elsif InStr(Special, cSymbol) > 0 then
        Special_Count := Special_Count + 1;
      END IF;
    END LOOP;
  
    IF Liters_Count < Chr_Quantity THEN
      ErrMsg := ErrMsg ||
                'Недостаточное количество алфавитных символов в пароле';
      RAISE E_USR;
    END IF;
  
    IF Digits_Count < Num_Quantity THEN
      ErrMsg := ErrMsg ||
                'Недостаточное количество цифровых символов в пароле';
      RAISE E_USR;
    END IF;
  
    if Special_Count < Spec_Quantity then
      ErrMsg := ErrMsg ||
                'Недостаточное количество специальных символов в пароле';
      raise E_USR;
    end if;
  
    if Check_Restricted then
      ErrMsg := ErrMsg ||
                'Разрешено использовать только буквы латинского алфавита, цифры и символы: ' ||
                Special;
      raise E_USR;
    end if;
  
    RETURN TRUE;
  
  EXCEPTION
    WHEN E_USR THEN
      RETURN FALSE;
  END Check_Password_Quality;

  FUNCTION Check_Password(ErrMsg          out VARCHAR2,
                          Log_Name        IN VARCHAR2,
                          New_Pwd         IN VARCHAR2,
                          Confirm_New_Pwd IN VARCHAR2) RETURN NUMBER IS
    Usr_ID         USR.IUSRID%TYPE;
    Pwd_Length     INTEGER;
    Chr_Quantity   INTEGER;
    Num_Quantity   INTEGER;
    Spec_Quantity  Integer;
    History_Length INTEGER;
  
    CURSOR C_PASSWD_INFO IS
      SELECT IUSRPWD_LENGTH,
             IUSRCHR_QUANTITY,
             IUSRNUM_QUANTITY,
             IUSRPWDREUSE,
             iUSRspec_quantity
        FROM USR
       WHERE IUSRID = Usr_ID;
  BEGIN
  
    Usr_ID := Get_User_Info(ErrMsg, Log_Name);
  
    IF Usr_ID IS NULL THEN
      RAISE E_USR;
    END IF;
  
    IF New_Pwd != Confirm_New_Pwd THEN
      ErrMsg := ErrMsg ||
                'Несовпадение паролей, пожалуйста, повторите ввод';
      RAISE E_USR;
    end if;
  
    OPEN C_PASSWD_INFO;
    FETCH C_PASSWD_INFO
      INTO Pwd_Length,
           Chr_Quantity,
           Num_Quantity,
           History_Length,
           Spec_Quantity;
    CLOSE C_PASSWD_INFO;
  
    Pwd_Length    := NVL(Pwd_Length, C_DEF_PWD_LENGTH);
    Chr_Quantity  := NVL(Chr_Quantity, C_DEF_PWD_CHR_QUANTITY);
    Num_Quantity  := NVL(Num_Quantity, C_DEF_PWD_NUM_QUANTITY);
    Spec_Quantity := NVL(Spec_Quantity, C_DEF_PWD_SPEC_QUANTITY);
  
    IF NOT Check_Password_Quality(ErrMsg,
                                  New_Pwd,
                                  Pwd_Length,
                                  Chr_Quantity,
                                  Num_Quantity,
                                  Spec_Quantity) THEN
      RAISE E_USR;
    END IF;
  
    RETURN 0;
  
  EXCEPTION
    WHEN E_USR THEN
      RETURN - 1;
    WHEN OTHERS THEN
      ErrMsg := ErrMsg || 'Check_Password: ' || SqlErrM;
      RETURN - 1;
  END Check_Password;

  FUNCTION Set_Up_Password(ErrMsg   out VARCHAR2,
                           Log_Name IN VARCHAR2,
                           Pwd      IN VARCHAR2,
                           Pwd2     IN VARCHAR2) RETURN NUMBER IS
    Usr_ID       USR.IUSRID%TYPE;
    vIsSavePoint Boolean default false;
    vActionId    AU_Action.iaction_id%type;
  BEGIN
    Usr_ID := Get_User_Info(ErrMsg, Log_Name);
    IF Usr_ID IS NULL THEN
      RAISE E_USR;
    END IF;
  
    IF Check_Password(ErrMsg, Log_Name, Pwd, Pwd2) != 0 THEN
      RAISE E_USR;
    END IF;
  
    dbms_output.put_line('ALTER USER ' || Log_Name || ' IDENTIFIED BY ' || Pwd);
  
    execute immediate ('ALTER USER ' || Log_Name || ' IDENTIFIED BY ' || Pwd);
  
    vIsSavePoint := true;
  
    SAVEPOINT SP_BEFORE_SETPASSWD;
  
    select S_AU_Action.NextVal into vActionId from dual;
    insert into AU_Action
      (iaction_id,
       cauduser,
       caudmachine,
       caudprogram,
       caudoperation,
       ctable,
       caudaction,
       caudmodule)
    values
      (vActionId,
       Log_Name,
       Auditing.v_machine,
       Auditing.v_program,
       'U',
       'USR',
       Auditing.v_action,
       Auditing.v_module);
    --update USR set must_change_password = 'Y' where iUSRid = Usr_Id;
    --delete Usr_Token where user_id=Usr_Id;
  
    AUP_Util.Put_Data(vActionId, 'PASSWORD', '******', '******');
    --COMMIT;
    RETURN 0;
  
  EXCEPTION
    WHEN E_USR THEN
      if vIsSavePoint then
        ROLLBACK TO SP_BEFORE_SETPASSWD;
      end if;
      RETURN - 1;
    WHEN OTHERS THEN
      ErrMsg := ErrMsg || 'Set_Up_Password: ' || SqlErrM;
      if vIsSavePoint then
        ROLLBACK TO SP_BEFORE_SETPASSWD;
      end if;
      RETURN - 1;
  END Set_Up_Password;

  function OdbAccess(usr number, act number) return number is
    ret number;
  begin
    select count(*)
      into ret
      from ODB_ACTUSR t
     where t.usr_id = usr
       and t.odbact_id = act;
    return(ret);
  end;

  /*
  ||
  ||Учреждение, Загс, Нотариус, Оба...
  ||
  */
  function ACC_LEV(logn varchar2) return varchar2 is
    ret varchar2(3);
  begin
    select usr.access_level
      into ret
      from usr
     where upper(usr.cusrlogname) = upper(logn);
    return(ret);
  end;

  function OdbMnuAccess(usr number, act number) return number is
    ret number;
  begin
    select count(*)
      into ret
      from ODB_MNUUSR t
     where t.usr_id = usr
       and t.odbmnu_id = act;
    return(ret);
  end;

  function OdbMnuAccessGrp(grp number, act number) return number is
    ret number;
  begin
    select count(*)
      into ret
      from ODB_MNUGRP t
     where t.grp_id = grp
       and t.odbmnu_id = act;
    return(ret);
  end;

  FUNCTION ODB_ACT_ACCESS_GRP(GRP NUMBER, ACT NUMBER) RETURN NUMBER IS
    RET NUMBER;
  BEGIN
    SELECT COUNT(*)
      INTO RET
      FROM ODB_ACTGRP T
     WHERE T.GRP_ID = GRP
       AND T.ODBACT_ID = ACT;
    RETURN(RET);
  END;

  FUNCTION MNU_ACCESS(MNU_ID NUMBER, USR_LOGIN VARCHAR2 DEFAULT USER)
    RETURN NUMBER IS
    GRP_CNT NUMBER;
    USR_CNT NUMBER;
    USRID   NUMBER;
  BEGIN
  
    SELECT USR.IUSRID
      INTO USRID
      FROM USR
     WHERE UPPER(USR.CUSRLOGNAME) = UPPER(USR_LOGIN);
  
    SELECT COUNT(*)
      INTO GRP_CNT
      FROM ODB_MNUGRP GRP_MNU, ODB_GRP_MEMBER GRP_USR
     WHERE GRP_MNU.GRP_ID = GRP_USR.GRP_ID
       AND GRP_MNU.ODBMNU_ID = MNU_ID
       AND GRP_USR.IUSRID = USRID;
  
    SELECT COUNT(*)
      INTO USR_CNT
      FROM ODB_MNUUSR
     WHERE ODB_MNUUSR.USR_ID = USRID
       AND ODB_MNUUSR.ODBMNU_ID = MNU_ID;
  
    RETURN(GREATEST(USR_CNT, GRP_CNT));
  END;

  FUNCTION ACT_ACCESS(ACT_ID NUMBER, USR_LOGIN VARCHAR2 DEFAULT USER)
    RETURN NUMBER IS
    GRP_CNT NUMBER;
    USR_CNT NUMBER;
    USRID   NUMBER;
  BEGIN
  
    SELECT USR.IUSRID
      INTO USRID
      FROM USR
     WHERE UPPER(USR.CUSRLOGNAME) = UPPER(USR_LOGIN);
  
    SELECT COUNT(*)
      INTO GRP_CNT
      FROM ODB_ACTGRP GRP_ACT, ODB_GRP_MEMBER GRP_USR
     WHERE GRP_ACT.GRP_ID = GRP_USR.GRP_ID
       AND GRP_ACT.ODBACT_ID = ACT_ID
       AND GRP_USR.IUSRID = USRID;
  
    SELECT COUNT(*)
      INTO USR_CNT
      FROM ODB_ACTUSR
     WHERE ODB_ACTUSR.USR_ID = USRID
       AND ODB_ACTUSR.ODBACT_ID = ACT_ID;
  
    RETURN(GREATEST(USR_CNT, GRP_CNT));
  END;

  function OdbMnuAccess(act number) return number is
    ret   number;
    usrid number;
  begin
  
    select t.iusrid
      into usrid
      from usr t
     where upper(t.cusrlogname) = upper(user);
  
    select count(*)
      into ret
      from ODB_MNUUSR t
     where t.usr_id = usrid
       and t.odbmnu_id = act;
    return(ret);
  end;

  FUNCTION ODBACCESS(ACT NUMBER) RETURN NUMBER IS
    RET   NUMBER;
    USRID NUMBER;
  BEGIN
    SELECT T.IUSRID
      INTO USRID
      FROM USR T
     WHERE UPPER(T.CUSRLOGNAME) = UPPER(USER);
  
    SELECT COUNT(*)
      INTO RET
      FROM ODB_ACTUSR T
     WHERE T.USR_ID = USRID
       AND T.ODBACT_ID = ACT;
    RETURN(RET);
  END;

  function OdbActUsrAdd(usr number, act number) return varchar2 is
    ret varchar2(4000) := 'ok';
    cnt number;
  begin
    begin
      select count(*)
        into cnt
        from ODB_ACTUSR t
       where t.usr_id = usr
         and t.odbact_id = act;
      if cnt > 0 then
        ret := 'Действие уже доступно!';
      else
        insert into ODB_ACTUSR (USR_ID, ODBACT_ID) values (usr, act);
      end if;
    exception
      when others then
        ret := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
      
    end;
    return(ret);
  end;

  function OdbMnuUsrAdd(usr number, act number) return varchar2 is
    ret varchar2(4000) := 'ok';
    cnt number;
  begin
    begin
      select count(*)
        into cnt
        from odb_mnuusr t
       where t.usr_id = usr
         and t.odbmnu_id = act;
      if cnt > 0 then
        ret := 'Действие уже доступно!';
      else
        insert into odb_mnuusr (USR_ID, ODBMNU_ID) values (usr, act);
      end if;
    exception
      when others then
        ret := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
      
    end;
    return(ret);
  end;

  FUNCTION ODB_ACT_GRP_DEL(GRP NUMBER, ACT NUMBER) RETURN VARCHAR2 IS
    RET VARCHAR2(4000) := 'ok';
    CNT NUMBER;
  BEGIN
    BEGIN
      SELECT COUNT(*)
        INTO CNT
        FROM ODB_ACTGRP T
       WHERE T.GRP_ID = GRP
         AND T.ODBACT_ID = ACT;
      IF CNT = 0 THEN
        RET := 'Действие недоступно, добавьте сначала!';
      ELSE
        DELETE FROM ODB_ACTGRP
         WHERE GRP_ID = GRP
           AND ODBACT_ID = ACT;
      END IF;
    EXCEPTION
      WHEN OTHERS THEN
        RET := SQLERRM || CHR(13) || CHR(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
      
    END;
    RETURN(RET);
  END;

  FUNCTION ODB_ACT_GRP_ADD(GRP NUMBER, ACT NUMBER) RETURN VARCHAR2 IS
    RET VARCHAR2(4000) := 'ok';
    CNT NUMBER;
  BEGIN
    BEGIN
      SELECT COUNT(*)
        INTO CNT
        FROM ODB_ACTGRP T
       WHERE T.GRP_ID = GRP
         AND T.ODBACT_ID = ACT;
      IF CNT > 0 THEN
        RET := 'Действие уже доступно!';
      ELSE
        INSERT INTO ODB_ACTGRP (GRP_ID, ODBACT_ID) VALUES (GRP, ACT);
      END IF;
    EXCEPTION
      WHEN OTHERS THEN
        RET := SQLERRM || CHR(13) || CHR(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
      
    END;
    RETURN(RET);
  END;

  function OdbMnuGrpAdd(grp number, act number) return varchar2 is
    ret varchar2(4000) := 'ok';
    cnt number;
  begin
    begin
      select count(*)
        into cnt
        from odb_mnugrp t
       where t.grp_id = grp
         and t.odbmnu_id = act;
      if cnt > 0 then
        ret := 'Действие уже доступно!';
      else
        insert into odb_mnugrp (grp_id, ODBMNU_ID) values (grp, act);
      end if;
    exception
      when others then
        ret := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
      
    end;
    return(ret);
  end;

  procedure AddOdbActionItem(error       out varchar2,
                             ACT_PARENT_ number,
                             ACT_NAME_   varchar2) is
    act_npp_ number;
  begin
    if ACT_NAME_ is not null and ACT_PARENT_ is not null then
      select nvl(max(ACT_NPP), 0) + 1
        into act_npp_
        from ODB_ACTION t
       where ACT_PARENT = ACT_PARENT_;
    
      insert into odb_action
        (ACT_PARENT, act_npp, act_name)
      values
        (ACT_PARENT_, act_npp_, ACT_NAME_);
    else
      error := 'Пусто "ID родитель" и наименование действия';
    end if;
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
    
  end;

  procedure AddOdbMenuItem(error       out varchar2,
                           MNU_PARENT_ number,
                           MNU_NAME_   varchar2) is
    MNU_npp_ number;
  begin
    if MNU_NAME_ is not null and MNU_PARENT_ is not null then
      select nvl(max(MNU_NPP), 0) + 1
        into MNU_npp_
        from odb_mnu t
       where MNU_PARENT = MNU_PARENT_;
      insert into odb_mnu
        (MNU_PARENT, MNU_npp, MNU_name)
      values
        (MNU_PARENT_, MNU_npp_, MNU_NAME_);
    else
      error := 'Пусто "ID родитель" и наименование действия';
    end if;
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
    
  end;

  procedure EditOdbActionItem(error     out varchar2,
                              ID        number,
                              ACT_NAME_ varchar2,
                              new_ac    number) is
  begin
    if new_ac is not null then
      update odb_action t
         set t.act_name = ACT_NAME_, t.act_parent = new_ac
       where t.act_id = ID;
    else
      update odb_action t set t.act_name = ACT_NAME_ where t.act_id = ID;
    end if;
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
    
  end;

  procedure EditOdbMenuItem(error     out varchar2,
                            ID        number,
                            MNU_NAME_ varchar2,
                            new_mnu   number) is
  begin
    if new_mnu is not null then
      update odb_mnu t
         set t.mnu_name = MNU_NAME_, t.mnu_parent = new_mnu
       where t.mnu_id = ID;
    else
      update odb_mnu t set t.mnu_name = MNU_NAME_ where t.mnu_id = ID;
    end if;
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
    
  end;

  procedure DeleteUser(error out varchar2, USR_ID number) is
    usename varchar2(100);
  begin
  
    select upper(usr.cusrlogname)
      into usename
      from usr
     where usr.iusrid = USR_ID;
  
    delete from usr where usr.iusrid = USR_ID;
  
    execute immediate 'drop user ' || usename;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
    
  end;

  function OdbActUsrDelete(usr number, act number) return varchar2 is
    ret varchar2(4000) := 'ok';
    cnt number;
  begin
    begin
      select count(*)
        into cnt
        from ODB_ACTUSR t
       where t.usr_id = usr
         and t.odbact_id = act;
      if cnt = 0 then
        ret := 'Действие недоступно, добавьте сначала!';
      else
        delete from ODB_ACTUSR
         where USR_ID = usr
           and ODBACT_ID = act;
      end if;
    exception
      when others then
        ret := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
      
    end;
    return(ret);
  end;

  function OdbMnuUsrDelete(usr number, act number) return varchar2 is
    ret varchar2(4000) := 'ok';
    cnt number;
  begin
    begin
      select count(*)
        into cnt
        from odb_mnuusr t
       where t.usr_id = usr
         and t.odbmnu_id = act;
      if cnt = 0 then
        ret := 'Действие недоступно, добавьте сначала!';
      else
        delete from odb_mnuusr
         where USR_ID = usr
           and ODBMNU_ID = act;
      end if;
    exception
      when others then
        ret := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
      
    end;
    return(ret);
  end;

  function OdbMnuGrpDelete(grp number, act number) return varchar2 is
    ret varchar2(4000) := 'ok';
    cnt number;
  begin
    begin
      select count(*)
        into cnt
        from odb_mnugrp t
       where t.grp_id = grp
         and t.odbmnu_id = act;
      if cnt = 0 then
        ret := 'Действие недоступно, добавьте сначала!';
      else
        delete from odb_mnugrp
         where grp_id = grp
           and ODBMNU_ID = act;
      end if;
    exception
      when others then
        ret := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
      
    end;
    return(ret);
  end;
  --
  -- Клонировать пользователя
  --
  FUNCTION Clone_User(Cloned_ID IN VARCHAR2,
                      New_Login IN VARCHAR2,
                      New_Name  IN VARCHAR2,
                      Err_Msg   OUT VARCHAR2) RETURN INTEGER IS
    New_ID USR.IUSRID%TYPE;
    E_E    EXCEPTION;
  BEGIN
  
    SAVEPOINT SP_BEFORE_CLONE_USER;
    Err_Msg := NULL;
  
    IF Cloned_ID IS NULL THEN
      Err_Msg := 'Не указан образец для клонирования';
      RAISE E_E;
    END IF;
  
    IF New_Login IS NULL THEN
      Err_Msg := 'Не указано имя регистрации нового пользователя';
      RAISE E_E;
    END IF;
  
    SELECT S_USR.NextVal INTO New_ID FROM DUAL;
  
    BEGIN
      -- запись о пользователе
      INSERT INTO USR
        (iusrid,
         cusrlogname,
         cusrname,
         cusrposition,
         dusrhire,
         iusrbranch,
         dusrfire,
         iusrpwd_length,
         iusrchr_quantity,
         iusrnum_quantity,
         iusrexp_days,
         cusroffphone,
         twrtstart,
         twrtend,
         cemail,
         crestrict_term,
         iusrpwdreuse,
         iusrspec_quantity,
         welcome_message,
         short_name,
         lock_date_time,
         lock_info,
         must_change_password,
         short_position,
         workday_time_end,
         workday_time_begin,
         zags_id)
        (SELECT New_ID,
                New_Login,
                New_Name,
                cusrposition,
                dusrhire,
                iusrbranch,
                dusrfire,
                iusrpwd_length,
                iusrchr_quantity,
                iusrnum_quantity,
                iusrexp_days,
                cusroffphone,
                twrtstart,
                twrtend,
                cemail,
                crestrict_term,
                iusrpwdreuse,
                iusrspec_quantity,
                welcome_message,
                short_name,
                lock_date_time,
                lock_info,
                must_change_password,
                short_position,
                workday_time_end,
                workday_time_begin,
                zags_id
           FROM USR
          WHERE IUSRID = Cloned_ID);
    EXCEPTION
      WHEN E_USER_ALREADY_EXISTS THEN
        Err_Msg := 'Пользователь с таким именем регистрации уже существует';
        RAISE E_E;
      WHEN OTHERS THEN
        Err_Msg := 'Ошибка добавления записи в список пользователей ' ||
                   SqlErrM;
        RAISE E_E;
    END;
    --
    -- доступ к функциям
    --
    BEGIN
      INSERT INTO odb_actusr
        (usr_id, odbact_id)
        (SELECT usr_id, odbact_id FROM odb_actusr WHERE usr_id = Cloned_ID);
    EXCEPTION
      WHEN OTHERS THEN
        Err_Msg := 'Ошибка копирования прав доступа к списку функций ' ||
                   SqlErrM;
        RAISE E_E;
    END;
    --
    -- доступ к меню
    --
    BEGIN
      INSERT INTO odb_mnuusr
        (usr_id, odbmnu_id)
        (SELECT usr_id, odbmnu_id FROM odb_mnuusr WHERE usr_id = Cloned_ID);
    EXCEPTION
      WHEN OTHERS THEN
        Err_Msg := 'Ошибка копирования прав доступа к списку функций ' ||
                   SqlErrM;
        RAISE E_E;
    END;
  
    RETURN New_ID;
  
  EXCEPTION
    WHEN E_E THEN
      ROLLBACK TO SP_BEFORE_CLONE_USER;
      RETURN NULL;
    WHEN OTHERS THEN
      Err_Msg := 'Ошибка клонирования ' || SqlErrM;
      ROLLBACK TO SP_BEFORE_CLONE_USER;
      RETURN NULL;
  END Clone_User;

end MJUsers;
/
grant execute on XXI.MJUSERS to ODB;


prompt
prompt Creating package body NOTATY
prompt ============================
prompt
create or replace package body xxi.NOTATY is

end NOTATY;
/
grant execute on XXI.NOTATY to ODB;


prompt
prompt Creating package body N3D_FX_PKG
prompt ================================
prompt
create or replace package body xxi.N3D_FX_Pkg is
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  -- Общие функции
  -- Java FX
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  n Number;
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  -- Select single
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  procedure Select_Single(pTableName in VarChar2, -- Имя таблицы
                          pIdName    in T_Tab_VarChar2_2000, -- Список Id
                          pIdType    in Sys.ODCINumberList, -- Список Типов Id
                          pIdValueS  in T_Tab_VarChar2_2000, -- Список строковых значений Id
                          pIdValueN  in Sys.ODCINumberList) is
    -- Список сисловых значений Id
    vColumn VarChar2(2000);
    vValue  VarChar2(2000);
    vDesc   Integer;
  begin
    for i in 1 .. pIdName.count loop
      if i > 1 then
        vColumn := vColumn || ',';
        vValue  := vValue || ',';
      end if;
      vColumn := vColumn || pIdName(i);
      vValue  := vValue || ':p' || i;
    end loop;
    vDesc := DBMS_SQL_Add.Open_Parse('insert into ' || pTableName || ' (' ||
                                     vColumn || ') values (' || vValue || ')');
    for i in 1 .. pIdName.count loop
      if pIdType(i) = 12 then
        DBMS_SQL.Bind_Variable(vDesc, 'p' || i, pIdValueS(i), 2000);
      else
        DBMS_SQL.Bind_Variable(vDesc, 'p' || i, pIdValueN(i));
      end if;
    end loop;
    n := DBMS_SQL.Execute(vDesc);
    DBMS_SQL.Close_Cursor(vDesc);
  end;
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  -- UnSelect single
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  procedure UnSelect_Single(pTableName in VarChar2, -- Имя таблицы
                            pIdName    in T_Tab_VarChar2_2000, -- Список Id
                            pIdType    in Sys.ODCINumberList, -- Список Типов Id
                            pIdValueS  in T_Tab_VarChar2_2000, -- Список строковых значений Id
                            pIdValueN  in Sys.ODCINumberList) is
    -- Список сисловых значений Id
    vWhere VarChar2(2000);
    vDesc  Integer;
  begin
    for i in 1 .. pIdName.count loop
      if i > 1 then
        vWhere := vWhere || ' and ';
      end if;
      vWhere := vWhere || pIdName(i) || '=:p' || i;
    end loop;
    vDesc := DBMS_SQL_Add.Open_Parse('delete ' || pTableName || ' where ' ||
                                     vWhere);
    for i in 1 .. pIdName.count loop
      if pIdType(i) = 12 then
        DBMS_SQL.Bind_Variable(vDesc, 'p' || i, pIdValueS(i), 2000);
      else
        DBMS_SQL.Bind_Variable(vDesc, 'p' || i, pIdValueN(i));
      end if;
    end loop;
    n := DBMS_SQL.Execute(vDesc);
    DBMS_SQL.Close_Cursor(vDesc);
  end;
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  -- Select all
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  procedure Select_All(pTableName in VarChar2, -- Имя таблицы
                       pIdName    in T_Tab_VarChar2_2000, -- Список Id
                       pSQL       in VarChar2, -- Запрос
                       pWhere     in VarChar2) is
    -- Условие
    vColumn VarChar2(2000);
  begin
    for i in 1 .. pIdName.count loop
      if i > 1 then
        vColumn := vColumn || ',';
      end if;
      vColumn := vColumn || pIdName(i);
    end loop;
    execute immediate 'insert into ' || pTableName || ' (' || vColumn ||
                      ') select ' || vColumn || ' from (select * from (' || pSQL ||
                      ') where ' || NVL(pWhere, '1=1') || ')';
  end;
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  -- UnSelect all
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  procedure UnSelect_All(pTableName in VarChar2, -- Имя таблицы
                         pIdName    in T_Tab_VarChar2_2000, -- Список Id
                         pSQL       in VarChar2, -- Запрос
                         pWhere     in VarChar2) is
    -- Условие
    vColumn VarChar2(2000);
  begin
    for i in 1 .. pIdName.count loop
      if i > 1 then
        vColumn := vColumn || ',';
      end if;
      vColumn := vColumn || pIdName(i);
    end loop;
    execute immediate 'delete ' || pTableName || ' where (' || vColumn ||
                      ') in (select ' || vColumn ||
                      ' from (select * from (' || pSQL || ') where ' ||
                      NVL(pWhere, '1=1') || '))';
  end;
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  -- Проверка SQL запроса
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  procedure Check_SQL(pSQL     in VarChar2, -- SQL запрос
                      pType    in Integer, -- Тип (0 - SQL, 1 - PL/SQL)
                      pName    in VarChar2, -- Имя запроса
                      pResult  out Integer, -- Результат
                      pMessage out VarChar2) is -- Сообщение об ошибке
  begin
    if AC.CheckCode(pMessage,
                    pType,
                    iif(pType = 0, pSQL, 'begin ' || pSQL || ' end;')) then
      pResult := N3D_Pkg.cResultOk;
    else
      pResult := N3D_Pkg.cResultFake;
    end if;
    pMessage := Replace(pMessage, '"%NAME%"', '"' || pName || '"');
  end;
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
end;
/
grant execute on XXI.N3D_FX_PKG to ODB;


prompt
prompt Creating package body N3D_PKG
prompt =============================
prompt
create or replace package body xxi.N3D_Pkg is

end N3D_Pkg;
/
grant execute on XXI.N3D_PKG to ODB;


prompt
prompt Creating package body PATERN
prompt ============================
prompt
create or replace package body xxi.PATERN is

  sep varchar2(2) := chr(13) || chr(10);

  procedure AddPatern(error          out varchar2,
                      PС_NUMBER_    VARCHAR2, /*Выдано свид. номер*/
                      PС_SERIA_     VARCHAR2, /*Выдано свид. серия*/
                      PС_CRDATE_    DATE, /*V-решение суда - дата*/
                      PС_CRNAME_    VARCHAR2, /*V-решение суда - наименование*/
                      PС_FZ_        DATE, /*B-заявление отца ребенка -дата*/
                      PС_TRZ_       DATE, /*A-Совмест. заявл. род-дата*/
                      PС_TYPE_      VARCHAR2, /*Тип основания для уст. отцовства*/
                      PС_M_         NUMBER, /*Ссылка на мать*/
                      PС_F_         NUMBER, /*Ссылка на отца*/
                      PС_CH_        NUMBER, /*Ссылка на ребенка*/
                      PС_AFT_MNAME_ VARCHAR2, /*Отчество после уст. отц*/
                      PС_AFT_FNAME_ VARCHAR2, /*Имя после уст. отц*/
                      PС_AFT_LNAME_ VARCHAR2, /*Фамилия после уст. отц*/
                      PC_ACT_ID_     NUMBER, /*Ссылка на свидетельство о рождении*/
                      PC_ZPLACE_     VARCHAR2, /*Место жительства заявителя*/
                      PC_ZLNAME_     VARCHAR2, /*Фамилия заявителя*/
                      PC_ZFNAME_     VARCHAR2, /*Имя заявителя*/
                      PC_ZMNAME_     VARCHAR2, /*Отчество заявителя*/
                      ID             out number) is
    ZagsId number;
  begin
  
    select nvl(zags_id, -1)
      into ZagsId
      from usr
     where usr.cusrlogname = user;
  
    if ZagsId = -1 then
      error := error || sep || 'У пользователя "' || user ||
               '" не проставлен номер ЗАГСа!';
      return;
    end if;
  
    insert into PATERN_CERT
      (PС_ZAGS, /*Загс*/
       PС_NUMBER, /*Выдано свид. номер*/
       PС_SERIA, /*Выдано свид. серия*/
       PС_CRDATE, /*V-решение суда - дата*/
       PС_CRNAME, /*V-решение суда - наименование*/
       PС_FZ, /*B-заявление отца ребенка -дата*/
       PС_TRZ, /*A-Совмест. заявл. род-дата*/
       PС_TYPE, /*Тип основания для уст. отцовства*/
       PС_M, /*Ссылка на мать*/
       PС_F, /*Ссылка на отца*/
       PС_CH, /*Ссылка на ребенка*/
       PС_AFT_MNAME, /*Отчество после уст. отц*/
       PС_AFT_FNAME, /*Имя после уст. отц*/
       PС_AFT_LNAME, /*Фамилия после уст. отц*/
       PC_ACT_ID, /*Ссылка на свидетельство о рождении*/
       PC_ZPLACE, /*Место жительства заявителя*/
       PC_ZLNAME, /*Фамилия заявителя*/
       PC_ZFNAME, /*Имя заявителя*/
       PC_ZMNAME /*Отчество заявителя*/)
    values
      (ZagsId, /*Загс*/
       PС_NUMBER_, /*Выдано свид. номер*/
       PС_SERIA_, /*Выдано свид. серия*/
       PС_CRDATE_, /*V-решение суда - дата*/
       PС_CRNAME_, /*V-решение суда - наименование*/
       PС_FZ_, /*B-заявление отца ребенка -дата*/
       PС_TRZ_, /*A-Совмест. заявл. род-дата*/
       decode(PС_TYPE_,
              'Совместное заявление родителей, не состоящихмежду собой в браке',
              'A',
              'Заявление отца ребенка',
              'B',
              'Решение суда об установлении отцовства',
              'V1',
              'Решение суда об установления факта признания',
              'V2'), /*Тип основания для уст. отцовства*/
       PС_M_, /*Ссылка на мать*/
       PС_F_, /*Ссылка на отца*/
       PС_CH_, /*Ссылка на ребенка*/
       PС_AFT_MNAME_, /*Отчество после уст. отц*/
       PС_AFT_FNAME_, /*Имя после уст. отц*/
       PС_AFT_LNAME_, /*Фамилия после уст. отц*/
       PC_ACT_ID_, /*Ссылка на свидетельство о рождении*/
       PC_ZPLACE_, /*Место жительства заявителя*/
       PC_ZLNAME_, /*Фамилия заявителя*/
       PC_ZFNAME_, /*Имя заявителя*/
       PC_ZMNAME_ /*Отчество заявителя*/)
    returning PC_ID into ID;
  
    update cus
       set cus.ccuslast_name   = PС_AFT_LNAME_,
           cus.ccusfirst_name  = PС_AFT_FNAME_,
           cus.ccusmiddle_name = PС_AFT_MNAME_
     where cus.icusnum = PС_CH_;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  procedure EditPatern(error          out varchar2,
                       ID             in number,
                       PС_NUMBER_    VARCHAR2, /*Выдано свид. номер*/
                       PС_SERIA_     VARCHAR2, /*Выдано свид. серия*/
                       PС_CRDATE_    DATE, /*V-решение суда - дата*/
                       PС_CRNAME_    VARCHAR2, /*V-решение суда - наименование*/
                       PС_FZ_        DATE, /*B-заявление отца ребенка -дата*/
                       PС_TRZ_       DATE, /*A-Совмест. заявл. род-дата*/
                       PС_TYPE_      VARCHAR2, /*Тип основания для уст. отцовства*/
                       PС_M_         NUMBER, /*Ссылка на мать*/
                       PС_F_         NUMBER, /*Ссылка на отца*/
                       PС_CH_        NUMBER, /*Ссылка на ребенка*/
                       PС_AFT_MNAME_ VARCHAR2, /*Отчество после уст. отц*/
                       PС_AFT_FNAME_ VARCHAR2, /*Имя после уст. отц*/
                       PС_AFT_LNAME_ VARCHAR2, /*Фамилия после уст. отц*/
                       PC_ACT_ID_     NUMBER, /*Ссылка на свидетельство о рождении*/
                       PC_ZPLACE_     VARCHAR2, /*Место жительства заявителя*/
                       PC_ZLNAME_     VARCHAR2, /*Фамилия заявителя*/
                       PC_ZFNAME_     VARCHAR2, /*Имя заявителя*/
                       PC_ZMNAME_     VARCHAR2 /*Отчество заявителя*/) is
  begin
  
    update PATERN_CERT t
       set PС_NUMBER    = PС_NUMBER_,
           PС_SERIA     = PС_SERIA_,
           PС_CRDATE    = PС_CRDATE_,
           PС_CRNAME    = PС_CRNAME_,
           PС_FZ        = PС_FZ_,
           PС_TRZ       = PС_TRZ_,
           PС_TYPE      = decode(PС_TYPE_,
                                 'Совместное заявление родителей, не состоящихмежду собой в браке',
                                 'A',
                                 'Заявление отца ребенка',
                                 'B',
                                 'Решение суда об установлении отцовства',
                                 'V1',
                                 'Решение суда об установления факта признания',
                                 'V2'),
           PС_M         = PС_M_,
           PС_F         = PС_F_,
           PС_CH        = PС_CH_,
           PС_AFT_MNAME = PС_AFT_MNAME_,
           PС_AFT_FNAME = PС_AFT_FNAME_,
           PС_AFT_LNAME = PС_AFT_LNAME_,
           PC_ACT_ID     = PC_ACT_ID_,
           PC_ZPLACE     = PC_ZPLACE_,
           PC_ZLNAME     = PC_ZLNAME_,
           PC_ZFNAME     = PC_ZFNAME_,
           PC_ZMNAME     = PC_ZMNAME_
     where t.pc_id = ID;
  
    /*Обновим данные гражданина*/
    update cus
       set cus.ccuslast_name   = PС_AFT_LNAME_,
           cus.ccusfirst_name  = PС_AFT_FNAME_,
           cus.ccusmiddle_name = PС_AFT_MNAME_
     where cus.icusnum = PС_CH_;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  /*Возврат текущего документа, простой XML*/
  procedure RetXmls(ID number, error out varchar2, clob_ out clob) is
  begin
    clob_ := dbms_xmlgen.getxmltype(q'[
select 
PC_ID, /*ID*/
PC_ACT_ID, /*Ссылка на свидетельство о рождении*/
PС_AFT_LNAME, /*Фамилия после уст. отц*/
PС_AFT_FNAME, /*Имя после уст. отц*/
PС_AFT_MNAME, /*Отчество после уст. отц*/
PС_CH, /*Ссылка на ребенка*/
PС_F, /*Ссылка на отца*/
PС_M, /*Ссылка на мать*/
PС_TYPE, /*Тип основания для уст. отцовства*/
to_char(PС_TRZ,'dd.mm.yyyy')PС_TRZ, /*A-Совмест. заявл. род-дата*/
to_char(PС_FZ,'dd.mm.yyyy')PС_FZ, /*B-заявление отца ребенка -дата*/
PС_CRNAME, /*V-решение суда - наименование*/
to_char(PС_CRDATE,'dd.mm.yyyy')PС_CRDATE, /*V-решение суда - дата*/
PС_SERIA, /*Выдано свид. серия*/
PС_NUMBER, /*Выдано свид. номер*/
to_char(PС_DATE,'dd.mm.yyyy hh24:mi:ss')PС_DATE, /*Дата документа*/
PС_USER, /*Юзер*/
PС_ZAGS, /*Загс*/
PC_ZMNAME, /*Отчество заявителя*/
PC_ZFNAME, /*Имя заявителя*/
PC_ZLNAME, /*Фамилия заявителя*/
PC_ZPLACE /*Место жительства заявителя*/
FROM patern_cert where PC_ID = ]' || ID).GetClobVal();
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  procedure CompareXmls(ID    in number,
                        clob_ in clob,
                        error out varchar2,
                        res   out number) is
    cnt number;
    cursor compare(ID_ number, XML clob) is
      with xml as
       (SELECT PC_ID, /*ID*/
               PC_ACT_ID, /*Ссылка на свидетельство о рождении*/
               PС_AFT_LNAME, /*Фамилия после уст. отц*/
               PС_AFT_FNAME, /*Имя после уст. отц*/
               PС_AFT_MNAME, /*Отчество после уст. отц*/
               PС_CH, /*Ссылка на ребенка*/
               PС_F, /*Ссылка на отца*/
               PС_M, /*Ссылка на мать*/
               PС_TYPE, /*Тип основания для уст. отцовства*/
               to_date(PС_TRZ, 'dd.mm.yyyy') PС_TRZ, /*A-Совмест. заявл. род-дата*/
               to_date(PС_FZ, 'dd.mm.yyyy') PС_FZ, /*B-заявление отца ребенка -дата*/
               PС_CRNAME, /*V-решение суда - наименование*/
               to_date(PС_CRDATE, 'dd.mm.yyyy') PС_CRDATE, /*V-решение суда - дата*/
               PС_SERIA, /*Выдано свид. серия*/
               PС_NUMBER, /*Выдано свид. номер*/
               to_date(PС_DATE, 'dd.mm.yyyy hh24:mi:ss') PС_DATE, /*Дата документа*/
               PС_USER, /*Юзер*/
               PС_ZAGS, /*Загс*/
               PC_ZMNAME, /*Отчество заявителя*/
               PC_ZFNAME, /*Имя заявителя*/
               PC_ZLNAME, /*Фамилия заявителя*/
               PC_ZPLACE /*Место жительства заявителя*/
          FROM XMLTABLE('/ROWSET/ROW' PASSING xmltype(XML) COLUMNS PC_ID
                        NUMBER PATH 'PC_ID', /*ID*/
                        PC_ACT_ID NUMBER PATH 'PC_ACT_ID', /*Ссылка на свидетельство о рождении*/
                        PС_AFT_LNAME VARCHAR2(100) PATH 'PС_AFT_LNAME', /*Фамилия после уст. отц*/
                        PС_AFT_FNAME VARCHAR2(100) PATH 'PС_AFT_FNAME', /*Имя после уст. отц*/
                        PС_AFT_MNAME VARCHAR2(100) PATH 'PС_AFT_MNAME', /*Отчество после уст. отц*/
                        PС_CH NUMBER PATH 'PС_CH', /*Ссылка на ребенка*/
                        PС_F NUMBER PATH 'PС_F', /*Ссылка на отца*/
                        PС_M NUMBER PATH 'PС_M', /*Ссылка на мать*/
                        PС_TYPE VARCHAR2(100) PATH 'PС_TYPE', /*Тип основания для уст. отцовства*/
                        PС_TRZ VARCHAR2(100) PATH 'PС_TRZ', /*A-Совмест. заявл. род-дата*/
                        PС_FZ VARCHAR2(100) PATH 'PС_FZ', /*B-заявление отца ребенка -дата*/
                        PС_CRNAME VARCHAR2(200) PATH 'PС_CRNAME', /*V-решение суда - наименование*/
                        PС_CRDATE VARCHAR2(100) PATH 'PС_CRDATE', /*V-решение суда - дата*/
                        PС_SERIA VARCHAR2(100) PATH 'PС_SERIA', /*Выдано свид. серия*/
                        PС_NUMBER VARCHAR2(100) PATH 'PС_NUMBER', /*Выдано свид. номер*/
                        PС_DATE VARCHAR2(100) PATH 'PС_DATE', /*Дата документа*/
                        PС_USER VARCHAR2(100) PATH 'PС_USER', /*Юзер*/
                        PС_ZAGS NUMBER PATH 'PС_ZAGS', /*Загс*/
                        PC_ZMNAME VARCHAR2(100) PATH 'PC_ZMNAME', /*Отчество заявителя*/
                        PC_ZFNAME VARCHAR2(100) PATH 'PC_ZFNAME', /*Имя заявителя*/
                        PC_ZLNAME VARCHAR2(100) PATH 'PC_ZLNAME', /*Фамилия заявителя*/
                        PC_ZPLACE VARCHAR2(100) PATH 'PC_ZPLACE' /*Место жительства заявителя*/) xmls),
      cur as
       (select PC_ID, /*ID*/
               PC_ACT_ID, /*Ссылка на свидетельство о рождении*/
               PС_AFT_LNAME, /*Фамилия после уст. отц*/
               PС_AFT_FNAME, /*Имя после уст. отц*/
               PС_AFT_MNAME, /*Отчество после уст. отц*/
               PС_CH, /*Ссылка на ребенка*/
               PС_F, /*Ссылка на отца*/
               PС_M, /*Ссылка на мать*/
               PС_TYPE, /*Тип основания для уст. отцовства*/
               PС_TRZ, /*A-Совмест. заявл. род-дата*/
               PС_FZ, /*B-заявление отца ребенка -дата*/
               PС_CRNAME, /*V-решение суда - наименование*/
               PС_CRDATE, /*V-решение суда - дата*/
               PС_SERIA, /*Выдано свид. серия*/
               PС_NUMBER, /*Выдано свид. номер*/
               PС_DATE, /*Дата документа*/
               PС_USER, /*Юзер*/
               PС_ZAGS, /*Загс*/
               PC_ZMNAME, /*Отчество заявителя*/
               PC_ZFNAME, /*Имя заявителя*/
               PC_ZLNAME, /*Фамилия заявителя*/
               PC_ZPLACE /*Место жительства заявителя*/
          from PATERN_CERT
         where PATERN_CERT.PC_ID = ID_)
      select count(*) cnt
        from (select *
                from cur
              minus
              select *
                from xml
              union all
              select *
                from xml
              minus
              select *
                from cur);
  
  begin
  
    for r in compare(ID, clob_) loop
      cnt := r.cnt;
    end loop;
  
    if cnt > 0 then
      res := 1;
    else
      res := 0;
    end if;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;
  
end PATERN;
/
grant execute on XXI.PATERN to ODB;


prompt
prompt Creating package body SHEDULER
prompt ==============================
prompt
create or replace package body xxi.SHEDULER is

end SHEDULER;
/
grant execute on XXI.SHEDULER to ODB;


prompt
prompt Creating package body TS
prompt ========================
prompt
CREATE OR REPLACE PACKAGE BODY XXI.TS IS

  FUNCTION TO_2000(cStr IN VARCHAR2) RETURN VARCHAR2 IS
  BEGIN
    RETURN SUBSTR(cStr, 1, 2000);
  END TO_2000;
END TS;
/
grant execute on XXI.TS to ODB;


prompt
prompt Creating package body UDPNAT
prompt ============================
prompt
create or replace package body xxi.UDPNAT is

  sep varchar2(2) := chr(13) || chr(10);

  procedure AddUpdNat(error        out varchar2,
                      id_          out number,
                      NEW_NAT_     NUMBER, /*Новая национальность*/
                      OLD_NAT_     NUMBER, /*Старая национальность*/
                      BRN_ACT_ID_  NUMBER, /*Ссылка  на свидетельство о рождении*/
                      CUSID_       NUMBER, /*Ссылка на клиента*/
                      SVID_SERIA_  varchar2,
                      SVID_NUMBER_ varchar2) is
    ZagsId number;
  begin
    select nvl(zags_id, -1)
      into ZagsId
      from usr
     where usr.cusrlogname = user;
  
    if ZagsId = -1 then
      error := error || sep || 'У пользователя "' || user ||
               '" не проставлен номер ЗАГСа!';
      return;
    end if;
  
    insert into UPD_NAT
      (NEW_NAT, /*Новая национальность*/
       OLD_NAT, /*Старая национальность*/
       BRN_ACT_ID, /*Ссылка  на свидетельство о рождении*/
       ZAGS_ID, /*Ссылка на загс*/
       CUSID, /*Ссылка на клиента*/
       SVID_SERIA,
       SVID_NUMBER)
    values
      (NEW_NAT_, /*Новая национальность*/
       OLD_NAT_, /*Старая национальность*/
       BRN_ACT_ID_, /*Ссылка  на свидетельство о рождении*/
       ZagsId, /*Ссылка на загс*/
       CUSID_, /*Ссылка на клиента*/
       SVID_SERIA_,
       SVID_NUMBER_)
    returning ID into id_;
  
    update cus
       set cus.CCUSNATIONALITY = NEW_NAT_
     where cus.icusnum = CUSID_;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  procedure EditUpdNat(error        out varchar2,
                       id_          in number,
                       NEW_NAT_     NUMBER, /*Новая национальность*/
                       OLD_NAT_     NUMBER, /*Старая национальность*/
                       BRN_ACT_ID_  NUMBER, /*Ссылка  на свидетельство о рождении*/
                       CUSID_       NUMBER, /*Ссылка на клиента*/
                       SVID_SERIA_  varchar2,
                       SVID_NUMBER_ varchar2) is
  begin
    update UPD_NAT f
       set NEW_NAT     = NEW_NAT_,
           OLD_NAT     = OLD_NAT_,
           BRN_ACT_ID  = BRN_ACT_ID_,
           CUSID       = CUSID_,
           SVID_SERIA  = SVID_SERIA_,
           SVID_NUMBER = SVID_NUMBER_
     where f.id = id_;
  
    update cus
       set cus.CCUSNATIONALITY = NEW_NAT_
     where cus.icusnum = CUSID_;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  /*Возврат текущего документа, простой XML*/
  procedure RetXmls(ID number, error out varchar2, clob_ out clob) is
  begin
    clob_ := dbms_xmlgen.getxmltype(q'[
select 
ID, /*ИД*/
CUSID, /*Ссылка на клиента*/
OPER, /*Пользователь*/
to_char(DOC_DATE,'dd.mm.yyyy hh24:mi:ss') DOC_DATE, /*Дата заведения*/
ZAGS_ID, /*Ссылка на загс*/
BRN_ACT_ID, /*Ссылка  на свидетельство о рождении*/
OLD_NAT, /*Старая национальность*/
NEW_NAT, /*Новая национальность*/
FIO, /*ФИО*/
SVID_SERIA, /*Серия*/
SVID_NUMBER /*Номер*/
FROM UPD_NAT where id = ]' || ID).GetClobVal();
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  procedure CompareXmls(ID    in number,
                        clob_ in clob,
                        error out varchar2,
                        res   out number) is
    cnt number;
    cursor compare(ID_ number, XML clob) is
      with xml as
       (SELECT ID, /*ИД*/
               CUSID, /*Ссылка на клиента*/
               OPER, /*Пользователь*/
               to_date(DOC_DATE, 'dd.mm.yyyy hh24:mi:ss') DOC_DATE, /*Дата заведения*/
               ZAGS_ID, /*Ссылка на загс*/
               BRN_ACT_ID, /*Ссылка  на свидетельство о рождении*/
               OLD_NAT, /*Старая национальность*/
               NEW_NAT, /*Новая национальность*/
               FIO, /*ФИО*/
               SVID_SERIA, /*Серия*/
               SVID_NUMBER /*Номер*/
          FROM XMLTABLE('/ROWSET/ROW' PASSING xmltype(XML) COLUMNS ID NUMBER PATH 'ID', /*ИД*/
                        CUSID NUMBER PATH 'CUSID', /*Ссылка на клиента*/
                        OPER VARCHAR2(100) PATH 'OPER', /*Пользователь*/
                        DOC_DATE VARCHAR2(100) PATH 'DOC_DATE', /*Дата заведения*/
                        ZAGS_ID NUMBER PATH 'ZAGS_ID', /*Ссылка на загс*/
                        BRN_ACT_ID NUMBER PATH 'BRN_ACT_ID', /*Ссылка  на свидетельство о рождении*/
                        OLD_NAT NUMBER PATH 'OLD_NAT', /*Старая национальность*/
                        NEW_NAT NUMBER PATH 'NEW_NAT', /*Новая национальность*/
                        FIO VARCHAR2(100) PATH 'FIO', /*ФИО*/
                        SVID_SERIA VARCHAR2(100) PATH 'SVID_SERIA', /*Серия*/
                        SVID_NUMBER VARCHAR2(100) PATH 'SVID_NUMBER' /*Номер*/) xmls),
      cur as
       (select ID, /*ИД*/
               CUSID, /*Ссылка на клиента*/
               OPER, /*Пользователь*/
               DOC_DATE, /*Дата заведения*/
               ZAGS_ID, /*Ссылка на загс*/
               BRN_ACT_ID, /*Ссылка  на свидетельство о рождении*/
               OLD_NAT, /*Старая национальность*/
               NEW_NAT, /*Новая национальность*/
               FIO, /*ФИО*/
               SVID_SERIA, /*Серия*/
               SVID_NUMBER /*Номер*/
          from UPD_NAT
         where UPD_NAT.id = ID_)
      select count(*) cnt
        from (select *
                from cur
              minus
              select *
                from xml
              union all
              select *
                from xml
              minus
              select *
                from cur);
  
  begin
  
    for r in compare(ID, clob_) loop
      cnt := r.cnt;
    end loop;
  
    if cnt > 0 then
      res := 1;
    else
      res := 0;
    end if;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

end UDPNAT;
/
grant execute on XXI.UDPNAT to ODB;


prompt
prompt Creating package body UPDABHNAME
prompt ================================
prompt
create or replace package body xxi.UpdAbhName is

  sep varchar2(2) := chr(13) || chr(10);

  procedure AddUpdAbhName(error          out varchar2,
                          id_            out number,
                          OLD_LASTNAME_  VARCHAR2, /*Фамилия до перемены*/
                          OLD_FIRSTNAME_ VARCHAR2, /*Имя до перемены*/
                          OLD_MIDDLNAME_ VARCHAR2, /*Отчество до перемены*/
                          NEW_LASTNAME_  VARCHAR2, /*Фамилия после перемены*/
                          NEW_FIRSTNAME_ VARCHAR2, /*Имя после перемены*/
                          NEW_MIDDLNAME_ VARCHAR2, /*Отчество после перемены*/
                          BRN_ACT_ID_    NUMBER, /*Ссылка на акт о рождении*/
                          CUSID_         NUMBER, /*Ссылка на клиента*/
                          SVID_NUMBER_   VARCHAR2, /*Выдано свидетельство номер*/
                          SVID_SERIA_    VARCHAR2 /*Выдано свидетельство серия*/) is
    ZagsId number;
  begin
    select nvl(zags_id, -1)
      into ZagsId
      from usr
     where usr.cusrlogname = user;
  
    if ZagsId = -1 then
      error := error || sep || 'У пользователя "' || user ||
               '" не проставлен номер ЗАГСа!';
      return;
    end if;
  
    insert into UPDATE_ABH_NAME
      (OLD_LASTNAME, /*Фамилия до перемены*/
       OLD_FIRSTNAME, /*Имя до перемены*/
       OLD_MIDDLNAME, /*Отчество до перемены*/
       NEW_LASTNAME, /*Фамилия после перемены*/
       NEW_FIRSTNAME, /*Имя после перемены*/
       NEW_MIDDLNAME, /*Отчество после перемены*/
       BRN_ACT_ID, /*Ссылка на акт о рождении*/
       ZAGS_ID, /*ИД загса*/
       CUSID, /*Ссылка на клиента*/
       SVID_NUMBER, /*Выдано свидетельство номер*/
       SVID_SERIA /*Выдано свидетельство серия*/)
    values
      (OLD_LASTNAME_, /*Фамилия до перемены*/
       OLD_FIRSTNAME_, /*Имя до перемены*/
       OLD_MIDDLNAME_, /*Отчество до перемены*/
       NEW_LASTNAME_, /*Фамилия после перемены*/
       NEW_FIRSTNAME_, /*Имя после перемены*/
       NEW_MIDDLNAME_, /*Отчество после перемены*/
       BRN_ACT_ID_, /*Ссылка на акт о рождении*/
       ZagsId, /*ИД загса*/
       CUSID_, /*Ссылка на клиента*/
       SVID_NUMBER_, /*Выдано свидетельство номер*/
       SVID_SERIA_ /*Выдано свидетельство серия*/)
    returning ID into id_;
  
    update cus
       set cus.ccuslast_name   = NEW_LASTNAME_,
           cus.ccusfirst_name  = NEW_FIRSTNAME_,
           cus.ccusmiddle_name = NEW_MIDDLNAME_
     where cus.icusnum = CUSID_;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  procedure EditUpdAbhName(error          out varchar2,
                           id_            in number,
                           OLD_LASTNAME_  VARCHAR2, /*Фамилия до перемены*/
                           OLD_FIRSTNAME_ VARCHAR2, /*Имя до перемены*/
                           OLD_MIDDLNAME_ VARCHAR2, /*Отчество до перемены*/
                           NEW_LASTNAME_  VARCHAR2, /*Фамилия после перемены*/
                           NEW_FIRSTNAME_ VARCHAR2, /*Имя после перемены*/
                           NEW_MIDDLNAME_ VARCHAR2, /*Отчество после перемены*/
                           BRN_ACT_ID_    NUMBER, /*Ссылка на акт о рождении*/
                           CUSID_         NUMBER, /*Ссылка на клиента*/
                           SVID_NUMBER_   VARCHAR2, /*Выдано свидетельство номер*/
                           SVID_SERIA_    VARCHAR2 /*Выдано свидетельство серия*/) is
  begin
    update UPDATE_ABH_NAME f
       set OLD_LASTNAME  = OLD_LASTNAME_,
           OLD_FIRSTNAME = OLD_FIRSTNAME_,
           OLD_MIDDLNAME = OLD_MIDDLNAME_,
           NEW_LASTNAME  = NEW_LASTNAME_,
           NEW_FIRSTNAME = NEW_FIRSTNAME_,
           NEW_MIDDLNAME = NEW_MIDDLNAME_,
           BRN_ACT_ID    = BRN_ACT_ID_,
           CUSID         = CUSID_,
           SVID_NUMBER   = SVID_NUMBER_,
           SVID_SERIA    = SVID_SERIA_
     where f.id = id_;
  
    update cus
       set cus.ccuslast_name   = NEW_LASTNAME_,
           cus.ccusfirst_name  = NEW_FIRSTNAME_,
           cus.ccusmiddle_name = NEW_MIDDLNAME_
     where cus.icusnum = CUSID_;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  /*Возврат текущего документа, простой XML*/
  procedure RetXmls(ID number, error out varchar2, clob_ out clob) is
  begin
    clob_ := dbms_xmlgen.getxmltype(q'[
select 
ID, /*ИД*/
OLD_LASTNAME, /*Фамилия до перемены*/
OLD_FIRSTNAME, /*Имя до перемены*/
OLD_MIDDLNAME, /*Отчество до перемены*/
NEW_LASTNAME, /*Фамилия после перемены*/
NEW_FIRSTNAME, /*Имя после перемены*/
NEW_MIDDLNAME, /*Отчество после перемены*/
BRN_ACT_ID, /*Ссылка на акт о рождении*/
to_char(DOC_DATE,'dd.mm.yyyy hh24:mi:ss') DOC_DATE, /*Дата создания*/
OPER, /*Пользователь*/
ZAGS_ID, /*ИД загса*/
CUSID, /*Ссылка на клиента*/
SVID_NUMBER, /*Выдано свидетельство номер*/
SVID_SERIA /*Выдано свидетельство серия*/
FROM UPDATE_ABH_NAME where id = ]' || ID).GetClobVal();
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  procedure CompareXmls(ID    in number,
                        clob_ in clob,
                        error out varchar2,
                        res   out number) is
    upd_cnt number;
    cursor update_name_compare(ID_ number, XML clob) is
      with xml as
       (SELECT ID, /*ИД*/
               OLD_LASTNAME, /*Фамилия до перемены*/
               OLD_FIRSTNAME, /*Имя до перемены*/
               OLD_MIDDLNAME, /*Отчество до перемены*/
               NEW_LASTNAME, /*Фамилия после перемены*/
               NEW_FIRSTNAME, /*Имя после перемены*/
               NEW_MIDDLNAME, /*Отчество после перемены*/
               BRN_ACT_ID, /*Ссылка на акт о рождении*/
               TO_DATE(DOC_DATE, 'dd.mm.yyyy hh24:mi:ss') DOC_DATE, /*Дата создания*/
               OPER, /*Пользователь*/
               ZAGS_ID, /*ИД загса*/
               CUSID, /*Ссылка на клиента*/
               SVID_NUMBER, /*Выдано свидетельство номер*/
               SVID_SERIA /*Выдано свидетельство серия*/
          FROM XMLTABLE('/ROWSET/ROW' PASSING xmltype(XML) COLUMNS ID NUMBER PATH 'ID', /*ИД*/
                        OLD_LASTNAME VARCHAR2(100) PATH 'OLD_LASTNAME', /*Фамилия до перемены*/
                        OLD_FIRSTNAME VARCHAR2(100) PATH 'OLD_FIRSTNAME', /*Имя до перемены*/
                        OLD_MIDDLNAME VARCHAR2(100) PATH 'OLD_MIDDLNAME', /*Отчество до перемены*/
                        NEW_LASTNAME VARCHAR2(100) PATH 'NEW_LASTNAME', /*Фамилия после перемены*/
                        NEW_FIRSTNAME VARCHAR2(100) PATH 'NEW_FIRSTNAME', /*Имя после перемены*/
                        NEW_MIDDLNAME VARCHAR2(100) PATH 'NEW_MIDDLNAME', /*Отчество после перемены*/
                        BRN_ACT_ID NUMBER PATH 'BRN_ACT_ID', /*Ссылка на акт о рождении*/
                        DOC_DATE VARCHAR2(100) PATH 'DOC_DATE', /*Дата создания*/
                        OPER VARCHAR2(50) PATH 'OPER', /*Пользователь*/
                        ZAGS_ID NUMBER PATH 'ZAGS_ID', /*ИД загса*/
                        CUSID NUMBER PATH 'CUSID', /*Ссылка на клиента*/
                        SVID_NUMBER VARCHAR2(100) PATH 'SVID_NUMBER', /*Выдано свидетельство номер*/
                        SVID_SERIA VARCHAR2(100) PATH 'SVID_SERIA' /*Выдано свидетельство серия*/) xmls),
      cur as
       (select ID, /*ИД*/
               OLD_LASTNAME, /*Фамилия до перемены*/
               OLD_FIRSTNAME, /*Имя до перемены*/
               OLD_MIDDLNAME, /*Отчество до перемены*/
               NEW_LASTNAME, /*Фамилия после перемены*/
               NEW_FIRSTNAME, /*Имя после перемены*/
               NEW_MIDDLNAME, /*Отчество после перемены*/
               BRN_ACT_ID, /*Ссылка на акт о рождении*/
               DOC_DATE, /*Дата создания*/
               OPER, /*Пользователь*/
               ZAGS_ID, /*ИД загса*/
               CUSID, /*Ссылка на клиента*/
               SVID_NUMBER, /*Выдано свидетельство номер*/
               SVID_SERIA /*Выдано свидетельство серия*/
          from UPDATE_ABH_NAME
         where UPDATE_ABH_NAME.id = ID_)
      select count(*) cnt
        from (select *
                from cur
              minus
              select *
                from xml
              union all
              select *
                from xml
              minus
              select *
                from cur);
  
  begin
  
    for r in update_name_compare(ID, clob_) loop
      upd_cnt := r.cnt;
    end loop;
  
    if upd_cnt > 0 then
      res := 1;
    else
      res := 0;
    end if;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

end UpdAbhName;
/
grant execute on XXI.UPDABHNAME to ODB;


prompt
prompt Creating package body UPDATE_PRJ
prompt ================================
prompt
create or replace package body xxi.UPDATE_PRJ is
  --сепаратор
  SEP char(2) := chr(13) || chr(10);
  procedure AddFileOrFolder(error       out varchar2,
                            folder_name varchar2,
                            file_name   varchar2,
                            isfolder    varchar2,
                            binary      blob,
                            parents     number,
                            bytes_       number) is
    max_ver number;
    new_id  number;
  begin
    --добавим запись о папке или файле
    insert into PROJECT
      (prj_parent, prj_name, is_folder, version, BYTES)
    values
      (parents,
       decode(isfolder, 'Y', folder_name, 'N', file_name),
       isfolder,
       decode(isfolder, 'Y', null, 'N', 1),
       decode(isfolder, 'Y', null, 'N', bytes_))
    returning prj_id into new_id;
    -- добавим файл
    if isfolder = 'N' then
      insert into prj_file (prj_id, blb_file) values (new_id, binary);
    end if;
    -- добавим историю
    --Есть в триггере PROJECT!
/*    if isfolder = 'N' then
      -- максимальный ИД
      select nvl((select max(hist.verision)
                   from PRJ_FL_VER_HIST hist
                  where hist.prj_id = parents),
                 0) + 1
        into max_ver
        from dual;
      -- в таблицу
      insert into PRJ_FL_VER_HIST
        (PRJ_ID, VERISION)
      values
        (new_id, max_ver);
    end if;*/
  exception
    when others then
      error := sqlerrm || SEP || DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  procedure UpdateFileOrFolder(error       out varchar2,
                               folder_name varchar2,
                               file_name   varchar2,
                               isfolder    varchar2,
                               binary      blob,
                               prj_id_     number,
                               bytes_       number) is
    max_ver number;
  begin
    if isfolder = 'N' then
      -- максимальный ИД
      select nvl((select max(hist.verision)
                   from PRJ_FL_VER_HIST hist
                  where hist.prj_id = prj_id_),
                 0) + 1
        into max_ver
        from dual;
    end if;
    --обновим запись о папке или файле
    update PROJECT
       set prj_name = decode(isfolder, 'Y', folder_name, 'N', file_name),
           version  = decode(isfolder, 'Y', null, 'N', max_ver),
           BYTES    = decode(isfolder, 'Y', null, 'N', bytes_)
     where PROJECT.PRJ_ID = prj_id_;
    -- добавим файл
    if isfolder = 'N' then
      update prj_file
         set prj_file.blb_file = binary
       where prj_file.prj_id = prj_id_;
    end if;
    -- добавим историю
    if isfolder = 'N' then
    
      -- в таблицу
      insert into PRJ_FL_VER_HIST
        (PRJ_ID, VERISION)
      values
        (prj_id_, max_ver);
    
    end if;
  exception
    when others then
      error := sqlerrm || SEP || DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;
end UPDATE_PRJ;
/
grant execute on XXI.UPDATE_PRJ to ODB;


prompt
prompt Creating package body UPDNAME
prompt =============================
prompt
create or replace package body xxi.UpdName is

  sep varchar2(2) := chr(13) || chr(10);

  procedure AddUpdName(error             out varchar2,
                       id_               out number,
                       OLD_LASTNAME_     VARCHAR2, /*Фамилия до перемены*/
                       OLD_FIRSTNAME_    VARCHAR2, /*Имя до перемены*/
                       OLD_MIDDLNAME_    VARCHAR2, /*Отчество до перемены*/
                       NEW_LASTNAME_     VARCHAR2, /*Фамилия после перемены*/
                       NEW_FIRSTNAME_    VARCHAR2, /*Имя после перемены*/
                       NEW_MIDDLNAME_    VARCHAR2, /*Отчество после перемены*/
                       BRN_ACT_ID_       NUMBER, /*Ссылка на акт о рождении*/
                       CUSID_            NUMBER, /*Ссылка на клиента*/
                       SVID_NUMBER_      VARCHAR2, /*Выдано свидетельство номер*/
                       SVID_SERIA_       VARCHAR2 /*Выдано свидетельство серия*/,
                       OLD_LASTNAME_AB_  VARCHAR2, /*Фамилия до перемены АБХ*/
                       OLD_FIRSTNAME_AB_ VARCHAR2, /*Имя до перемены АБХ*/
                       OLD_MIDDLNAME_AB_ VARCHAR2, /*Отчество до перемены АБХ*/
                       NEW_LASTNAME_AB_  VARCHAR2, /*Фамилия после перемены АБХ*/
                       NEW_FIRSTNAME_AB_ VARCHAR2, /*Имя после перемены АБХ*/
                       NEW_MIDDLNAME_AB_ VARCHAR2 /*Отчество после перемены АБХ*/) is
    ZagsId number;
  begin
    select nvl(zags_id, -1)
      into ZagsId
      from usr
     where usr.cusrlogname = user;
  
    if ZagsId = -1 then
      error := error || sep || 'У пользователя "' || user ||
               '" не проставлен номер ЗАГСа!';
      return;
    end if;
  
    insert into UPDATE_NAME
      (OLD_LASTNAME, /*Фамилия до перемены*/
       OLD_FIRSTNAME, /*Имя до перемены*/
       OLD_MIDDLNAME, /*Отчество до перемены*/
       NEW_LASTNAME, /*Фамилия после перемены*/
       NEW_FIRSTNAME, /*Имя после перемены*/
       NEW_MIDDLNAME, /*Отчество после перемены*/
       BRN_ACT_ID, /*Ссылка на акт о рождении*/
       ZAGS_ID, /*ИД загса*/
       CUSID, /*Ссылка на клиента*/
       SVID_NUMBER, /*Выдано свидетельство номер*/
       SVID_SERIA /*Выдано свидетельство серия*/,
       OLD_LASTNAME_AB, /*Фамилия до перемены АБХ*/
       OLD_FIRSTNAME_AB, /*Имя до перемены АБХ*/
       OLD_MIDDLNAME_AB, /*Отчество до перемены АБХ*/
       NEW_LASTNAME_AB, /*Фамилия после перемены АБХ*/
       NEW_FIRSTNAME_AB, /*Имя после перемены АБХ*/
       NEW_MIDDLNAME_AB /*Отчество после перемены АБХ*/)
    values
      (OLD_LASTNAME_, /*Фамилия до перемены*/
       OLD_FIRSTNAME_, /*Имя до перемены*/
       OLD_MIDDLNAME_, /*Отчество до перемены*/
       NEW_LASTNAME_, /*Фамилия после перемены*/
       NEW_FIRSTNAME_, /*Имя после перемены*/
       NEW_MIDDLNAME_, /*Отчество после перемены*/
       BRN_ACT_ID_, /*Ссылка на акт о рождении*/
       ZagsId, /*ИД загса*/
       CUSID_, /*Ссылка на клиента*/
       SVID_NUMBER_, /*Выдано свидетельство номер*/
       SVID_SERIA_, /*Выдано свидетельство серия*/
       OLD_LASTNAME_AB_, /*Фамилия до перемены АБХ*/
       OLD_FIRSTNAME_AB_, /*Имя до перемены АБХ*/
       OLD_MIDDLNAME_AB_, /*Отчество до перемены АБХ*/
       NEW_LASTNAME_AB_, /*Фамилия после перемены АБХ*/
       NEW_FIRSTNAME_AB_, /*Имя после перемены АБХ*/
       NEW_MIDDLNAME_AB_ /*Отчество после перемены АБХ*/)
    returning ID into id_;
  
    update cus
       set cus.ccuslast_name   = NEW_LASTNAME_,
           cus.ccusfirst_name  = NEW_FIRSTNAME_,
           cus.ccusmiddle_name = NEW_MIDDLNAME_
     where cus.icusnum = CUSID_;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  procedure EditUpdName(error             out varchar2,
                        id_               in number,
                        OLD_LASTNAME_     VARCHAR2, /*Фамилия до перемены*/
                        OLD_FIRSTNAME_    VARCHAR2, /*Имя до перемены*/
                        OLD_MIDDLNAME_    VARCHAR2, /*Отчество до перемены*/
                        NEW_LASTNAME_     VARCHAR2, /*Фамилия после перемены*/
                        NEW_FIRSTNAME_    VARCHAR2, /*Имя после перемены*/
                        NEW_MIDDLNAME_    VARCHAR2, /*Отчество после перемены*/
                        BRN_ACT_ID_       NUMBER, /*Ссылка на акт о рождении*/
                        CUSID_            NUMBER, /*Ссылка на клиента*/
                        SVID_NUMBER_      VARCHAR2, /*Выдано свидетельство номер*/
                        SVID_SERIA_       VARCHAR2, /*Выдано свидетельство серия*/
                        OLD_LASTNAME_AB_  VARCHAR2, /*Фамилия до перемены АБХ*/
                        OLD_FIRSTNAME_AB_ VARCHAR2, /*Имя до перемены АБХ*/
                        OLD_MIDDLNAME_AB_ VARCHAR2, /*Отчество до перемены АБХ*/
                        NEW_LASTNAME_AB_  VARCHAR2, /*Фамилия после перемены АБХ*/
                        NEW_FIRSTNAME_AB_ VARCHAR2, /*Имя после перемены АБХ*/
                        NEW_MIDDLNAME_AB_ VARCHAR2 /*Отчество после перемены АБХ*/) is
  begin
    update UPDATE_NAME f
       set OLD_LASTNAME     = OLD_LASTNAME_,
           OLD_FIRSTNAME    = OLD_FIRSTNAME_,
           OLD_MIDDLNAME    = OLD_MIDDLNAME_,
           NEW_LASTNAME     = NEW_LASTNAME_,
           NEW_FIRSTNAME    = NEW_FIRSTNAME_,
           NEW_MIDDLNAME    = NEW_MIDDLNAME_,
           BRN_ACT_ID       = BRN_ACT_ID_,
           CUSID            = CUSID_,
           SVID_NUMBER      = SVID_NUMBER_,
           SVID_SERIA       = SVID_SERIA_,
           OLD_LASTNAME_AB  = OLD_LASTNAME_AB_,
           OLD_FIRSTNAME_AB = OLD_FIRSTNAME_AB_,
           OLD_MIDDLNAME_AB = OLD_MIDDLNAME_AB_,
           NEW_LASTNAME_AB  = NEW_LASTNAME_AB_,
           NEW_FIRSTNAME_AB = NEW_FIRSTNAME_AB_,
           NEW_MIDDLNAME_AB = NEW_MIDDLNAME_AB_
    
     where f.id = id_;
  
    update cus
       set cus.ccuslast_name   = NEW_LASTNAME_,
           cus.ccusfirst_name  = NEW_FIRSTNAME_,
           cus.ccusmiddle_name = NEW_MIDDLNAME_
     where cus.icusnum = CUSID_;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  /*Возврат текущего документа, простой XML*/
  procedure RetXmls(ID number, error out varchar2, update_name out clob) is
  begin
    update_name := dbms_xmlgen.getxmltype(q'[select SVID_SERIA, /*Выдано свидетельство серия*/
         SVID_NUMBER, /*Выдано свидетельство номер*/
         CUSID, /*Ссылка на клиента*/
         ZAGS_ID, /*ИД загса*/ 
         OPER, /*Пользователь*/
         to_char(DOC_DATE, 'dd.mm.yyyy hh24:mi:ss') DOC_DATE, /*Дата создания*/
         BRN_ACT_ID, /*Ссылка на акт о рождении*/
         NEW_MIDDLNAME, /*Отчество после перемены*/
         NEW_FIRSTNAME, /*Имя после перемены*/
         NEW_LASTNAME, /*Фамилия после перемены*/
         OLD_MIDDLNAME, /*Отчество до перемены*/
         OLD_FIRSTNAME, /*Имя до перемены*/
         OLD_LASTNAME, /*Фамилия до перемены*/
         ID, /*ИД*/
         OLD_LASTNAME_AB, /*Фамилия до перемены АБХ*/
         OLD_FIRSTNAME_AB, /*Имя до перемены АБХ*/
         OLD_MIDDLNAME_AB, /*Отчество до перемены АБХ*/
         NEW_LASTNAME_AB, /*Фамилия после перемены АБХ*/
         NEW_FIRSTNAME_AB, /*Имя после перемены АБХ*/
         NEW_MIDDLNAME_AB /*Отчество после перемены АБХ*/
         FROM update_name where id = ]' || ID).GetClobVal();
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

  procedure CompareXmls(ID          in number,
                        update_name in clob,
                        error       out varchar2,
                        res         out number) is
  
    upd_cnt number;
  
    cursor update_name_compare(ID_ number, XML clob) is
      with xml_update_name as
       (SELECT SVID_SERIA, /*Выдано свидетельство серия*/
               SVID_NUMBER, /*Выдано свидетельство номер*/
               CUSID, /*Ссылка на клиента*/
               ZAGS_ID, /*ИД загса*/
               OPER, /*Пользователь*/
               to_date(DOC_DATE, 'dd.mm.yyyy hh24:mi:ss') DOC_DATE, /*Дата создания*/
               BRN_ACT_ID, /*Ссылка на акт о рождении*/
               NEW_MIDDLNAME, /*Отчество после перемены*/
               NEW_FIRSTNAME, /*Имя после перемены*/
               NEW_LASTNAME, /*Фамилия после перемены*/
               OLD_MIDDLNAME, /*Отчество до перемены*/
               OLD_FIRSTNAME, /*Имя до перемены*/
               OLD_LASTNAME, /*Фамилия до перемены*/
               ID, /*ИД*/
               OLD_LASTNAME_AB, /*Фамилия до перемены АБХ*/
               OLD_FIRSTNAME_AB, /*Имя до перемены АБХ*/
               OLD_MIDDLNAME_AB, /*Отчество до перемены АБХ*/
               NEW_LASTNAME_AB, /*Фамилия после перемены АБХ*/
               NEW_FIRSTNAME_AB, /*Имя после перемены АБХ*/
               NEW_MIDDLNAME_AB /*Отчество после перемены АБХ*/
          FROM XMLTABLE('/ROWSET/ROW' PASSING xmltype(XML) COLUMNS ID NUMBER PATH 'ID', /*ИД*/
                        OLD_LASTNAME VARCHAR2(100) PATH 'OLD_LASTNAME', /*Фамилия до перемены*/
                        OLD_FIRSTNAME VARCHAR2(100) PATH 'OLD_FIRSTNAME', /*Имя до перемены*/
                        OLD_MIDDLNAME VARCHAR2(100) PATH 'OLD_MIDDLNAME', /*Отчество до перемены*/
                        NEW_LASTNAME VARCHAR2(100) PATH 'NEW_LASTNAME', /*Фамилия после перемены*/
                        NEW_FIRSTNAME VARCHAR2(100) PATH 'NEW_FIRSTNAME', /*Имя после перемены*/
                        NEW_MIDDLNAME VARCHAR2(100) PATH 'NEW_MIDDLNAME', /*Отчество после перемены*/
                        BRN_ACT_ID NUMBER PATH 'BRN_ACT_ID', /*Ссылка на акт о рождении*/
                        DOC_DATE VARCHAR2(100) PATH 'DOC_DATE', /*Дата создания*/
                        OPER VARCHAR2(50) PATH 'OPER', /*Пользователь*/
                        ZAGS_ID NUMBER PATH 'ZAGS_ID', /*ИД загса*/
                        CUSID NUMBER PATH 'CUSID', /*Ссылка на клиента*/
                        SVID_NUMBER VARCHAR2(100) PATH 'SVID_NUMBER', /*Выдано свидетельство номер*/
                        SVID_SERIA VARCHAR2(100) PATH 'SVID_SERIA', /*Выдано свидетельство серия*/
                        OLD_LASTNAME_AB VARCHAR2(100) PATH 'OLD_LASTNAME_AB', /*Фамилия до перемены АБХ*/
                        OLD_FIRSTNAME_AB VARCHAR2(100) PATH
                        'OLD_FIRSTNAME_AB', /*Имя до перемены АБХ*/
                        OLD_MIDDLNAME_AB VARCHAR2(100) PATH
                        'OLD_MIDDLNAME_AB', /*Отчество до перемены АБХ*/
                        NEW_LASTNAME_AB VARCHAR2(100) PATH 'NEW_LASTNAME_AB', /*Фамилия после перемены АБХ*/
                        NEW_FIRSTNAME_AB VARCHAR2(100) PATH
                        'NEW_FIRSTNAME_AB', /*Имя после перемены АБХ*/
                        NEW_MIDDLNAME_AB VARCHAR2(100) PATH
                        'NEW_MIDDLNAME_AB' /*Отчество после перемены АБХ*/) xmls),
      cur_update_name as
       (select SVID_SERIA, /*Выдано свидетельство серия*/
               SVID_NUMBER, /*Выдано свидетельство номер*/
               CUSID, /*Ссылка на клиента*/
               ZAGS_ID, /*ИД загса*/
               OPER, /*Пользователь*/
               DOC_DATE, /*Дата создания*/
               BRN_ACT_ID, /*Ссылка на акт о рождении*/
               NEW_MIDDLNAME, /*Отчество после перемены*/
               NEW_FIRSTNAME, /*Имя после перемены*/
               NEW_LASTNAME, /*Фамилия после перемены*/
               OLD_MIDDLNAME, /*Отчество до перемены*/
               OLD_FIRSTNAME, /*Имя до перемены*/
               OLD_LASTNAME, /*Фамилия до перемены*/
               ID, /*ИД*/
               OLD_LASTNAME_AB, /*Фамилия до перемены АБХ*/
               OLD_FIRSTNAME_AB, /*Имя до перемены АБХ*/
               OLD_MIDDLNAME_AB, /*Отчество до перемены АБХ*/
               NEW_LASTNAME_AB, /*Фамилия после перемены АБХ*/
               NEW_FIRSTNAME_AB, /*Имя после перемены АБХ*/
               NEW_MIDDLNAME_AB /*Отчество после перемены АБХ*/
          from update_name
         where update_name.id = ID_)
      select count(*) cnt
        from (select *
                from cur_update_name
              minus
              select *
                from xml_update_name
              union all
              select *
                from xml_update_name
              minus
              select *
                from cur_update_name);
  
  begin
  
    for r in update_name_compare(ID, update_name) loop
      upd_cnt := r.cnt;
    end loop;
  
    if upd_cnt > 0 then
      res := 1;
    else
      res := 0;
    end if;
  
  exception
    when others then
      error := sqlerrm || chr(13) || chr(10) ||
               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
  end;

end UpdName;
/
grant execute on XXI.UPDNAME to ODB;


prompt
prompt Creating package body UTIL
prompt ==========================
prompt
create or replace package body xxi.UTIL is

  FUNCTION Get_Nat_Name(ID_ IN number) RETURN VARCHAR2 IS
    ret VARCHAR2(250) := ' ';
  BEGIN
    IF ID_ is not null THEN
      SELECT name INTO ret FROM nationality nat WHERE nat.id = ID_;
    END IF;
    RETURN ret;
  EXCEPTION
    WHEN OTHERS THEN
      RETURN ' ';
  END;

  FUNCTION Get_Country_Name(ID_ IN number) RETURN VARCHAR2 IS
    ret VARCHAR2(250) := ' ';
  BEGIN
    IF ID_ is not null THEN
      SELECT name INTO ret FROM countries WHERE code = ID_;
    END IF;
    RETURN ret;
  EXCEPTION
    WHEN OTHERS THEN
      RETURN ' ';
  END;

  function Address(ID number) return varchar2 is
    address varchar2(500) := ' ';
  begin
    select trim(BOTH ',' from case
                  when INFR_NAME is not null then
                   'ул. ' || INFR_NAME || ','
                  else
                   ''
                end || case
                  when DOM is not null then
                   'дом № ' || DOM || ','
                  else
                   ''
                end || case
                  when KV is not null then
                   'кв. ' || KV || ','
                  else
                   ''
                end)
      into address
      from CUS_ADDR cs_ad
     where cs_ad.icusnum = ID;
    return(address);
  EXCEPTION
    WHEN OTHERS THEN
      RETURN ' ';
  end;

  FUNCTION Get_Citizen(ID_ IN number) RETURN VARCHAR2 IS
    ret VARCHAR2(250) := ' ';
  BEGIN
    IF ID_ is not null THEN
      
      select t.country_name
        into ret
        from CUS_CITIZEN t
       where osn = 'Y'
         and ICUSNUM = ID_;
    END IF;
    RETURN ret;
  EXCEPTION
    WHEN OTHERS THEN
      RETURN ' ';
  END;

end UTIL;
/
grant execute on XXI.UTIL to ODB;


prompt
prompt Creating type body LISTAGG_CLOB_T
prompt =================================
prompt
CREATE OR REPLACE TYPE BODY XXI."LISTAGG_CLOB_T" is
  static function odciaggregateinitialize(sctx in out listagg_clob_t)
    return number is
  begin
    sctx := listagg_clob_t(null, null);
    return odciconst.success;
  end;
  --
  member function odciaggregateiterate(self  in out listagg_clob_t,
                                       a_val varchar2) return number is
    procedure add_val(p_val varchar2) is
    begin
      if nvl(lengthb(self.t_varchar2), 0) + lengthb(p_val) <= 4000
      -- Strange limit, the max size of self.t_varchar2 is 29993
      -- If you exceeds this number you get ORA-22813: operand value exceeds system limits
      -- with 29993 you get JSON-output as large 58894 bytes
      -- with 4000 you get JSON-output as large 1063896 bytes, probably max more
       then
        if self.t_varchar2 is null then
          self.t_varchar2 := self.t_varchar2 || p_val;
        else
          self.t_varchar2 := self.t_varchar2 || /*CHR(13) || CHR(10)*/',' || p_val;
        end if;
      else
        if self.t_clob is null then
          dbms_lob.createtemporary(self.t_clob, true, dbms_lob.call);
          dbms_lob.writeappend(self.t_clob,
                               length(self.t_varchar2),
                               self.t_varchar2);
        else
          dbms_lob.writeappend(self.t_clob,
                               length(self.t_varchar2 + 1),
                               CHR(13) || CHR(10)/*','*/ || self.t_varchar2);
        end if;
        self.t_varchar2 := p_val;
      end if;
    end;
  begin
    add_val(a_val);
    return odciconst.success;
  end;
  --
  member function odciaggregateterminate(self        in out listagg_clob_t,
                                         returnvalue out clob,
                                         flags       in number) return number is
  begin
    if self.t_clob is null then
      dbms_lob.createtemporary(self.t_clob, true, dbms_lob.call);
    end if;
    if self.t_varchar2 is not null then
      dbms_lob.writeappend(self.t_clob,
                           length(self.t_varchar2),
                           self.t_varchar2);
    end if;
    returnvalue := self.t_clob;
    return odciconst.success;
  end;
  --
  member function odciaggregatemerge(self in out listagg_clob_t,
                                     ctx2 in out listagg_clob_t)
    return number is
  begin
    if self.t_clob is null then
      dbms_lob.createtemporary(self.t_clob, true, dbms_lob.call);
    end if;
    if self.t_varchar2 is not null then
      dbms_lob.writeappend(self.t_clob,
                           length(self.t_varchar2),
                           self.t_varchar2);
    end if;
    if ctx2.t_clob is not null then
      dbms_lob.append(self.t_clob, ctx2.t_clob);
      dbms_lob.freetemporary(ctx2.t_clob);
    end if;
    if ctx2.t_varchar2 is not null then
      dbms_lob.writeappend(self.t_clob,
                           length(ctx2.t_varchar2),
                           ctx2.t_varchar2);
      ctx2.t_varchar2 := null;
    end if;
    return odciconst.success;
  end;
  --
end;
/

prompt
prompt Creating type body LOB_ROW
prompt ==========================
prompt
create or replace type body xxi.lob_row
as
  constructor function lob_row(p_row_no number)
    return self as result
  as
  begin
    self.row_no := p_row_no;
    return;
  end lob_row;
  --
  member function get_column(p_column_no pls_integer) return varchar2
  as
  begin
    return case p_column_no
             when   1 then self.column1
             when   2 then self.column2
             when   3 then self.column3
             when   4 then self.column4
             when   5 then self.column5
             when   6 then self.column6
             when   7 then self.column7
             when   8 then self.column8
             when   9 then self.column9
             when  10 then self.column10
             when  11 then self.column11
             when  12 then self.column12
             when  13 then self.column13
             when  14 then self.column14
             when  15 then self.column15
             when  16 then self.column16
             when  17 then self.column17
             when  18 then self.column18
             when  19 then self.column19
             when  20 then self.column20
             when  21 then self.column21
             when  22 then self.column22
             when  23 then self.column23
             when  24 then self.column24
             when  25 then self.column25
             when  26 then self.column26
             when  27 then self.column27
             when  28 then self.column28
             when  29 then self.column29
             when  30 then self.column30
             when  31 then self.column31
             when  32 then self.column32
             when  33 then self.column33
             when  34 then self.column34
             when  35 then self.column35
             when  36 then self.column36
             when  37 then self.column37
             when  38 then self.column38
             when  39 then self.column39
             when  40 then self.column40
             when  41 then self.column41
             when  42 then self.column42
             when  43 then self.column43
             when  44 then self.column44
             when  45 then self.column45
             when  46 then self.column46
             when  47 then self.column47
             when  48 then self.column48
             when  49 then self.column49
             when  50 then self.column50
             when  51 then self.column51
             when  52 then self.column52
             when  53 then self.column53
             when  54 then self.column54
             when  55 then self.column55
             when  56 then self.column56
             when  57 then self.column57
             when  58 then self.column58
             when  59 then self.column59
             when  60 then self.column60
             when  61 then self.column61
             when  62 then self.column62
             when  63 then self.column63
             when  64 then self.column64
             when  65 then self.column65
             when  66 then self.column66
             when  67 then self.column67
             when  68 then self.column68
             when  69 then self.column69
             when  70 then self.column70
             when  71 then self.column71
             when  72 then self.column72
             when  73 then self.column73
             when  74 then self.column74
             when  75 then self.column75
             when  76 then self.column76
             when  77 then self.column77
             when  78 then self.column78
             when  79 then self.column79
             when  80 then self.column80
             when  81 then self.column81
             when  82 then self.column82
             when  83 then self.column83
             when  84 then self.column84
             when  85 then self.column85
             when  86 then self.column86
             when  87 then self.column87
             when  88 then self.column88
             when  89 then self.column89
             when  90 then self.column90
             when  91 then self.column91
             when  92 then self.column92
             when  93 then self.column93
             when  94 then self.column94
             when  95 then self.column95
             when  96 then self.column96
             when  97 then self.column97
             when  98 then self.column98
             when  99 then self.column99
             when 100 then self.column100
             when 101 then self.column101
             when 102 then self.column102
             when 103 then self.column103
             when 104 then self.column104
             when 105 then self.column105
             when 106 then self.column106
             when 107 then self.column107
             when 108 then self.column108
             when 109 then self.column109
             when 110 then self.column110
             when 111 then self.column111
             when 112 then self.column112
             when 113 then self.column113
             when 114 then self.column114
             when 115 then self.column115
             when 116 then self.column116
             when 117 then self.column117
             when 118 then self.column118
             when 119 then self.column119
             when 120 then self.column120
             when 121 then self.column121
             when 122 then self.column122
             when 123 then self.column123
             when 124 then self.column124
             when 125 then self.column125
             when 126 then self.column126
             when 127 then self.column127
             when 128 then self.column128
             when 129 then self.column129
             when 130 then self.column130
             when 131 then self.column131
             when 132 then self.column132
             when 133 then self.column133
             when 134 then self.column134
             when 135 then self.column135
             when 136 then self.column136
             when 137 then self.column137
             when 138 then self.column138
             when 139 then self.column139
             when 140 then self.column140
             when 141 then self.column141
             when 142 then self.column142
             when 143 then self.column143
             when 144 then self.column144
             when 145 then self.column145
             when 146 then self.column146
             when 147 then self.column147
             when 148 then self.column148
             when 149 then self.column149
             when 150 then self.column150
             when 151 then self.column151
             when 152 then self.column152
             when 153 then self.column153
             when 154 then self.column154
             when 155 then self.column155
             when 156 then self.column156
             when 157 then self.column157
             when 158 then self.column158
             when 159 then self.column159
             when 160 then self.column160
             when 161 then self.column161
             when 162 then self.column162
             when 163 then self.column163
             when 164 then self.column164
             when 165 then self.column165
             when 166 then self.column166
             when 167 then self.column167
             when 168 then self.column168
             when 169 then self.column169
             when 170 then self.column170
             when 171 then self.column171
             when 172 then self.column172
             when 173 then self.column173
             when 174 then self.column174
             when 175 then self.column175
             when 176 then self.column176
             when 177 then self.column177
             when 178 then self.column178
             when 179 then self.column179
             when 180 then self.column180
             when 181 then self.column181
             when 182 then self.column182
             when 183 then self.column183
             when 184 then self.column184
             when 185 then self.column185
             when 186 then self.column186
             when 187 then self.column187
             when 188 then self.column188
             when 189 then self.column189
             when 190 then self.column190
             when 191 then self.column191
             when 192 then self.column192
             when 193 then self.column193
             when 194 then self.column194
             when 195 then self.column195
             when 196 then self.column196
             when 197 then self.column197
             when 198 then self.column198
             when 199 then self.column199
             when 200 then self.column200
           end;
  end get_column;
  --
  member function get_column_count return number
  as
  begin
    return case
             when self.column200 is not null then 200
             when self.column199 is not null then 199
             when self.column198 is not null then 198
             when self.column197 is not null then 197
             when self.column196 is not null then 196
             when self.column195 is not null then 195
             when self.column194 is not null then 194
             when self.column193 is not null then 193
             when self.column192 is not null then 192
             when self.column191 is not null then 191
             when self.column190 is not null then 190
             when self.column189 is not null then 189
             when self.column188 is not null then 188
             when self.column187 is not null then 187
             when self.column186 is not null then 186
             when self.column185 is not null then 185
             when self.column184 is not null then 184
             when self.column183 is not null then 183
             when self.column182 is not null then 182
             when self.column181 is not null then 181
             when self.column180 is not null then 180
             when self.column179 is not null then 179
             when self.column178 is not null then 178
             when self.column177 is not null then 177
             when self.column176 is not null then 176
             when self.column175 is not null then 175
             when self.column174 is not null then 174
             when self.column173 is not null then 173
             when self.column172 is not null then 172
             when self.column171 is not null then 171
             when self.column170 is not null then 170
             when self.column169 is not null then 169
             when self.column168 is not null then 168
             when self.column167 is not null then 167
             when self.column166 is not null then 166
             when self.column165 is not null then 165
             when self.column164 is not null then 164
             when self.column163 is not null then 163
             when self.column162 is not null then 162
             when self.column161 is not null then 161
             when self.column160 is not null then 160
             when self.column159 is not null then 159
             when self.column158 is not null then 158
             when self.column157 is not null then 157
             when self.column156 is not null then 156
             when self.column155 is not null then 155
             when self.column154 is not null then 154
             when self.column153 is not null then 153
             when self.column152 is not null then 152
             when self.column151 is not null then 151
             when self.column150 is not null then 150
             when self.column149 is not null then 149
             when self.column148 is not null then 148
             when self.column147 is not null then 147
             when self.column146 is not null then 146
             when self.column145 is not null then 145
             when self.column144 is not null then 144
             when self.column143 is not null then 143
             when self.column142 is not null then 142
             when self.column141 is not null then 141
             when self.column140 is not null then 140
             when self.column139 is not null then 139
             when self.column138 is not null then 138
             when self.column137 is not null then 137
             when self.column136 is not null then 136
             when self.column135 is not null then 135
             when self.column134 is not null then 134
             when self.column133 is not null then 133
             when self.column132 is not null then 132
             when self.column131 is not null then 131
             when self.column130 is not null then 130
             when self.column129 is not null then 129
             when self.column128 is not null then 128
             when self.column127 is not null then 127
             when self.column126 is not null then 126
             when self.column125 is not null then 125
             when self.column124 is not null then 124
             when self.column123 is not null then 123
             when self.column122 is not null then 122
             when self.column121 is not null then 121
             when self.column120 is not null then 120
             when self.column119 is not null then 119
             when self.column118 is not null then 118
             when self.column117 is not null then 117
             when self.column116 is not null then 116
             when self.column115 is not null then 115
             when self.column114 is not null then 114
             when self.column113 is not null then 113
             when self.column112 is not null then 112
             when self.column111 is not null then 111
             when self.column110 is not null then 110
             when self.column109 is not null then 109
             when self.column108 is not null then 108
             when self.column107 is not null then 107
             when self.column106 is not null then 106
             when self.column105 is not null then 105
             when self.column104 is not null then 104
             when self.column103 is not null then 103
             when self.column102 is not null then 102
             when self.column101 is not null then 101
             when self.column100 is not null then 100
             when self.column99  is not null then 99
             when self.column98  is not null then 98
             when self.column97  is not null then 97
             when self.column96  is not null then 96
             when self.column95  is not null then 95
             when self.column94  is not null then 94
             when self.column93  is not null then 93
             when self.column92  is not null then 92
             when self.column91  is not null then 91
             when self.column90  is not null then 90
             when self.column89  is not null then 89
             when self.column88  is not null then 88
             when self.column87  is not null then 87
             when self.column86  is not null then 86
             when self.column85  is not null then 85
             when self.column84  is not null then 84
             when self.column83  is not null then 83
             when self.column82  is not null then 82
             when self.column81  is not null then 81
             when self.column80  is not null then 80
             when self.column79  is not null then 79
             when self.column78  is not null then 78
             when self.column77  is not null then 77
             when self.column76  is not null then 76
             when self.column75  is not null then 75
             when self.column74  is not null then 74
             when self.column73  is not null then 73
             when self.column72  is not null then 72
             when self.column71  is not null then 71
             when self.column70  is not null then 70
             when self.column69  is not null then 69
             when self.column68  is not null then 68
             when self.column67  is not null then 67
             when self.column66  is not null then 66
             when self.column65  is not null then 65
             when self.column64  is not null then 64
             when self.column63  is not null then 63
             when self.column62  is not null then 62
             when self.column61  is not null then 61
             when self.column60  is not null then 60
             when self.column59  is not null then 59
             when self.column58  is not null then 58
             when self.column57  is not null then 57
             when self.column56  is not null then 56
             when self.column55  is not null then 55
             when self.column54  is not null then 54
             when self.column53  is not null then 53
             when self.column52  is not null then 52
             when self.column51  is not null then 51
             when self.column50  is not null then 50
             when self.column49  is not null then 49
             when self.column48  is not null then 48
             when self.column47  is not null then 47
             when self.column46  is not null then 46
             when self.column45  is not null then 45
             when self.column44  is not null then 44
             when self.column43  is not null then 43
             when self.column42  is not null then 42
             when self.column41  is not null then 41
             when self.column40  is not null then 40
             when self.column39  is not null then 39
             when self.column38  is not null then 38
             when self.column37  is not null then 37
             when self.column36  is not null then 36
             when self.column35  is not null then 35
             when self.column34  is not null then 34
             when self.column33  is not null then 33
             when self.column32  is not null then 32
             when self.column31  is not null then 31
             when self.column30  is not null then 30
             when self.column29  is not null then 29
             when self.column28  is not null then 28
             when self.column27  is not null then 27
             when self.column26  is not null then 26
             when self.column25  is not null then 25
             when self.column24  is not null then 24
             when self.column23  is not null then 23
             when self.column22  is not null then 22
             when self.column21  is not null then 21
             when self.column20  is not null then 20
             when self.column19  is not null then 19
             when self.column18  is not null then 18
             when self.column17  is not null then 17
             when self.column16  is not null then 16
             when self.column15  is not null then 15
             when self.column14  is not null then 14
             when self.column13  is not null then 13
             when self.column12  is not null then 12
             when self.column11  is not null then 11
             when self.column10  is not null then 10
             when self.column9   is not null then 9
             when self.column8   is not null then 8
             when self.column7   is not null then 7
             when self.column6   is not null then 6
             when self.column5   is not null then 5
             when self.column4   is not null then 4
             when self.column3   is not null then 3
             when self.column2   is not null then 2
             when self.column1   is not null then 1
             else 0
           end;
  end get_column_count;
end;
/

prompt
prompt Creating type body T_PROPERTIES
prompt ===============================
prompt
CREATE OR REPLACE TYPE BODY XXI."T_PROPERTIES"
IS
--
-- $Id: t_properties.sql,v 2.2 2016/12/15 09:40:44 psh500 Exp $
--
     CONSTRUCTOR FUNCTION T_Properties
         RETURN SELF AS RESULT
     IS
     BEGIN
         tabItem := T_Tab_Properties_Item ();

         RETURN;
     END T_Properties;

     CONSTRUCTOR FUNCTION T_Properties ( tabItem  T_Tab_Properties_Item )
         RETURN SELF AS RESULT
     IS
     BEGIN
         SELF.tabItem := tabItem;

         RETURN;
     END T_Properties;
--
--
--
     MEMBER FUNCTION find ( Self    IN  T_Properties,
                            cKey    IN  VARCHAR2 )
         RETURN INTEGER
     IS
         i_find  INTEGER := 0;
         i_st    INTEGER := tabItem.First ();
     BEGIN

         WHILE i_st IS NOT NULL LOOP

             IF tabItem (i_st).cKey = cKey THEN
                i_find := i_st;
                EXIT;
             END IF;

             i_st := tabItem.Next (i_st);
         END LOOP;

         RETURN i_find;
     END find;
--
--
--
     MEMBER FUNCTION get ( Self    IN  T_Properties,
                           cKey    IN  VARCHAR2 )
         RETURN VARCHAR2
     IS
         i_find  INTEGER := Self.find (cKey);
     BEGIN

         IF i_find > 0 THEN
            RETURN tabItem (i_find).cValue;
         ELSE
            RAISE NO_DATA_FOUND;
         END IF;

     END get;
--
--
--
     MEMBER FUNCTION getProperty ( Self    IN  T_Properties,
                                   cKey    IN  VARCHAR2 )
         RETURN VARCHAR2
     IS
     BEGIN
         RETURN Self.get (cKey);
     EXCEPTION
         WHEN NO_DATA_FOUND THEN
              RETURN NULL;
     END getProperty;
--
--
--
     MEMBER FUNCTION getProperty ( Self      IN  T_Properties,
                                   cKey      IN  VARCHAR2,
                                   cDefault  IN  VARCHAR2 )
         RETURN VARCHAR2
     IS
     BEGIN
         RETURN Self.get (cKey);
     EXCEPTION
         WHEN NO_DATA_FOUND THEN
              RETURN cDefault;
     END getProperty;
--
--
--
     MEMBER PROCEDURE put ( Self    IN OUT NOCOPY T_Properties,
                            cKey    IN            VARCHAR2,
                            cValue  IN            VARCHAR2 )
     IS
         i_find  INTEGER := Self.find (cKey);
     BEGIN

         IF i_find > 0 THEN
            tabItem (i_find).cValue := cValue;
         ELSE
            tabItem.Extend ();
            tabItem (tabItem.Count ()) := T_Properties_Item (cKey, cValue);
         END IF;

     END put;
--
--
--
     MEMBER PROCEDURE remove ( Self  IN OUT NOCOPY T_Properties,
                               cKey  IN            VARCHAR2 )
     IS
         i_find  INTEGER := Self.find (cKey);
     BEGIN
         IF i_find > 0 THEN
            tabItem.Delete (i_find);
         END IF;
     END remove;
--
--
--
     MEMBER PROCEDURE clear ( Self  IN OUT NOCOPY T_Properties )
     IS
     BEGIN
         tabItem.Delete ();
     END clear;
--
--
--
END;
/

prompt
prompt Creating trigger FILL_FAKE_CUS
prompt ==============================
prompt
CREATE OR REPLACE TRIGGER XXI."FILL_FAKE_CUS"
  AFTER INSERT OR UPDATE ON "CUS"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
DECLARE
  cnt_doc number;
  cnt_cit number;
BEGIN

  --документ
  select count(*)
    into cnt_doc
    from cus_docum
   where cus_docum.icusnum = :new.icusnum
     and cus_docum.PREF = 'Y';

  if cnt_doc > 1 then
    raise_application_error(-20000,
                            'К гражданину может быть привязан только один документ с признаком "основной" ');
  end if;

  --гражданство
  select count(*)
    into cnt_cit
    from cus_citizen
   where cus_citizen.icusnum = :new.icusnum
     and cus_citizen.osn = 'Y';

  if cnt_cit > 1 then
    raise_application_error(-20000,
                            'К гражданину может быть привязано только одно гражданство с признаком "основное"');
  end if;

END FILL_FAKE_CUS;
/

prompt
prompt Creating trigger FR_QUERIES_BEFORE_INSERT
prompt =========================================
prompt
CREATE OR REPLACE TRIGGER XXI."FR_QUERIES_BEFORE_INSERT"
 BEFORE
  INSERT
 ON fr_queries
REFERENCING NEW AS NEW OLD AS OLD
 FOR EACH ROW
DECLARE
    new_id number(12);
BEGIN
    select FR_QUERIES_SEQ.NEXTVAL into new_id from dual;
    :NEW.QUERYID := new_id;
END;
/

prompt
prompt Creating trigger LOGOFF_AUDIT_TRIGGER
prompt =====================================
prompt
CREATE OR REPLACE TRIGGER XXI."LOGOFF_AUDIT_TRIGGER"
  BEFORE LOGOFF ON DATABASE
declare
  function is_usr(usr varchar2) return boolean is
    ret boolean;
    cnt number;
  begin
    select count(*)
      into cnt
      from usr
     where upper(usr.CUSRLOGNAME) = upper(usr);
    if cnt = 0 then
      ret := false;
    else
      ret := true;
    end if;
    return ret;
  end;
BEGIN
  if is_usr(user) /*and sys_context('userenv', 'ip_address') is not null*/
   then
    -- ***************************************************
    -- Update the last action accessed
    -- ***************************************************
    update stats$user_log
       set last_action =
           (select action
              from v$session
             where sys_context('USERENV', 'SESSIONID') = audsid)
     where sys_context('USERENV', 'SESSIONID') = session_id;
    --***************************************************
    -- Update the last program accessed
    -- ***************************************************
    update stats$user_log
       set last_program =
           (select program
              from v$session
             where sys_context('USERENV', 'SESSIONID') = audsid)
     where sys_context('USERENV', 'SESSIONID') = session_id;
    -- ***************************************************
    -- Update the last module accessed
    -- ***************************************************
    update stats$user_log
       set last_module =
           (select module
              from v$session
             where sys_context('USERENV', 'SESSIONID') = audsid)
     where sys_context('USERENV', 'SESSIONID') = session_id;
    -- ***************************************************
    -- Update the logoff day
    -- ***************************************************
    update stats$user_log
       set logoff_day = sysdate
     where sys_context('USERENV', 'SESSIONID') = session_id;
    -- ***************************************************
    -- Update the logoff time
    -- ***************************************************
    update stats$user_log
       set logoff_time = to_char(sysdate, 'hh24:mi:ss')
     where sys_context('USERENV', 'SESSIONID') = session_id;
    -- ***************************************************
    -- Compute the elapsed minutes
    -- ***************************************************
    update stats$user_log
       set elapsed_minutes = round((logoff_day - logon_day) * 1440)
     where sys_context('USERENV', 'SESSIONID') = session_id;
  end if;
END;
/

prompt
prompt Creating trigger LOGON_AUDIT_TRIGGER
prompt ====================================
prompt
CREATE OR REPLACE TRIGGER XXI."LOGON_AUDIT_TRIGGER"
  AFTER LOGON ON DATABASE
declare
  function is_usr(usr varchar2) return boolean is
    ret boolean;
    cnt number;
  begin
    select count(*)
      into cnt
      from usr
     where upper(usr.CUSRLOGNAME) = upper(usr);
    if cnt = 0 then
      ret := false;
    else
      ret := true;
    end if;
    return ret;
  end is_usr;
BEGIN

  if is_usr(user) /*and sys_context('userenv', 'ip_address') is not null*/
   then
    insert into stats$user_log
      (user_id,
       session_id,
       host,
       last_program,
       last_action,
       last_module,
       logon_day,
       logon_time,
       logoff_day,
       logoff_time,
       elapsed_minutes,
       ip,
       first_module,
       os_user)
    values
      (user,
       sys_context('USERENV', 'SESSIONID'),
       sys_context('USERENV', 'HOST'),
       null,
       null,
       null,
       sysdate,
       to_char(sysdate, 'hh24:mi:ss'),
       null,
       null,
       null,
       sys_context('userenv', 'ip_address'),
       sys_context('userenv', 'module'),
       SYS_CONTEXT('USERENV', 'OS_USER'));
  end if;

END;
/

prompt
prompt Creating trigger T_ADOPTOIN
prompt ===========================
prompt
CREATE OR REPLACE TRIGGER XXI."T_ADOPTOIN"
  before insert ON "ADOPTOIN"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  :new.ID := S_ADOPTOIN.NEXTVAL;
END T_ADOPTOIN;
/

prompt
prompt Creating trigger T_A_IDU_ADOPTOIN
prompt =================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_A_IDU_ADOPTOIN"
AFTER INSERT OR UPDATE OR DELETE 
ON "ADOPTOIN"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('ADOPTOIN') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'ADOPTOIN', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.ID, :NEW.ID), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ID', :NEW.ID, :OLD.ID); END IF;
IF UPDATING ('ZAGS_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ZAGS_ID', :NEW.ZAGS_ID, :OLD.ZAGS_ID); END IF;
IF UPDATING ('OLD_LASTNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'OLD_LASTNAME', :NEW.OLD_LASTNAME, :OLD.OLD_LASTNAME); END IF;
IF UPDATING ('OLD_FIRSTNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'OLD_FIRSTNAME', :NEW.OLD_FIRSTNAME, :OLD.OLD_FIRSTNAME); END IF;
IF UPDATING ('OLD_MIDDLNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'OLD_MIDDLNAME', :NEW.OLD_MIDDLNAME, :OLD.OLD_MIDDLNAME); END IF;
IF UPDATING ('NEW_LASTNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'NEW_LASTNAME', :NEW.NEW_LASTNAME, :OLD.NEW_LASTNAME); END IF;
IF UPDATING ('NEW_FIRSTNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'NEW_FIRSTNAME', :NEW.NEW_FIRSTNAME, :OLD.NEW_FIRSTNAME); END IF;
IF UPDATING ('NEW_MIDDLNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'NEW_MIDDLNAME', :NEW.NEW_MIDDLNAME, :OLD.NEW_MIDDLNAME); END IF;
IF UPDATING ('CUSID_CH') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CUSID_CH', :NEW.CUSID_CH, :OLD.CUSID_CH); END IF;
IF UPDATING ('DOC_DATE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DOC_DATE', :NEW.DOC_DATE, :OLD.DOC_DATE); END IF;
IF UPDATING ('OPER') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'OPER', :NEW.OPER, :OLD.OPER); END IF;
IF UPDATING ('CUSID_M') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CUSID_M', :NEW.CUSID_M, :OLD.CUSID_M); END IF;
IF UPDATING ('CUSID_F') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CUSID_F', :NEW.CUSID_F, :OLD.CUSID_F); END IF;
IF UPDATING ('BRNACT') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BRNACT', :NEW.BRNACT, :OLD.BRNACT); END IF;
IF UPDATING ('SVID_SERIA') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'SVID_SERIA', :NEW.SVID_SERIA, :OLD.SVID_SERIA); END IF;
IF UPDATING ('SVID_NOMER') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'SVID_NOMER', :NEW.SVID_NOMER, :OLD.SVID_NOMER); END IF;
IF UPDATING ('CUSID_M_AD') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CUSID_M_AD', :NEW.CUSID_M_AD, :OLD.CUSID_M_AD); END IF;
IF UPDATING ('CUSID_F_AD') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CUSID_F_AD', :NEW.CUSID_F_AD, :OLD.CUSID_F_AD); END IF;
IF UPDATING ('ADOPT_PARENTS') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ADOPT_PARENTS', :NEW.ADOPT_PARENTS, :OLD.ADOPT_PARENTS); END IF;
IF UPDATING ('ZAP_ISPOLKOM_RESH') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ZAP_ISPOLKOM_RESH', :NEW.ZAP_ISPOLKOM_RESH, :OLD.ZAP_ISPOLKOM_RESH); END IF;
IF UPDATING ('ZAP_SOVET_DEP_TRUD') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ZAP_SOVET_DEP_TRUD', :NEW.ZAP_SOVET_DEP_TRUD, :OLD.ZAP_SOVET_DEP_TRUD); END IF;
IF UPDATING ('ZAP_DATE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ZAP_DATE', :NEW.ZAP_DATE, :OLD.ZAP_DATE); END IF;
IF UPDATING ('ZAP_NUMBER') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ZAP_NUMBER', :NEW.ZAP_NUMBER, :OLD.ZAP_NUMBER); END IF;
IF UPDATING ('NEW_BRTH') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'NEW_BRTH', :NEW.NEW_BRTH, :OLD.NEW_BRTH); END IF;
IF UPDATING ('OLD_BRTH') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'OLD_BRTH', :NEW.OLD_BRTH, :OLD.OLD_BRTH); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_ADOPTOIN;
/

prompt
prompt Creating trigger T_A_IDU_BRN_BIRTH_ACT
prompt ======================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_A_IDU_BRN_BIRTH_ACT"
AFTER INSERT OR UPDATE OR DELETE 
ON "BRN_BIRTH_ACT"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('BRN_BIRTH_ACT') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'BRN_BIRTH_ACT', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.BR_ACT_ID, :NEW.BR_ACT_ID), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('BR_ACT_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_ID', :NEW.BR_ACT_ID, :OLD.BR_ACT_ID); END IF;
IF UPDATING ('BR_ACT_DATE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_DATE', :NEW.BR_ACT_DATE, :OLD.BR_ACT_DATE); END IF;
IF UPDATING ('BR_ACT_ZTP') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_ZTP', :NEW.BR_ACT_ZTP, :OLD.BR_ACT_ZTP); END IF;
IF UPDATING ('BR_ACT_BRCHCNT') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_BRCHCNT', :NEW.BR_ACT_BRCHCNT, :OLD.BR_ACT_BRCHCNT); END IF;
IF UPDATING ('BR_ACT_LD') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_LD', :NEW.BR_ACT_LD, :OLD.BR_ACT_LD); END IF;
IF UPDATING ('BR_ACT_ZGID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_ZGID', :NEW.BR_ACT_ZGID, :OLD.BR_ACT_ZGID); END IF;
IF UPDATING ('BR_ACT_USER') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_USER', :NEW.BR_ACT_USER, :OLD.BR_ACT_USER); END IF;
IF UPDATING ('BR_ACT_TGRABF') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_TGRABF', :NEW.BR_ACT_TGRABF, :OLD.BR_ACT_TGRABF); END IF;
IF UPDATING ('BR_ACT_MZDATE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_MZDATE', :NEW.BR_ACT_MZDATE, :OLD.BR_ACT_MZDATE); END IF;
IF UPDATING ('BR_ACT_DBF') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_DBF', :NEW.BR_ACT_DBF, :OLD.BR_ACT_DBF); END IF;
IF UPDATING ('BR_ACT_CH') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_CH', :NEW.BR_ACT_CH, :OLD.BR_ACT_CH); END IF;
IF UPDATING ('BR_ACT_F') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_F', :NEW.BR_ACT_F, :OLD.BR_ACT_F); END IF;
IF UPDATING ('BR_ACT_M') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_M', :NEW.BR_ACT_M, :OLD.BR_ACT_M); END IF;
IF UPDATING ('BR_ACT_MEDORGA') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_MEDORGA', :NEW.BR_ACT_MEDORGA, :OLD.BR_ACT_MEDORGA); END IF;
IF UPDATING ('BR_ACT_DATEDOCA') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_DATEDOCA', :NEW.BR_ACT_DATEDOCA, :OLD.BR_ACT_DATEDOCA); END IF;
IF UPDATING ('BR_ACT_NDOCA') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_NDOCA', :NEW.BR_ACT_NDOCA, :OLD.BR_ACT_NDOCA); END IF;
IF UPDATING ('BR_ACT_FIOB') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_FIOB', :NEW.BR_ACT_FIOB, :OLD.BR_ACT_FIOB); END IF;
IF UPDATING ('BR_ACT_DATEDOCB') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_DATEDOCB', :NEW.BR_ACT_DATEDOCB, :OLD.BR_ACT_DATEDOCB); END IF;
IF UPDATING ('BR_ACT_NAMECOURT') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_NAMECOURT', :NEW.BR_ACT_NAMECOURT, :OLD.BR_ACT_NAMECOURT); END IF;
IF UPDATING ('BR_ACT_DESCCOURT') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_DESCCOURT', :NEW.BR_ACT_DESCCOURT, :OLD.BR_ACT_DESCCOURT); END IF;
IF UPDATING ('BR_ACT_DCOURT') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_DCOURT', :NEW.BR_ACT_DCOURT, :OLD.BR_ACT_DCOURT); END IF;
IF UPDATING ('BR_ACT_FADFIRST_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_FADFIRST_NAME', :NEW.BR_ACT_FADFIRST_NAME, :OLD.BR_ACT_FADFIRST_NAME); END IF;
IF UPDATING ('BR_ACT_FADLAST_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_FADLAST_NAME', :NEW.BR_ACT_FADLAST_NAME, :OLD.BR_ACT_FADLAST_NAME); END IF;
IF UPDATING ('BR_ACT_FADMIDDLE_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_FADMIDDLE_NAME', :NEW.BR_ACT_FADMIDDLE_NAME, :OLD.BR_ACT_FADMIDDLE_NAME); END IF;
IF UPDATING ('BR_ACT_FADLOCATION') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_FADLOCATION', :NEW.BR_ACT_FADLOCATION, :OLD.BR_ACT_FADLOCATION); END IF;
IF UPDATING ('BR_ACT_FADORG_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_FADORG_NAME', :NEW.BR_ACT_FADORG_NAME, :OLD.BR_ACT_FADORG_NAME); END IF;
IF UPDATING ('BR_ACT_FADREG_ADR') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_FADREG_ADR', :NEW.BR_ACT_FADREG_ADR, :OLD.BR_ACT_FADREG_ADR); END IF;
IF UPDATING ('BR_ACT_MERCER_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_MERCER_ID', :NEW.BR_ACT_MERCER_ID, :OLD.BR_ACT_MERCER_ID); END IF;
IF UPDATING ('BR_ACT_NUM') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_NUM', :NEW.BR_ACT_NUM, :OLD.BR_ACT_NUM); END IF;
IF UPDATING ('BR_ACT_SERIA') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_SERIA', :NEW.BR_ACT_SERIA, :OLD.BR_ACT_SERIA); END IF;
IF UPDATING ('BR_ACT_PATCER') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BR_ACT_PATCER', :NEW.BR_ACT_PATCER, :OLD.BR_ACT_PATCER); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_BRN_BIRTH_ACT;
/

prompt
prompt Creating trigger T_A_IDU_CUS
prompt ============================
prompt
CREATE OR REPLACE TRIGGER XXI.T_A_IDU_CUS
AFTER INSERT OR UPDATE OR DELETE
ON "CUS"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('CUS') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'CUS', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.ICUSNUM, :NEW.ICUSNUM), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('AB_FIRST_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'AB_FIRST_NAME', :NEW.AB_FIRST_NAME, :OLD.AB_FIRST_NAME); END IF;
IF UPDATING ('AB_MIDDLE_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'AB_MIDDLE_NAME', :NEW.AB_MIDDLE_NAME, :OLD.AB_MIDDLE_NAME); END IF;
IF UPDATING ('AB_LAST_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'AB_LAST_NAME', :NEW.AB_LAST_NAME, :OLD.AB_LAST_NAME); END IF;
IF UPDATING ('AB_PLACE_BIRTH') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'AB_PLACE_BIRTH', :NEW.AB_PLACE_BIRTH, :OLD.AB_PLACE_BIRTH); END IF;
IF UPDATING ('ICUSNUM') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ICUSNUM', :NEW.ICUSNUM, :OLD.ICUSNUM); END IF;
IF UPDATING ('DCUSOPEN') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DCUSOPEN', :NEW.DCUSOPEN, :OLD.DCUSOPEN); END IF;
IF UPDATING ('DCUSEDIT') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DCUSEDIT', :NEW.DCUSEDIT, :OLD.DCUSEDIT); END IF;
IF UPDATING ('CCUSIDOPEN') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CCUSIDOPEN', :NEW.CCUSIDOPEN, :OLD.CCUSIDOPEN); END IF;
IF UPDATING ('CCUSNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CCUSNAME', :NEW.CCUSNAME, :OLD.CCUSNAME); END IF;
IF UPDATING ('CCUSCOUNTRY1') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CCUSCOUNTRY1', :NEW.CCUSCOUNTRY1, :OLD.CCUSCOUNTRY1); END IF;
IF UPDATING ('CCUSNAME_SH') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CCUSNAME_SH', :NEW.CCUSNAME_SH, :OLD.CCUSNAME_SH); END IF;
IF UPDATING ('DCUSBIRTHDAY') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DCUSBIRTHDAY', :NEW.DCUSBIRTHDAY, :OLD.DCUSBIRTHDAY); END IF;
IF UPDATING ('CCUSLAST_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CCUSLAST_NAME', :NEW.CCUSLAST_NAME, :OLD.CCUSLAST_NAME); END IF;
IF UPDATING ('CCUSFIRST_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CCUSFIRST_NAME', :NEW.CCUSFIRST_NAME, :OLD.CCUSFIRST_NAME); END IF;
IF UPDATING ('CCUSMIDDLE_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CCUSMIDDLE_NAME', :NEW.CCUSMIDDLE_NAME, :OLD.CCUSMIDDLE_NAME); END IF;
IF UPDATING ('CCUSSEX') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CCUSSEX', :NEW.CCUSSEX, :OLD.CCUSSEX); END IF;
IF UPDATING ('CCUSPLACE_BIRTH') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CCUSPLACE_BIRTH', :NEW.CCUSPLACE_BIRTH, :OLD.CCUSPLACE_BIRTH); END IF;
IF UPDATING ('ICUSOTD') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ICUSOTD', :NEW.ICUSOTD, :OLD.ICUSOTD); END IF;
IF UPDATING ('CCUS_OK_SM') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CCUS_OK_SM', :NEW.CCUS_OK_SM, :OLD.CCUS_OK_SM); END IF;
IF UPDATING ('CCUSNATIONALITY') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CCUSNATIONALITY', :NEW.CCUSNATIONALITY, :OLD.CCUSNATIONALITY); END IF;
IF UPDATING ('ID1C') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ID1C', :NEW.ID1C, :OLD.ID1C); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_CUS;
/

prompt
prompt Creating trigger T_A_IDU_CUS_ADDR
prompt =================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_A_IDU_CUS_ADDR"
AFTER INSERT OR UPDATE OR DELETE 
ON "CUS_ADDR"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('CUS_ADDR') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'CUS_ADDR', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.ID_ADDR, :NEW.ID_ADDR), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('ID_ADDR') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ID_ADDR', :NEW.ID_ADDR, :OLD.ID_ADDR); END IF;
IF UPDATING ('ICUSNUM') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ICUSNUM', :NEW.ICUSNUM, :OLD.ICUSNUM); END IF;
IF UPDATING ('ADDR_TYPE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ADDR_TYPE', :NEW.ADDR_TYPE, :OLD.ADDR_TYPE); END IF;
IF UPDATING ('CODE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CODE', :NEW.CODE, :OLD.CODE); END IF;
IF UPDATING ('COUNTRY') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'COUNTRY', :NEW.COUNTRY, :OLD.COUNTRY); END IF;
IF UPDATING ('POST_INDEX') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'POST_INDEX', :NEW.POST_INDEX, :OLD.POST_INDEX); END IF;
IF UPDATING ('REG_NUM') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'REG_NUM', :NEW.REG_NUM, :OLD.REG_NUM); END IF;
IF UPDATING ('AREA') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'AREA', :NEW.AREA, :OLD.AREA); END IF;
IF UPDATING ('REG_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'REG_NAME', :NEW.REG_NAME, :OLD.REG_NAME); END IF;
IF UPDATING ('CITY') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CITY', :NEW.CITY, :OLD.CITY); END IF;
IF UPDATING ('PUNCT_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PUNCT_NAME', :NEW.PUNCT_NAME, :OLD.PUNCT_NAME); END IF;
IF UPDATING ('CITY_TYPE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CITY_TYPE', :NEW.CITY_TYPE, :OLD.CITY_TYPE); END IF;
IF UPDATING ('AREA_TYPE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'AREA_TYPE', :NEW.AREA_TYPE, :OLD.AREA_TYPE); END IF;
IF UPDATING ('INFR_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'INFR_NAME', :NEW.INFR_NAME, :OLD.INFR_NAME); END IF;
IF UPDATING ('DOM') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DOM', :NEW.DOM, :OLD.DOM); END IF;
IF UPDATING ('PUNCT_TYPE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PUNCT_TYPE', :NEW.PUNCT_TYPE, :OLD.PUNCT_TYPE); END IF;
IF UPDATING ('KORP') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'KORP', :NEW.KORP, :OLD.KORP); END IF;
IF UPDATING ('STROY') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'STROY', :NEW.STROY, :OLD.STROY); END IF;
IF UPDATING ('INFR_TYPE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'INFR_TYPE', :NEW.INFR_TYPE, :OLD.INFR_TYPE); END IF;
IF UPDATING ('KV') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'KV', :NEW.KV, :OLD.KV); END IF;
IF UPDATING ('OFFICE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'OFFICE', :NEW.OFFICE, :OLD.OFFICE); END IF;
IF UPDATING ('PORCH') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PORCH', :NEW.PORCH, :OLD.PORCH); END IF;
IF UPDATING ('OKSM_CODE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'OKSM_CODE', :NEW.OKSM_CODE, :OLD.OKSM_CODE); END IF;
IF UPDATING ('ADDRESS_INLINE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ADDRESS_INLINE', :NEW.ADDRESS_INLINE, :OLD.ADDRESS_INLINE); END IF;
IF UPDATING ('STROY_TYPE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'STROY_TYPE', :NEW.STROY_TYPE, :OLD.STROY_TYPE); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_CUS_ADDR;
/

prompt
prompt Creating trigger T_A_IDU_CUS_CITIZEN
prompt ====================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_A_IDU_CUS_CITIZEN"
AFTER INSERT OR UPDATE OR DELETE 
ON "CUS_CITIZEN"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('CUS_CITIZEN') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'CUS_CITIZEN', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.ID, :NEW.ID), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ID', :NEW.ID, :OLD.ID); END IF;
IF UPDATING ('COUNTRY_CODE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'COUNTRY_CODE', :NEW.COUNTRY_CODE, :OLD.COUNTRY_CODE); END IF;
IF UPDATING ('COUNTRY_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'COUNTRY_NAME', :NEW.COUNTRY_NAME, :OLD.COUNTRY_NAME); END IF;
IF UPDATING ('ICUSNUM') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ICUSNUM', :NEW.ICUSNUM, :OLD.ICUSNUM); END IF;
IF UPDATING ('OSN') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'OSN', :NEW.OSN, :OLD.OSN); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_CUS_CITIZEN;
/

prompt
prompt Creating trigger T_A_IDU_CUS_DOCUM
prompt ==================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_A_IDU_CUS_DOCUM"
AFTER INSERT OR UPDATE OR DELETE 
ON "CUS_DOCUM"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('CUS_DOCUM') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'CUS_DOCUM', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.ID_DOC, :NEW.ID_DOC), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('ID_DOC') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ID_DOC', :NEW.ID_DOC, :OLD.ID_DOC); END IF;
IF UPDATING ('ICUSNUM') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ICUSNUM', :NEW.ICUSNUM, :OLD.ICUSNUM); END IF;
IF UPDATING ('PREF') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PREF', :NEW.PREF, :OLD.PREF); END IF;
IF UPDATING ('ID_DOC_TP') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ID_DOC_TP', :NEW.ID_DOC_TP, :OLD.ID_DOC_TP); END IF;
IF UPDATING ('DOC_NUM') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DOC_NUM', :NEW.DOC_NUM, :OLD.DOC_NUM); END IF;
IF UPDATING ('DOC_SER') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DOC_SER', :NEW.DOC_SER, :OLD.DOC_SER); END IF;
IF UPDATING ('DOC_DATE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DOC_DATE', :NEW.DOC_DATE, :OLD.DOC_DATE); END IF;
IF UPDATING ('DOC_AGENCY') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DOC_AGENCY', :NEW.DOC_AGENCY, :OLD.DOC_AGENCY); END IF;
IF UPDATING ('DOC_PERIOD') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DOC_PERIOD', :NEW.DOC_PERIOD, :OLD.DOC_PERIOD); END IF;
IF UPDATING ('DOC_SUBDIV') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DOC_SUBDIV', :NEW.DOC_SUBDIV, :OLD.DOC_SUBDIV); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_CUS_DOCUM;
/

prompt
prompt Creating trigger T_A_IDU_DEATH_CERT
prompt ===================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_A_IDU_DEATH_CERT"
AFTER INSERT OR UPDATE OR DELETE 
ON "DEATH_CERT"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('DEATH_CERT') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'DEATH_CERT', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.DC_ID, :NEW.DC_ID), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('DC_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_ID', :NEW.DC_ID, :OLD.DC_ID); END IF;
IF UPDATING ('DC_CUS') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_CUS', :NEW.DC_CUS, :OLD.DC_CUS); END IF;
IF UPDATING ('DC_DD') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_DD', :NEW.DC_DD, :OLD.DC_DD); END IF;
IF UPDATING ('DC_DPL') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_DPL', :NEW.DC_DPL, :OLD.DC_DPL); END IF;
IF UPDATING ('DC_CD') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_CD', :NEW.DC_CD, :OLD.DC_CD); END IF;
IF UPDATING ('DC_FNUM') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_FNUM', :NEW.DC_FNUM, :OLD.DC_FNUM); END IF;
IF UPDATING ('DC_FD') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_FD', :NEW.DC_FD, :OLD.DC_FD); END IF;
IF UPDATING ('DC_FTYPE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_FTYPE', :NEW.DC_FTYPE, :OLD.DC_FTYPE); END IF;
IF UPDATING ('DC_FMON') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_FMON', :NEW.DC_FMON, :OLD.DC_FMON); END IF;
IF UPDATING ('DC_RCNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_RCNAME', :NEW.DC_RCNAME, :OLD.DC_RCNAME); END IF;
IF UPDATING ('DC_NRNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_NRNAME', :NEW.DC_NRNAME, :OLD.DC_NRNAME); END IF;
IF UPDATING ('DC_LLOC') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_LLOC', :NEW.DC_LLOC, :OLD.DC_LLOC); END IF;
IF UPDATING ('DC_ZTP') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_ZTP', :NEW.DC_ZTP, :OLD.DC_ZTP); END IF;
IF UPDATING ('DC_FADFIRST_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_FADFIRST_NAME', :NEW.DC_FADFIRST_NAME, :OLD.DC_FADFIRST_NAME); END IF;
IF UPDATING ('DC_FADLAST_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_FADLAST_NAME', :NEW.DC_FADLAST_NAME, :OLD.DC_FADLAST_NAME); END IF;
IF UPDATING ('DC_FADMIDDLE_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_FADMIDDLE_NAME', :NEW.DC_FADMIDDLE_NAME, :OLD.DC_FADMIDDLE_NAME); END IF;
IF UPDATING ('DC_FADLOCATION') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_FADLOCATION', :NEW.DC_FADLOCATION, :OLD.DC_FADLOCATION); END IF;
IF UPDATING ('DC_FADORG_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_FADORG_NAME', :NEW.DC_FADORG_NAME, :OLD.DC_FADORG_NAME); END IF;
IF UPDATING ('DC_FADREG_ADR') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_FADREG_ADR', :NEW.DC_FADREG_ADR, :OLD.DC_FADREG_ADR); END IF;
IF UPDATING ('DC_SERIA') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_SERIA', :NEW.DC_SERIA, :OLD.DC_SERIA); END IF;
IF UPDATING ('DC_NUMBER') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_NUMBER', :NEW.DC_NUMBER, :OLD.DC_NUMBER); END IF;
IF UPDATING ('DC_USR') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_USR', :NEW.DC_USR, :OLD.DC_USR); END IF;
IF UPDATING ('DC_OPEN') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_OPEN', :NEW.DC_OPEN, :OLD.DC_OPEN); END IF;
IF UPDATING ('DC_ZAGS') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DC_ZAGS', :NEW.DC_ZAGS, :OLD.DC_ZAGS); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_DEATH_CERT;
/

prompt
prompt Creating trigger T_A_IDU_DIVORCE_CERT
prompt =====================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_A_IDU_DIVORCE_CERT"
AFTER INSERT OR UPDATE OR DELETE 
ON "DIVORCE_CERT"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('DIVORCE_CERT') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'DIVORCE_CERT', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.DIVC_ID, :NEW.DIVC_ID), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('DIVC_ZLNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_ZLNAME', :NEW.DIVC_ZLNAME, :OLD.DIVC_ZLNAME); END IF;
IF UPDATING ('DIVC_ZАNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_ZАNAME', :NEW.DIVC_ZАNAME, :OLD.DIVC_ZАNAME); END IF;
IF UPDATING ('DIVC_ZMNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_ZMNAME', :NEW.DIVC_ZMNAME, :OLD.DIVC_ZMNAME); END IF;
IF UPDATING ('DIVC_ZPLACE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_ZPLACE', :NEW.DIVC_ZPLACE, :OLD.DIVC_ZPLACE); END IF;
IF UPDATING ('DIVC_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_ID', :NEW.DIVC_ID, :OLD.DIVC_ID); END IF;
IF UPDATING ('DIVC_HE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_HE', :NEW.DIVC_HE, :OLD.DIVC_HE); END IF;
IF UPDATING ('DIVC_SHE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_SHE', :NEW.DIVC_SHE, :OLD.DIVC_SHE); END IF;
IF UPDATING ('DIVC_HE_LNBEF') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_HE_LNBEF', :NEW.DIVC_HE_LNBEF, :OLD.DIVC_HE_LNBEF); END IF;
IF UPDATING ('DIVC_HE_LNAFT') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_HE_LNAFT', :NEW.DIVC_HE_LNAFT, :OLD.DIVC_HE_LNAFT); END IF;
IF UPDATING ('DIVC_SHE_LNBEF') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_SHE_LNBEF', :NEW.DIVC_SHE_LNBEF, :OLD.DIVC_SHE_LNBEF); END IF;
IF UPDATING ('DIVC_SHE_LNAFT') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_SHE_LNAFT', :NEW.DIVC_SHE_LNAFT, :OLD.DIVC_SHE_LNAFT); END IF;
IF UPDATING ('DIVC_DATE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_DATE', :NEW.DIVC_DATE, :OLD.DIVC_DATE); END IF;
IF UPDATING ('DIVC_DT') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_DT', :NEW.DIVC_DT, :OLD.DIVC_DT); END IF;
IF UPDATING ('DIVC_USR') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_USR', :NEW.DIVC_USR, :OLD.DIVC_USR); END IF;
IF UPDATING ('DIVC_TYPE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_TYPE', :NEW.DIVC_TYPE, :OLD.DIVC_TYPE); END IF;
IF UPDATING ('DIVC_TCHD') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_TCHD', :NEW.DIVC_TCHD, :OLD.DIVC_TCHD); END IF;
IF UPDATING ('DIVC_TCHNUM') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_TCHNUM', :NEW.DIVC_TCHNUM, :OLD.DIVC_TCHNUM); END IF;
IF UPDATING ('DIVC_CAN') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_CAN', :NEW.DIVC_CAN, :OLD.DIVC_CAN); END IF;
IF UPDATING ('DIVC_CAD') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_CAD', :NEW.DIVC_CAD, :OLD.DIVC_CAD); END IF;
IF UPDATING ('DIVC_ZOSCN') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_ZOSCN', :NEW.DIVC_ZOSCN, :OLD.DIVC_ZOSCN); END IF;
IF UPDATING ('DIVC_ZOSCD') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_ZOSCD', :NEW.DIVC_ZOSCD, :OLD.DIVC_ZOSCD); END IF;
IF UPDATING ('DIVC_ZOSFIO') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_ZOSFIO', :NEW.DIVC_ZOSFIO, :OLD.DIVC_ZOSFIO); END IF;
IF UPDATING ('DIVC_ZOSCN2') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_ZOSCN2', :NEW.DIVC_ZOSCN2, :OLD.DIVC_ZOSCN2); END IF;
IF UPDATING ('DIVC_ZOSCD2') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_ZOSCD2', :NEW.DIVC_ZOSCD2, :OLD.DIVC_ZOSCD2); END IF;
IF UPDATING ('DIVC_ZOSFIO2') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_ZOSFIO2', :NEW.DIVC_ZOSFIO2, :OLD.DIVC_ZOSFIO2); END IF;
IF UPDATING ('DIVC_ZOSPRISON') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_ZOSPRISON', :NEW.DIVC_ZOSPRISON, :OLD.DIVC_ZOSPRISON); END IF;
IF UPDATING ('DIVC_MC_MERCER') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_MC_MERCER', :NEW.DIVC_MC_MERCER, :OLD.DIVC_MC_MERCER); END IF;
IF UPDATING ('DIVC_NUM') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_NUM', :NEW.DIVC_NUM, :OLD.DIVC_NUM); END IF;
IF UPDATING ('DIVC_SERIA') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_SERIA', :NEW.DIVC_SERIA, :OLD.DIVC_SERIA); END IF;
IF UPDATING ('DIVC_ZAGS') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DIVC_ZAGS', :NEW.DIVC_ZAGS, :OLD.DIVC_ZAGS); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_DIVORCE_CERT;
/

prompt
prompt Creating trigger T_A_IDU_MC_MERCER
prompt ==================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_A_IDU_MC_MERCER"
AFTER INSERT OR UPDATE OR DELETE 
ON "MC_MERCER"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('MC_MERCER') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'MC_MERCER', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.MERCER_ID, :NEW.MERCER_ID), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('MERCER_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MERCER_ID', :NEW.MERCER_ID, :OLD.MERCER_ID); END IF;
IF UPDATING ('MERCER_HE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MERCER_HE', :NEW.MERCER_HE, :OLD.MERCER_HE); END IF;
IF UPDATING ('MERCER_SHE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MERCER_SHE', :NEW.MERCER_SHE, :OLD.MERCER_SHE); END IF;
IF UPDATING ('MERCER_HE_LNBEF') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MERCER_HE_LNBEF', :NEW.MERCER_HE_LNBEF, :OLD.MERCER_HE_LNBEF); END IF;
IF UPDATING ('MERCER_HE_LNAFT') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MERCER_HE_LNAFT', :NEW.MERCER_HE_LNAFT, :OLD.MERCER_HE_LNAFT); END IF;
IF UPDATING ('MERCER_SHE_LNBEF') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MERCER_SHE_LNBEF', :NEW.MERCER_SHE_LNBEF, :OLD.MERCER_SHE_LNBEF); END IF;
IF UPDATING ('MERCER_SHE_LNBAFT') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MERCER_SHE_LNBAFT', :NEW.MERCER_SHE_LNBAFT, :OLD.MERCER_SHE_LNBAFT); END IF;
IF UPDATING ('MERCER_HEAGE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MERCER_HEAGE', :NEW.MERCER_HEAGE, :OLD.MERCER_HEAGE); END IF;
IF UPDATING ('MERCER_SHEAGE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MERCER_SHEAGE', :NEW.MERCER_SHEAGE, :OLD.MERCER_SHEAGE); END IF;
IF UPDATING ('MERCER_DATE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MERCER_DATE', :NEW.MERCER_DATE, :OLD.MERCER_DATE); END IF;
IF UPDATING ('MERCER_USR') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MERCER_USR', :NEW.MERCER_USR, :OLD.MERCER_USR); END IF;
IF UPDATING ('MERCER_ZAGS') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MERCER_ZAGS', :NEW.MERCER_ZAGS, :OLD.MERCER_ZAGS); END IF;
IF UPDATING ('MERCER_DIVSHE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MERCER_DIVSHE', :NEW.MERCER_DIVSHE, :OLD.MERCER_DIVSHE); END IF;
IF UPDATING ('MERCER_DIVHE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MERCER_DIVHE', :NEW.MERCER_DIVHE, :OLD.MERCER_DIVHE); END IF;
IF UPDATING ('MERCER_DSPMT_HE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MERCER_DSPMT_HE', :NEW.MERCER_DSPMT_HE, :OLD.MERCER_DSPMT_HE); END IF;
IF UPDATING ('MERCER_NUM') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MERCER_NUM', :NEW.MERCER_NUM, :OLD.MERCER_NUM); END IF;
IF UPDATING ('MERCER_SERIA') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MERCER_SERIA', :NEW.MERCER_SERIA, :OLD.MERCER_SERIA); END IF;
IF UPDATING ('MERCER_DIESHE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MERCER_DIESHE', :NEW.MERCER_DIESHE, :OLD.MERCER_DIESHE); END IF;
IF UPDATING ('MERCER_DIEHE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MERCER_DIEHE', :NEW.MERCER_DIEHE, :OLD.MERCER_DIEHE); END IF;
IF UPDATING ('MERCER_OTHER') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MERCER_OTHER', :NEW.MERCER_OTHER, :OLD.MERCER_OTHER); END IF;
IF UPDATING ('MERCER_DSPMT_SHE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MERCER_DSPMT_SHE', :NEW.MERCER_DSPMT_SHE, :OLD.MERCER_DSPMT_SHE); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_MC_MERCER;
/

prompt
prompt Creating trigger T_A_IDU_NATIONALITY
prompt ====================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_A_IDU_NATIONALITY"
AFTER INSERT OR UPDATE OR DELETE 
ON "NATIONALITY"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('NATIONALITY') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'NATIONALITY', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.ID, :NEW.ID), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ID', :NEW.ID, :OLD.ID); END IF;
IF UPDATING ('NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'NAME', :NEW.NAME, :OLD.NAME); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_NATIONALITY;
/

prompt
prompt Creating trigger T_A_IDU_NOTARY
prompt ===============================
prompt
CREATE OR REPLACE TRIGGER XXI.T_A_IDU_NOTARY
AFTER INSERT OR UPDATE OR DELETE
ON "NOTARY"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('NOTARY') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'NOTARY', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.NOT_ID, :NEW.NOT_ID), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('NOT_ADDRESS') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'NOT_ADDRESS', :NEW.NOT_ADDRESS, :OLD.NOT_ADDRESS); END IF;
IF UPDATING ('NOT_TELEPHONE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'NOT_TELEPHONE', :NEW.NOT_TELEPHONE, :OLD.NOT_TELEPHONE); END IF;
IF UPDATING ('NOT_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'NOT_ID', :NEW.NOT_ID, :OLD.NOT_ID); END IF;
IF UPDATING ('NOT_OTD') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'NOT_OTD', :NEW.NOT_OTD, :OLD.NOT_OTD); END IF;
IF UPDATING ('NOT_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'NOT_NAME', :NEW.NOT_NAME, :OLD.NOT_NAME); END IF;
IF UPDATING ('NOT_RUK') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'NOT_RUK', :NEW.NOT_RUK, :OLD.NOT_RUK); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_NOTARY;
/

prompt
prompt Creating trigger T_A_IDU_ODB_ACTION
prompt ===================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_A_IDU_ODB_ACTION"
AFTER INSERT OR UPDATE OR DELETE 
ON "ODB_ACTION"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('ODB_ACTION') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'ODB_ACTION', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.ACT_ID, :NEW.ACT_ID), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('ACT_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ACT_ID', :NEW.ACT_ID, :OLD.ACT_ID); END IF;
IF UPDATING ('ACT_PARENT') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ACT_PARENT', :NEW.ACT_PARENT, :OLD.ACT_PARENT); END IF;
IF UPDATING ('ACT_NPP') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ACT_NPP', :NEW.ACT_NPP, :OLD.ACT_NPP); END IF;
IF UPDATING ('ACT_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ACT_NAME', :NEW.ACT_NAME, :OLD.ACT_NAME); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_ODB_ACTION;
/

prompt
prompt Creating trigger T_A_IDU_ODB_ACTUSR
prompt ===================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_A_IDU_ODB_ACTUSR"
AFTER INSERT OR UPDATE OR DELETE 
ON "ODB_ACTUSR"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('ODB_ACTUSR') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'ODB_ACTUSR', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.ROWID, :NEW.ROWID), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('USR_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'USR_ID', :NEW.USR_ID, :OLD.USR_ID); END IF;
IF UPDATING ('ODBACT_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ODBACT_ID', :NEW.ODBACT_ID, :OLD.ODBACT_ID); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_ODB_ACTUSR;
/

prompt
prompt Creating trigger T_A_IDU_ODB_MNU
prompt ================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_A_IDU_ODB_MNU"
AFTER INSERT OR UPDATE OR DELETE 
ON "ODB_MNU"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('ODB_MNU') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'ODB_MNU', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.MNU_ID, :NEW.MNU_ID), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('MNU_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MNU_ID', :NEW.MNU_ID, :OLD.MNU_ID); END IF;
IF UPDATING ('MNU_PARENT') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MNU_PARENT', :NEW.MNU_PARENT, :OLD.MNU_PARENT); END IF;
IF UPDATING ('MNU_NPP') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MNU_NPP', :NEW.MNU_NPP, :OLD.MNU_NPP); END IF;
IF UPDATING ('MNU_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MNU_NAME', :NEW.MNU_NAME, :OLD.MNU_NAME); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_ODB_MNU;
/

prompt
prompt Creating trigger T_A_IDU_ODB_MNUUSR
prompt ===================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_A_IDU_ODB_MNUUSR"
  AFTER INSERT OR UPDATE OR DELETE ON "ODB_MNUUSR"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
DECLARE
  OP_CODE             CHAR(1);
  New_ID              AU_ACTION.IACTION_ID%TYPE;
  tabData             AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION            VARCHAR2(64);
  V_MODULE            VARCHAR2(64);
  V_OSUSER            VARCHAR2(64);
  V_USER              VARCHAR2(64);
  V_CLIENT_IDENTIFIER VARCHAR2(64);
BEGIN
  /*---*
  * шаблон аудиторского триггера для "аудита справочников"
  * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
  *---*/

  V_SESSION := UserEnv('SessionID');
  dbms_application_info.read_module(module_name => V_MODULE,
                                    action_name => V_ACTION);
  Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
    into V_MACHINE,
         V_MODULE,
         V_OSUSER,
         V_USER,
         V_PROGRAM,
         V_CLIENT_IDENTIFIER
    from sys.v_$session
   where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('ODB_MNUUSR') != 'Y' THEN
    RETURN;
  END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
    (IACTION_ID,
     CAUDMACHINE,
     CAUDPROGRAM,
     CAUDOPERATION,
     CTABLE,
     RROWID,
     CAUDACTION,
     CAUDMODULE,
     ID_NUM,
     ID_ANUM)
  VALUES
    (S_AU_ACTION.NextVal,
     V_MACHINE,
     V_PROGRAM,
     OP_CODE,
     'ODB_MNUUSR',
     NVL(:NEW.ROWID, :OLD.ROWID),
     V_ACTION,
     V_MODULE,
     Decode(OP_Code, 'D', :OLD.ROWID, :NEW.ROWID),
     Decode(OP_Code, 'D', null, null))
  RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu();

  IF UPDATING('USR_ID') OR INSERTING OR DELETING THEN
    AUP_UTIL.Put_Data(tabData, 'USR_ID', :NEW.USR_ID, :OLD.USR_ID);
  END IF;
  IF UPDATING('ODBMNU_ID') OR INSERTING OR DELETING THEN
    AUP_UTIL.Put_Data(tabData, 'ODBMNU_ID', :NEW.ODBMNU_ID, :OLD.ODBMNU_ID);
  END IF;

  AUP_UTIL.Put_Data(New_ID, tabData);

END T_A_IDU_ODB_MNUUSR;
/

prompt
prompt Creating trigger T_A_IDU_OTD
prompt ============================
prompt
CREATE OR REPLACE TRIGGER XXI."T_A_IDU_OTD"
AFTER INSERT OR UPDATE OR DELETE 
ON "OTD"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('OTD') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'OTD', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.IOTDNUM, :NEW.IOTDNUM), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('IOTDNUM') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'IOTDNUM', :NEW.IOTDNUM, :OLD.IOTDNUM); END IF;
IF UPDATING ('COTDNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'COTDNAME', :NEW.COTDNAME, :OLD.COTDNAME); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_OTD;
/

prompt
prompt Creating trigger T_A_IDU_PATERN_CERT
prompt ====================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_A_IDU_PATERN_CERT"
AFTER INSERT OR UPDATE OR DELETE 
ON "PATERN_CERT"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('PATERN_CERT') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'PATERN_CERT', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.PC_ID, :NEW.PC_ID), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('PC_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PC_ID', :NEW.PC_ID, :OLD.PC_ID); END IF;
IF UPDATING ('PC_ACT_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PC_ACT_ID', :NEW.PC_ACT_ID, :OLD.PC_ACT_ID); END IF;
IF UPDATING ('PС_AFT_LNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PС_AFT_LNAME', :NEW.PС_AFT_LNAME, :OLD.PС_AFT_LNAME); END IF;
IF UPDATING ('PС_AFT_FNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PС_AFT_FNAME', :NEW.PС_AFT_FNAME, :OLD.PС_AFT_FNAME); END IF;
IF UPDATING ('PС_AFT_MNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PС_AFT_MNAME', :NEW.PС_AFT_MNAME, :OLD.PС_AFT_MNAME); END IF;
IF UPDATING ('PС_CH') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PС_CH', :NEW.PС_CH, :OLD.PС_CH); END IF;
IF UPDATING ('PС_F') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PС_F', :NEW.PС_F, :OLD.PС_F); END IF;
IF UPDATING ('PС_M') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PС_M', :NEW.PС_M, :OLD.PС_M); END IF;
IF UPDATING ('PС_TYPE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PС_TYPE', :NEW.PС_TYPE, :OLD.PС_TYPE); END IF;
IF UPDATING ('PС_TRZ') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PС_TRZ', :NEW.PС_TRZ, :OLD.PС_TRZ); END IF;
IF UPDATING ('PС_FZ') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PС_FZ', :NEW.PС_FZ, :OLD.PС_FZ); END IF;
IF UPDATING ('PС_CRNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PС_CRNAME', :NEW.PС_CRNAME, :OLD.PС_CRNAME); END IF;
IF UPDATING ('PС_CRDATE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PС_CRDATE', :NEW.PС_CRDATE, :OLD.PС_CRDATE); END IF;
IF UPDATING ('PС_SERIA') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PС_SERIA', :NEW.PС_SERIA, :OLD.PС_SERIA); END IF;
IF UPDATING ('PС_NUMBER') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PС_NUMBER', :NEW.PС_NUMBER, :OLD.PС_NUMBER); END IF;
IF UPDATING ('PС_DATE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PС_DATE', :NEW.PС_DATE, :OLD.PС_DATE); END IF;
IF UPDATING ('PС_USER') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PС_USER', :NEW.PС_USER, :OLD.PС_USER); END IF;
IF UPDATING ('PС_ZAGS') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PС_ZAGS', :NEW.PС_ZAGS, :OLD.PС_ZAGS); END IF;
IF UPDATING ('PC_ZMNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PC_ZMNAME', :NEW.PC_ZMNAME, :OLD.PC_ZMNAME); END IF;
IF UPDATING ('PC_ZFNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PC_ZFNAME', :NEW.PC_ZFNAME, :OLD.PC_ZFNAME); END IF;
IF UPDATING ('PC_ZLNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PC_ZLNAME', :NEW.PC_ZLNAME, :OLD.PC_ZLNAME); END IF;
IF UPDATING ('PC_ZPLACE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PC_ZPLACE', :NEW.PC_ZPLACE, :OLD.PC_ZPLACE); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_PATERN_CERT;
/

prompt
prompt Creating trigger T_A_IDU_PRJ_FL_VER_HIST
prompt ========================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_A_IDU_PRJ_FL_VER_HIST"
AFTER INSERT OR UPDATE OR DELETE 
ON "PRJ_FL_VER_HIST"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('PRJ_FL_VER_HIST') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'PRJ_FL_VER_HIST', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.ROWID, :NEW.ROWID), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('PRJ_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PRJ_ID', :NEW.PRJ_ID, :OLD.PRJ_ID); END IF;
IF UPDATING ('VERISION') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'VERISION', :NEW.VERISION, :OLD.VERISION); END IF;
IF UPDATING ('DT') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DT', :NEW.DT, :OLD.DT); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_PRJ_FL_VER_HIST;
/

prompt
prompt Creating trigger T_A_IDU_PROJECT
prompt ================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_A_IDU_PROJECT"
AFTER INSERT OR UPDATE OR DELETE 
ON "PROJECT"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('PROJECT') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'PROJECT', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.PRJ_ID, :NEW.PRJ_ID), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('PRJ_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PRJ_ID', :NEW.PRJ_ID, :OLD.PRJ_ID); END IF;
IF UPDATING ('PRJ_PARENT') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PRJ_PARENT', :NEW.PRJ_PARENT, :OLD.PRJ_PARENT); END IF;
IF UPDATING ('BYTES') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BYTES', :NEW.BYTES, :OLD.BYTES); END IF;
IF UPDATING ('PRJ_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'PRJ_NAME', :NEW.PRJ_NAME, :OLD.PRJ_NAME); END IF;
IF UPDATING ('IS_FOLDER') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'IS_FOLDER', :NEW.IS_FOLDER, :OLD.IS_FOLDER); END IF;
IF UPDATING ('VERSION') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'VERSION', :NEW.VERSION, :OLD.VERSION); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_PROJECT;
/

prompt
prompt Creating trigger T_A_IDU_UPDATE_ABH_NAME
prompt ========================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_A_IDU_UPDATE_ABH_NAME"
AFTER INSERT OR UPDATE OR DELETE 
ON "UPDATE_ABH_NAME"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('UPDATE_ABH_NAME') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'UPDATE_ABH_NAME', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.ID, :NEW.ID), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('SVID_SERIA') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'SVID_SERIA', :NEW.SVID_SERIA, :OLD.SVID_SERIA); END IF;
IF UPDATING ('ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ID', :NEW.ID, :OLD.ID); END IF;
IF UPDATING ('OLD_LASTNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'OLD_LASTNAME', :NEW.OLD_LASTNAME, :OLD.OLD_LASTNAME); END IF;
IF UPDATING ('OLD_FIRSTNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'OLD_FIRSTNAME', :NEW.OLD_FIRSTNAME, :OLD.OLD_FIRSTNAME); END IF;
IF UPDATING ('OLD_MIDDLNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'OLD_MIDDLNAME', :NEW.OLD_MIDDLNAME, :OLD.OLD_MIDDLNAME); END IF;
IF UPDATING ('NEW_LASTNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'NEW_LASTNAME', :NEW.NEW_LASTNAME, :OLD.NEW_LASTNAME); END IF;
IF UPDATING ('NEW_FIRSTNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'NEW_FIRSTNAME', :NEW.NEW_FIRSTNAME, :OLD.NEW_FIRSTNAME); END IF;
IF UPDATING ('NEW_MIDDLNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'NEW_MIDDLNAME', :NEW.NEW_MIDDLNAME, :OLD.NEW_MIDDLNAME); END IF;
IF UPDATING ('BRN_ACT_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BRN_ACT_ID', :NEW.BRN_ACT_ID, :OLD.BRN_ACT_ID); END IF;
IF UPDATING ('DOC_DATE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DOC_DATE', :NEW.DOC_DATE, :OLD.DOC_DATE); END IF;
IF UPDATING ('OPER') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'OPER', :NEW.OPER, :OLD.OPER); END IF;
IF UPDATING ('ZAGS_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ZAGS_ID', :NEW.ZAGS_ID, :OLD.ZAGS_ID); END IF;
IF UPDATING ('CUSID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CUSID', :NEW.CUSID, :OLD.CUSID); END IF;
IF UPDATING ('SVID_NUMBER') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'SVID_NUMBER', :NEW.SVID_NUMBER, :OLD.SVID_NUMBER); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_UPDATE_ABH_NAME;
/

prompt
prompt Creating trigger T_A_IDU_UPDATE_NAME
prompt ====================================
prompt
CREATE OR REPLACE TRIGGER XXI.T_A_IDU_UPDATE_NAME
AFTER INSERT OR UPDATE OR DELETE
ON "UPDATE_NAME"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('UPDATE_NAME') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'UPDATE_NAME', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.ID, :NEW.ID), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('OLD_LASTNAME_AB') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'OLD_LASTNAME_AB', :NEW.OLD_LASTNAME_AB, :OLD.OLD_LASTNAME_AB); END IF;
IF UPDATING ('OLD_FIRSTNAME_AB') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'OLD_FIRSTNAME_AB', :NEW.OLD_FIRSTNAME_AB, :OLD.OLD_FIRSTNAME_AB); END IF;
IF UPDATING ('OLD_MIDDLNAME_AB') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'OLD_MIDDLNAME_AB', :NEW.OLD_MIDDLNAME_AB, :OLD.OLD_MIDDLNAME_AB); END IF;
IF UPDATING ('NEW_LASTNAME_AB') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'NEW_LASTNAME_AB', :NEW.NEW_LASTNAME_AB, :OLD.NEW_LASTNAME_AB); END IF;
IF UPDATING ('NEW_FIRSTNAME_AB') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'NEW_FIRSTNAME_AB', :NEW.NEW_FIRSTNAME_AB, :OLD.NEW_FIRSTNAME_AB); END IF;
IF UPDATING ('NEW_MIDDLNAME_AB') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'NEW_MIDDLNAME_AB', :NEW.NEW_MIDDLNAME_AB, :OLD.NEW_MIDDLNAME_AB); END IF;
IF UPDATING ('ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ID', :NEW.ID, :OLD.ID); END IF;
IF UPDATING ('OLD_LASTNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'OLD_LASTNAME', :NEW.OLD_LASTNAME, :OLD.OLD_LASTNAME); END IF;
IF UPDATING ('OLD_FIRSTNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'OLD_FIRSTNAME', :NEW.OLD_FIRSTNAME, :OLD.OLD_FIRSTNAME); END IF;
IF UPDATING ('OLD_MIDDLNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'OLD_MIDDLNAME', :NEW.OLD_MIDDLNAME, :OLD.OLD_MIDDLNAME); END IF;
IF UPDATING ('NEW_LASTNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'NEW_LASTNAME', :NEW.NEW_LASTNAME, :OLD.NEW_LASTNAME); END IF;
IF UPDATING ('NEW_FIRSTNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'NEW_FIRSTNAME', :NEW.NEW_FIRSTNAME, :OLD.NEW_FIRSTNAME); END IF;
IF UPDATING ('NEW_MIDDLNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'NEW_MIDDLNAME', :NEW.NEW_MIDDLNAME, :OLD.NEW_MIDDLNAME); END IF;
IF UPDATING ('BRN_ACT_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BRN_ACT_ID', :NEW.BRN_ACT_ID, :OLD.BRN_ACT_ID); END IF;
IF UPDATING ('DOC_DATE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DOC_DATE', :NEW.DOC_DATE, :OLD.DOC_DATE); END IF;
IF UPDATING ('OPER') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'OPER', :NEW.OPER, :OLD.OPER); END IF;
IF UPDATING ('ZAGS_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ZAGS_ID', :NEW.ZAGS_ID, :OLD.ZAGS_ID); END IF;
IF UPDATING ('CUSID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CUSID', :NEW.CUSID, :OLD.CUSID); END IF;
IF UPDATING ('SVID_NUMBER') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'SVID_NUMBER', :NEW.SVID_NUMBER, :OLD.SVID_NUMBER); END IF;
IF UPDATING ('SVID_SERIA') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'SVID_SERIA', :NEW.SVID_SERIA, :OLD.SVID_SERIA); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_UPDATE_NAME;
/

prompt
prompt Creating trigger T_A_IDU_UPD_NAT
prompt ================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_A_IDU_UPD_NAT"
AFTER INSERT OR UPDATE OR DELETE 
ON "UPD_NAT"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('UPD_NAT') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'UPD_NAT', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.ID, :NEW.ID), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ID', :NEW.ID, :OLD.ID); END IF;
IF UPDATING ('CUSID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CUSID', :NEW.CUSID, :OLD.CUSID); END IF;
IF UPDATING ('OPER') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'OPER', :NEW.OPER, :OLD.OPER); END IF;
IF UPDATING ('DOC_DATE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DOC_DATE', :NEW.DOC_DATE, :OLD.DOC_DATE); END IF;
IF UPDATING ('ZAGS_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ZAGS_ID', :NEW.ZAGS_ID, :OLD.ZAGS_ID); END IF;
IF UPDATING ('BRN_ACT_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'BRN_ACT_ID', :NEW.BRN_ACT_ID, :OLD.BRN_ACT_ID); END IF;
IF UPDATING ('OLD_NAT') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'OLD_NAT', :NEW.OLD_NAT, :OLD.OLD_NAT); END IF;
IF UPDATING ('NEW_NAT') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'NEW_NAT', :NEW.NEW_NAT, :OLD.NEW_NAT); END IF;
IF UPDATING ('FIO') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'FIO', :NEW.FIO, :OLD.FIO); END IF;
IF UPDATING ('SVID_SERIA') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'SVID_SERIA', :NEW.SVID_SERIA, :OLD.SVID_SERIA); END IF;
IF UPDATING ('SVID_NUMBER') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'SVID_NUMBER', :NEW.SVID_NUMBER, :OLD.SVID_NUMBER); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_UPD_NAT;
/

prompt
prompt Creating trigger T_A_IDU_USR
prompt ============================
prompt
CREATE OR REPLACE TRIGGER XXI.T_A_IDU_USR
AFTER INSERT OR UPDATE OR DELETE
ON "USR"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('USR') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'USR', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.IUSRID, :NEW.IUSRID), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('FIO_SH') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'FIO_SH', :NEW.FIO_SH, :OLD.FIO_SH); END IF;
IF UPDATING ('FIO_ABH_SH') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'FIO_ABH_SH', :NEW.FIO_ABH_SH, :OLD.FIO_ABH_SH); END IF;
IF UPDATING ('FIO_ABH') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'FIO_ABH', :NEW.FIO_ABH, :OLD.FIO_ABH); END IF;
IF UPDATING ('IUSRID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'IUSRID', :NEW.IUSRID, :OLD.IUSRID); END IF;
IF UPDATING ('CUSRLOGNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CUSRLOGNAME', :NEW.CUSRLOGNAME, :OLD.CUSRLOGNAME); END IF;
IF UPDATING ('CUSRNAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CUSRNAME', :NEW.CUSRNAME, :OLD.CUSRNAME); END IF;
IF UPDATING ('CUSRPOSITION') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CUSRPOSITION', :NEW.CUSRPOSITION, :OLD.CUSRPOSITION); END IF;
IF UPDATING ('DUSRHIRE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DUSRHIRE', :NEW.DUSRHIRE, :OLD.DUSRHIRE); END IF;
IF UPDATING ('IUSRBRANCH') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'IUSRBRANCH', :NEW.IUSRBRANCH, :OLD.IUSRBRANCH); END IF;
IF UPDATING ('DUSRFIRE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'DUSRFIRE', :NEW.DUSRFIRE, :OLD.DUSRFIRE); END IF;
IF UPDATING ('IUSRPWD_LENGTH') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'IUSRPWD_LENGTH', :NEW.IUSRPWD_LENGTH, :OLD.IUSRPWD_LENGTH); END IF;
IF UPDATING ('IUSRCHR_QUANTITY') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'IUSRCHR_QUANTITY', :NEW.IUSRCHR_QUANTITY, :OLD.IUSRCHR_QUANTITY); END IF;
IF UPDATING ('IUSRNUM_QUANTITY') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'IUSRNUM_QUANTITY', :NEW.IUSRNUM_QUANTITY, :OLD.IUSRNUM_QUANTITY); END IF;
IF UPDATING ('IUSREXP_DAYS') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'IUSREXP_DAYS', :NEW.IUSREXP_DAYS, :OLD.IUSREXP_DAYS); END IF;
IF UPDATING ('CUSROFFPHONE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CUSROFFPHONE', :NEW.CUSROFFPHONE, :OLD.CUSROFFPHONE); END IF;
IF UPDATING ('TWRTSTART') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'TWRTSTART', :NEW.TWRTSTART, :OLD.TWRTSTART); END IF;
IF UPDATING ('TWRTEND') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'TWRTEND', :NEW.TWRTEND, :OLD.TWRTEND); END IF;
IF UPDATING ('CEMAIL') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CEMAIL', :NEW.CEMAIL, :OLD.CEMAIL); END IF;
IF UPDATING ('CRESTRICT_TERM') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'CRESTRICT_TERM', :NEW.CRESTRICT_TERM, :OLD.CRESTRICT_TERM); END IF;
IF UPDATING ('IUSRPWDREUSE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'IUSRPWDREUSE', :NEW.IUSRPWDREUSE, :OLD.IUSRPWDREUSE); END IF;
IF UPDATING ('IUSRSPEC_QUANTITY') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'IUSRSPEC_QUANTITY', :NEW.IUSRSPEC_QUANTITY, :OLD.IUSRSPEC_QUANTITY); END IF;
IF UPDATING ('WELCOME_MESSAGE') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'WELCOME_MESSAGE', :NEW.WELCOME_MESSAGE, :OLD.WELCOME_MESSAGE); END IF;
IF UPDATING ('SHORT_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'SHORT_NAME', :NEW.SHORT_NAME, :OLD.SHORT_NAME); END IF;
IF UPDATING ('LOCK_DATE_TIME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'LOCK_DATE_TIME', :NEW.LOCK_DATE_TIME, :OLD.LOCK_DATE_TIME); END IF;
IF UPDATING ('LOCK_INFO') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'LOCK_INFO', :NEW.LOCK_INFO, :OLD.LOCK_INFO); END IF;
IF UPDATING ('MUST_CHANGE_PASSWORD') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'MUST_CHANGE_PASSWORD', :NEW.MUST_CHANGE_PASSWORD, :OLD.MUST_CHANGE_PASSWORD); END IF;
IF UPDATING ('SHORT_POSITION') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'SHORT_POSITION', :NEW.SHORT_POSITION, :OLD.SHORT_POSITION); END IF;
IF UPDATING ('WORKDAY_TIME_END') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'WORKDAY_TIME_END', :NEW.WORKDAY_TIME_END, :OLD.WORKDAY_TIME_END); END IF;
IF UPDATING ('WORKDAY_TIME_BEGIN') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'WORKDAY_TIME_BEGIN', :NEW.WORKDAY_TIME_BEGIN, :OLD.WORKDAY_TIME_BEGIN); END IF;
IF UPDATING ('ZAGS_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ZAGS_ID', :NEW.ZAGS_ID, :OLD.ZAGS_ID); END IF;
IF UPDATING ('NOTARY_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'NOTARY_ID', :NEW.NOTARY_ID, :OLD.NOTARY_ID); END IF;
IF UPDATING ('ACCESS_LEVEL') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ACCESS_LEVEL', :NEW.ACCESS_LEVEL, :OLD.ACCESS_LEVEL); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_USR;
/

prompt
prompt Creating trigger T_A_IDU_ZAGS
prompt =============================
prompt
CREATE OR REPLACE TRIGGER XXI.T_A_IDU_ZAGS
AFTER INSERT OR UPDATE OR DELETE
ON "ZAGS"
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
  OP_CODE CHAR(1);
  New_ID AU_ACTION.IACTION_ID%TYPE;
  tabData AUP_UTIL.T_TabAu;
  V_SESSION           NUMBER;
  V_MACHINE           VARCHAR2(64);
  V_PROGRAM           VARCHAR2(64);
  V_ACTION           VARCHAR2(64);
  V_MODULE           VARCHAR2(64);
  V_OSUSER           VARCHAR2(64);
  V_USER           VARCHAR2(64);
  V_CLIENT_IDENTIFIER           VARCHAR2(64);
BEGIN
  /*---*
   * шаблон аудиторского триггера для "аудита справочников"
   * $Id: V_AU_TRIGGER.SQL,v 2.6 2015/08/26 08:11:34 psh500 Exp $
   *---*/

V_SESSION := UserEnv('SessionID');
dbms_application_info.read_module(module_name => V_MODULE,action_name => V_ACTION);
Select MACHINE, MODULE, OSUSER, USERNAME, PROGRAM, CLIENT_IDENTIFIER
into V_MACHINE,V_MODULE,V_OSUSER,V_USER,V_PROGRAM,V_CLIENT_IDENTIFIER
from sys.v_$session
where audsid = V_SESSION;
  IF AUP_UTIL.Get_Mode('ZAGS') != 'Y' THEN RETURN; END IF;

  IF INSERTING THEN
    OP_CODE := 'I';
  ELSIF UPDATING THEN
    OP_CODE := 'U';
  ELSIF DELETING THEN
    OP_CODE := 'D';
  ELSE
    OP_CODE := 'J';
  END IF;

  INSERT INTO AU_ACTION
     (IACTION_ID, CAUDMACHINE, CAUDPROGRAM, CAUDOPERATION, CTABLE, RROWID, CAUDACTION, CAUDMODULE, ID_NUM, ID_ANUM)
     VALUES
     (S_AU_ACTION.NextVal, V_MACHINE, V_PROGRAM, OP_CODE, 'ZAGS', NVL(:NEW.ROWID, :OLD.ROWID), V_ACTION, V_MODULE, Decode(OP_Code, 'D', :OLD.ZAGS_ID, :NEW.ZAGS_ID), Decode(OP_Code, 'D',null, null))
     RETURNING IACTION_ID INTO New_ID;

  tabData := AUP_UTIL.T_TabAu ();

IF UPDATING ('ZAGS_ADR') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ZAGS_ADR', :NEW.ZAGS_ADR, :OLD.ZAGS_ADR); END IF;
IF UPDATING ('ZAGS_CITY_ABH') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ZAGS_CITY_ABH', :NEW.ZAGS_CITY_ABH, :OLD.ZAGS_CITY_ABH); END IF;
IF UPDATING ('ZAGS_ADR_ABH') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ZAGS_ADR_ABH', :NEW.ZAGS_ADR_ABH, :OLD.ZAGS_ADR_ABH); END IF;
IF UPDATING ('ZAGS_RUK_ABH') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ZAGS_RUK_ABH', :NEW.ZAGS_RUK_ABH, :OLD.ZAGS_RUK_ABH); END IF;
IF UPDATING ('ADDR') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ADDR', :NEW.ADDR, :OLD.ADDR); END IF;
IF UPDATING ('ZAGS_ID') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ZAGS_ID', :NEW.ZAGS_ID, :OLD.ZAGS_ID); END IF;
IF UPDATING ('ZAGS_OTD') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ZAGS_OTD', :NEW.ZAGS_OTD, :OLD.ZAGS_OTD); END IF;
IF UPDATING ('ZAGS_NAME') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ZAGS_NAME', :NEW.ZAGS_NAME, :OLD.ZAGS_NAME); END IF;
IF UPDATING ('ZAGS_RUK') OR INSERTING OR DELETING THEN AUP_UTIL.Put_Data(tabData, 'ZAGS_RUK', :NEW.ZAGS_RUK, :OLD.ZAGS_RUK); END IF;

  AUP_UTIL.Put_Data (New_ID, tabData);

END T_A_IDU_ZAGS;
/

prompt
prompt Creating trigger T_BRN_BIRTH_ACT
prompt ================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_BRN_BIRTH_ACT"
  before insert ON "BRN_BIRTH_ACT"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  :new.BR_ACT_ID := S_BRN_BIRTH_ACT.NEXTVAL;
END T_BRN_BIRTH_ACT;
/

prompt
prompt Creating trigger T_CUS
prompt ======================
prompt
CREATE OR REPLACE TRIGGER XXI."T_CUS"
  before insert ON "CUS"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  :new.ICUSNUM := S_CUS.NEXTVAL;
END T_CUS;
/

prompt
prompt Creating trigger T_CUS_ADDR
prompt ===========================
prompt
CREATE OR REPLACE TRIGGER XXI."T_CUS_ADDR"
  before insert ON "CUS_ADDR"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  :new.ID_ADDR := s_cus_addr.nextval;
END T_CUS_ADDR;
/

prompt
prompt Creating trigger T_CUS_CITIZEN
prompt ==============================
prompt
CREATE OR REPLACE TRIGGER XXI."T_CUS_CITIZEN"
  before insert ON "CUS_CITIZEN"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  :new.ID := S_CUS_CITIZEN.NEXTVAL;
END T_CUS_CITIZEN;
/

prompt
prompt Creating trigger T_CUS_CITIZEN_TEMP
prompt ===================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_CUS_CITIZEN_TEMP"
  before insert ON "CUS_CITIZEN_TEMP"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
declare
  max_id number;
BEGIN
  select nvl(max(ID), 0) into max_id from CUS_CITIZEN_TEMP;
  :new.ID := max_id + 1;
END T_CUS_CITIZEN_TEMP;
/

prompt
prompt Creating trigger T_CUS_DOCUM
prompt ============================
prompt
CREATE OR REPLACE TRIGGER XXI."T_CUS_DOCUM"
  before insert ON "CUS_DOCUM"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  :new.ID_DOC := S_CUS_DOCUM.NEXTVAL;
END T_CUS_DOCUM;
/

prompt
prompt Creating trigger T_CUS_DOCUM_TEMP
prompt =================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_CUS_DOCUM_TEMP"
  before insert ON "CUS_DOCUM_TEMP"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
declare
  max_id number;
BEGIN
  select nvl(max(ID_DOC), 0) into max_id from CUS_DOCUM_TEMP;
  :new.ID_DOC := max_id + 1;
END T_CUS_DOCUM_TEMP;
/

prompt
prompt Creating trigger T_CUS_UPDATE
prompt =============================
prompt
CREATE OR REPLACE TRIGGER XXI."T_CUS_UPDATE"
  before update ON "CUS"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN

  :new.CCUSNAME := :new.CCUSLAST_NAME || ' ' || :new.CCUSFIRST_NAME || ' ' ||
                   :new.CCUSMIDDLE_NAME;

  :new.CCUSNAME_SH := :new.CCUSLAST_NAME || ' ' || case
                        when substr(:new.CCUSFIRST_NAME, 1, 1) is null then
                         ''
                        else
                         substr(:new.CCUSFIRST_NAME, 1, 1) || '.'
                      end || case
                        when substr(:new.CCUSMIDDLE_NAME, 1, 1) is null then
                         ''
                        else
                         substr(:new.CCUSMIDDLE_NAME, 1, 1) || '.'
                      end;
END T_CUS_UPDATE;
/

prompt
prompt Creating trigger T_DEATH_CERT
prompt =============================
prompt
CREATE OR REPLACE TRIGGER XXI."T_DEATH_CERT"
  before insert ON "DEATH_CERT"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  :new.DC_ID := S_DEATH_CERT.NEXTVAL;
END T_DEATH_CERT;
/

prompt
prompt Creating trigger T_DIVORCE_CERT
prompt ===============================
prompt
CREATE OR REPLACE TRIGGER XXI."T_DIVORCE_CERT"
  before insert ON "DIVORCE_CERT"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  :new.DIVC_ID := S_DIVORCE_CERT.NEXTVAL;
END T_DIVORCE_CERT;
/

prompt
prompt Creating trigger T_LOG_ERROR
prompt ============================
prompt
CREATE OR REPLACE TRIGGER XXI."T_LOG_ERROR"
  before insert ON "LOG_ERROR"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
declare
  max_id number;
BEGIN
  select nvl(max(ERID), 0) + 1 into max_id from LOG_ERROR;
  :new.ERID := max_id;
END T_LOG_ERROR;
/

prompt
prompt Creating trigger T_LOGS
prompt =======================
prompt
CREATE OR REPLACE TRIGGER XXI."T_LOGS"
  before insert ON "LOGS"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  :new.ID := S_LOGS.NEXTVAL;
END T_LOGS;
/

prompt
prompt Creating trigger T_MC_MERCER
prompt ============================
prompt
CREATE OR REPLACE TRIGGER XXI."T_MC_MERCER"
  before insert ON "MC_MERCER"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  :new.MERCER_ID := S_MC_MERCER.NEXTVAL;
END T_MC_MERCER;
/

prompt
prompt Creating trigger T_NATIONALITY
prompt ==============================
prompt
CREATE OR REPLACE TRIGGER XXI."T_NATIONALITY"
  before insert ON "NATIONALITY"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  :new.ID := S_NATIONALITY.NEXTVAL;
END T_NATIONALITY;
/

prompt
prompt Creating trigger T_NT_DOV
prompt =========================
prompt
CREATE OR REPLACE TRIGGER XXI."T_NT_DOV"
  before insert ON "NT_DOV"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  :new.ID := S_NT_DOV.NEXTVAL;
END T_NT_DOV;
/

prompt
prompt Creating trigger T_ODB_ACTION
prompt =============================
prompt
CREATE OR REPLACE TRIGGER XXI."T_ODB_ACTION"
  before insert ON "ODB_ACTION"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  :new.ACT_ID := S_ODB_ACTION.NEXTVAL;
END T_ODB_ACTION;
/

prompt
prompt Creating trigger T_ODB_GROUP_USR
prompt ================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_ODB_GROUP_USR"
  before insert ON "ODB_GROUP_USR"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  if :new.GRP_ID is null then
    :new.GRP_ID := S_ODB_GROUP_USR.NEXTVAL;
  end if;
END T_ODB_GROUP_USR;
/

prompt
prompt Creating trigger T_ODB_MNU
prompt ==========================
prompt
CREATE OR REPLACE TRIGGER XXI."T_ODB_MNU"
  before insert ON "ODB_MNU"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  :new.MNU_ID := S_ODB_MNU.NEXTVAL;
END T_ODB_MNU;
/

prompt
prompt Creating trigger T_PATERN_CERT
prompt ==============================
prompt
CREATE OR REPLACE TRIGGER XXI."T_PATERN_CERT"
  before insert ON "PATERN_CERT"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  :new.PC_ID := S_PATERN_CERT.NEXTVAL;
END T_PATERN_CERT;
/

prompt
prompt Creating trigger T_PROJECT
prompt ==========================
prompt
CREATE OR REPLACE TRIGGER XXI."T_PROJECT"
  before insert ON "PROJECT"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
declare
  next_id number;
BEGIN

  -- автоинкремент ИД
  if :new.PRJ_ID is null then
    next_id     := S_PROJECT.NEXTVAL;
    :new.PRJ_ID := next_id;
  end if;

/*  IF :NEW.IS_FOLDER = 'N' THEN
    :new.VERSION := 1;
  END IF;*/

END T_PROJECT;
/

prompt
prompt Creating trigger T_PROJECT_AFT
prompt ==============================
prompt
CREATE OR REPLACE TRIGGER XXI."T_PROJECT_AFT"
  after insert ON "PROJECT"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  -- добавим историю версии
  if :new.IS_FOLDER = 'N' then
    insert into PRJ_FL_VER_HIST (PRJ_ID, VERISION) values (:new.PRJ_ID, 1);
  end if;

END T_PROJECT_AFT;
/

prompt
prompt Creating trigger T_REPORTS
prompt ==========================
prompt
CREATE OR REPLACE TRIGGER XXI."T_REPORTS"
  before insert ON "REPORTS"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  if :new.REP_ID is null then
    :new.REP_ID := S_REPORTS.NEXTVAL;
  end if;
END T_REPORTS;
/

prompt
prompt Creating trigger T_REP_PARAMS
prompt =============================
prompt
CREATE OR REPLACE TRIGGER XXI."T_REP_PARAMS"
  before insert ON "REP_PARAMS"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
DECLARE
  MAX_ID NUMBER;
BEGIN

  SELECT (SELECT NVL(MAX(PRM_ID), 0) + 1
            FROM REP_PARAMS
           WHERE REP_ID = :NEW.REP_ID)
    INTO MAX_ID
    FROM DUAL;

  :new.PRM_ID := MAX_ID;

END T_REP_PARAMS;
/

prompt
prompt Creating trigger T_UPDATE_ABH_NAME
prompt ==================================
prompt
CREATE OR REPLACE TRIGGER XXI."T_UPDATE_ABH_NAME"
  before insert ON "UPDATE_ABH_NAME"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  :new.ID := S_UPDATE_ABH_NAME.NEXTVAL;
END T_UPDATE_ABH_NAME;
/

prompt
prompt Creating trigger T_UPDATE_NAME
prompt ==============================
prompt
CREATE OR REPLACE TRIGGER XXI."T_UPDATE_NAME"
  before insert ON "UPDATE_NAME"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  :new.ID := S_UPDATE_NAME.NEXTVAL;
END T_UPDATE_NAME;
/

prompt
prompt Creating trigger T_UPD_NAT
prompt ==========================
prompt
CREATE OR REPLACE TRIGGER XXI."T_UPD_NAT"
  before insert ON "UPD_NAT"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  :new.ID := S_UPD_NAT.NEXTVAL;
END T_UPD_NAT;
/

prompt
prompt Creating trigger T_USR
prompt ======================
prompt
CREATE OR REPLACE TRIGGER XXI."T_USR"
  before insert ON "USR"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  if :new.IUSRID is null then
    :new.IUSRID := S_USR.NEXTVAL;
  end if;
END USR_ID;
/

prompt
prompt Creating trigger T_ZAGS
prompt =======================
prompt
CREATE OR REPLACE TRIGGER XXI."T_ZAGS"
  before insert ON "ZAGS"
  REFERENCING NEW AS NEW OLD AS OLD
  FOR EACH ROW
BEGIN
  if :new.ZAGS_ID is null then
    :new.ZAGS_ID := S_ZAGS.NEXTVAL;
  end if;
END ZAGS_ID;
/


prompt Done
spool off
set define on
