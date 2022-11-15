--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.10
-- Dumped by pg_dump version 9.6.10

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: _chats; Type: TABLE; Schema: public; Owner: rebasedata
--

CREATE TABLE public._chats (
    chat_id smallint,
    chat_state smallint
);


ALTER TABLE public._chats OWNER TO rebasedata;

--
-- Name: _conclusion; Type: TABLE; Schema: public; Owner: rebasedata
--

CREATE TABLE public._conclusion (
    id smallint,
    conclusion_text character varying(35) DEFAULT NULL::character varying
);


ALTER TABLE public._conclusion OWNER TO rebasedata;

--
-- Name: _doctor; Type: TABLE; Schema: public; Owner: rebasedata
--

CREATE TABLE public._doctor (
    id smallint,
    surename character varying(7) DEFAULT NULL::character varying,
    name character varying(10) DEFAULT NULL::character varying,
    s_name character varying(9) DEFAULT NULL::character varying,
    phone_number bigint,
    password integer,
    hospital_id smallint
);


ALTER TABLE public._doctor OWNER TO rebasedata;

--
-- Name: _hospital; Type: TABLE; Schema: public; Owner: rebasedata
--

CREATE TABLE public._hospital (
    id smallint,
    hospital_name character varying(14) DEFAULT NULL::character varying,
    hospital_city character varying(8) DEFAULT NULL::character varying,
    hospital_street character varying(14) DEFAULT NULL::character varying,
    hospital_home smallint
);


ALTER TABLE public._hospital OWNER TO rebasedata;

--
-- Name: _messages; Type: TABLE; Schema: public; Owner: rebasedata
--

CREATE TABLE public._messages (
    message_id smallint,
    chat_id smallint,
    at_user_id smallint,
    user_type character varying(7) DEFAULT NULL::character varying,
    text character varying(53) DEFAULT NULL::character varying,
    sound_server_link character varying(48) DEFAULT NULL::character varying,
    message_datetime character varying(19) DEFAULT NULL::character varying
);


ALTER TABLE public._messages OWNER TO rebasedata;

--
-- Name: _patients; Type: TABLE; Schema: public; Owner: rebasedata
--

CREATE TABLE public._patients (
    id smallint,
    surename character varying(6) DEFAULT NULL::character varying,
    name character varying(10) DEFAULT NULL::character varying,
    s_name character varying(9) DEFAULT NULL::character varying,
    phone_number bigint,
    insurance_policy_number bigint,
    password integer
);


ALTER TABLE public._patients OWNER TO rebasedata;

--
-- Name: _report; Type: TABLE; Schema: public; Owner: rebasedata
--

CREATE TABLE public._report (
    id character varying(1) DEFAULT NULL::character varying,
    treatment_id character varying(1) DEFAULT NULL::character varying,
    report_date character varying(1) DEFAULT NULL::character varying
);


ALTER TABLE public._report OWNER TO rebasedata;

--
-- Name: _sound; Type: TABLE; Schema: public; Owner: rebasedata
--

CREATE TABLE public._sound (
    id smallint,
    sound_server_link character varying(11) DEFAULT NULL::character varying
);


ALTER TABLE public._sound OWNER TO rebasedata;

--
-- Name: _symptoms; Type: TABLE; Schema: public; Owner: rebasedata
--

CREATE TABLE public._symptoms (
    id smallint,
    symptoms_name character varying(37) DEFAULT NULL::character varying
);


ALTER TABLE public._symptoms OWNER TO rebasedata;

--
-- Name: _treatment; Type: TABLE; Schema: public; Owner: rebasedata
--

CREATE TABLE public._treatment (
    id smallint,
    chat_id character varying(1) DEFAULT NULL::character varying,
    patient_id smallint,
    doctor_id character varying(1) DEFAULT NULL::character varying,
    start_date character varying(19) DEFAULT NULL::character varying,
    symptoms_id smallint,
    sound_serverlink_id character varying(1) DEFAULT NULL::character varying,
    conclusion_id character varying(1) DEFAULT NULL::character varying
);


ALTER TABLE public._treatment OWNER TO rebasedata;

--
-- Data for Name: _chats; Type: TABLE DATA; Schema: public; Owner: rebasedata
--

COPY public._chats (chat_id, chat_state) FROM stdin;
1	0
\.


--
-- Data for Name: _conclusion; Type: TABLE DATA; Schema: public; Owner: rebasedata
--

COPY public._conclusion (id, conclusion_text) FROM stdin;
1	Рекомендовано блаблаблаблаблабла...
2	Пить больше воды
3	Есть здоровую пищу
4	Здаров
\.


--
-- Data for Name: _doctor; Type: TABLE DATA; Schema: public; Owner: rebasedata
--

COPY public._doctor (id, surename, name, s_name, phone_number, password, hospital_id) FROM stdin;
1	Смирнов	Константин	Сергеевич	9534500038	123456789	1
\.


--
-- Data for Name: _hospital; Type: TABLE DATA; Schema: public; Owner: rebasedata
--

COPY public._hospital (id, hospital_name, hospital_city, hospital_street, hospital_home) FROM stdin;
1	Поликлиника №1	Оренбург	ул. Терешковой	5
\.


--
-- Data for Name: _messages; Type: TABLE DATA; Schema: public; Owner: rebasedata
--

COPY public._messages (message_id, chat_id, at_user_id, user_type, text, sound_server_link, message_datetime) FROM stdin;
1	1	1	doctor	Привет		2021-12-18 19:53:22
2	1	2	patient	И тебе привет		2021-12-18 19:54:22
3	1	2	patient	Это чат обращения		2021-12-18 19:54:22
4	1	1	doctor	В нем пациент и врач могут обмениваться сообщениями		2021-12-18 19:54:22
5	1	1	doctor	Как текстовыми, так и аудиозаписями		2021-12-19 21:47:03
6	1	2	patient	Это помогает врачам поддерживать контакт с пациентами		2021-12-19 21:50:18
7	1	2	patient	И эффективно строить взаимодействие		2021-12-19 21:50:38
8	1	2	patient	Проверка	/www/u1554079.isp.regruhosting.ru/audio/1-10.wav	2021-12-20 12:34:40
9	1	1	doctor	Раз два три		
10	1	2	patient		/www/u1554079.isp.regruhosting.ru/audio/1-12.wav	2022-05-07 11:18:32
11	1	1	doctor	проверка		2022-05-22 19:18:06
12	1	2	patient		/www/u1554079.isp.regruhosting.ru/audio/1-12.wav	2022-05-23 11:17:44
13	1	2	patient	Новое сообщение		2022-05-23 15:57:38
14	1	2	patient	Новое сообщение	/www/u1554079.isp.regruhosting.ru/audio/1-14.wav	2022-05-23 15:58:02
15	1	2	patient	новое сообщение		2022-05-23 16:48:13
16	1	2	patient	новое сообщение	/www/u1554079.isp.regruhosting.ru/audio/1-16.wav	2022-05-23 16:48:30
\.


--
-- Data for Name: _patients; Type: TABLE DATA; Schema: public; Owner: rebasedata
--

COPY public._patients (id, surename, name, s_name, phone_number, insurance_policy_number, password) FROM stdin;
1	Иванов	Константин	Сергеевич	9224500038	16445231654	123456789
2	Иванов	Иван	Иванович	9134500037	14651654541	123456789
\.


--
-- Data for Name: _report; Type: TABLE DATA; Schema: public; Owner: rebasedata
--

COPY public._report (id, treatment_id, report_date) FROM stdin;
\.


--
-- Data for Name: _sound; Type: TABLE DATA; Schema: public; Owner: rebasedata
--

COPY public._sound (id, sound_server_link) FROM stdin;
1	sound/1.ogg
\.


--
-- Data for Name: _symptoms; Type: TABLE DATA; Schema: public; Owner: rebasedata
--

COPY public._symptoms (id, symptoms_name) FROM stdin;
1	Боли в животе
2	Расстройства цикла сон-бодрствование
3	Болезни нервной системы
4	Болезни кожи
5	Болезни опорно-двигательного аппарата
6	Болезни органов дыхания
\.


--
-- Data for Name: _treatment; Type: TABLE DATA; Schema: public; Owner: rebasedata
--

COPY public._treatment (id, chat_id, patient_id, doctor_id, start_date, symptoms_id, sound_serverlink_id, conclusion_id) FROM stdin;
1	1	2	1	2021-05-13 17:29:03	1	1	3
2	1	2		2021-05-13 17:29:04	1	1	
4		2		2022-05-23 15:57:13	3		
5		2		2022-05-23 16:47:39	2		
6		2		2022-09-25 15:10:58	1		
\.


--
-- PostgreSQL database dump complete
--

