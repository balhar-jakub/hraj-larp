--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: administrator; Type: TABLE; Schema: public; Owner: hrajlarp; Tablespace: 
--

CREATE TABLE administrator (
    id integer NOT NULL
);


ALTER TABLE public.administrator OWNER TO hrajlarp;

--
-- Name: authorized_editor; Type: TABLE; Schema: public; Owner: hrajlarp; Tablespace: 
--

CREATE TABLE authorized_editor (
    id integer NOT NULL
);


ALTER TABLE public.authorized_editor OWNER TO hrajlarp;

--
-- Name: hraj_game_id_seq; Type: SEQUENCE; Schema: public; Owner: hrajlarp
--

CREATE SEQUENCE hraj_game_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.hraj_game_id_seq OWNER TO hrajlarp;

--
-- Name: game; Type: TABLE; Schema: public; Owner: hrajlarp; Tablespace: 
--

CREATE TABLE game (
    id integer DEFAULT nextval('hraj_game_id_seq'::regclass) NOT NULL,
    name text NOT NULL,
    date timestamp without time zone NOT NULL,
    anotation text NOT NULL,
    author text NOT NULL,
    image text NOT NULL,
    added_by integer NOT NULL,
    men_role integer,
    women_role integer,
    both_role integer,
    short_text text,
    place text,
    info text,
    about_game text,
    web text,
    larp_db text,
    "time" character varying(255),
    confirmed boolean DEFAULT false,
    replacements_text text,
    ordinary_player_text text,
    registration_started timestamp without time zone,
    action character varying(255),
    mail_prohibition boolean DEFAULT false,
    payment_finished boolean default false
);


ALTER TABLE public.game OWNER TO hrajlarp;

--
-- Name: hraj_user_id_seq; Type: SEQUENCE; Schema: public; Owner: hrajlarp
--

CREATE SEQUENCE hraj_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.hraj_user_id_seq OWNER TO hrajlarp;

--
-- Name: hraj_user; Type: TABLE; Schema: public; Owner: hrajlarp; Tablespace: 
--

CREATE TABLE hraj_user (
    id integer DEFAULT nextval('hraj_user_id_seq'::regclass) NOT NULL,
    name text NOT NULL,
    last_name text NOT NULL,
    user_name text NOT NULL,
    password text NOT NULL,
    email text NOT NULL,
    phone text NOT NULL,
    gender integer NOT NULL,
    mail_information boolean
);


ALTER TABLE public.hraj_user OWNER TO hrajlarp;

--
-- Name: accountant; Type: TABLE; Schema: public; Owner: hrajlarp; Tablespace:
--

create table accountant (
    user_id integer not null
);

ALTER TABLE ONLY accountant
    ADD CONSTRAINT accountant_user_id_fkey FOREIGN KEY (user_id) REFERENCES hraj_user(id);

--
-- Name: places_finder; Type: TABLE; Schema: public; Owner: hrajlarp; Tablespace:
--

create table places_finder (
    user_id integer not null
);

ALTER TABLE ONLY places_finder
    ADD CONSTRAINT places_finder_user_id_fkey FOREIGN KEY (user_id) REFERENCES hraj_user(id);


--
-- Name: scheduler; Type: TABLE; Schema: public; Owner: hrajlarp; Tablespace:
--

create table scheduler (
    user_id integer not null
);

ALTER TABLE ONLY scheduler
    ADD CONSTRAINT scheduler_user_id_fkey FOREIGN KEY (user_id) REFERENCES hraj_user(id);


--
-- Name: hraj_user_attended_game_seq; Type: SEQUENCE; Schema: public; Owner: hrajlarp
--

CREATE SEQUENCE hraj_user_attended_game_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.hraj_user_attended_game_seq OWNER TO hrajlarp;

--
-- Name: prereg_notifications; Type: TABLE; Schema: public; Owner: hrajlarp; Tablespace: 
--

CREATE TABLE prereg_notifications (
    user_id integer NOT NULL,
    game_id integer NOT NULL,
    notify_date timestamp without time zone
);


ALTER TABLE public.prereg_notifications OWNER TO hrajlarp;

--
-- Name: user_attended_game; Type: TABLE; Schema: public; Owner: hrajlarp; Tablespace: 
--

CREATE TABLE user_attended_game (
    user_id integer NOT NULL,
    game_id integer NOT NULL,
    added timestamp without time zone DEFAULT now(),
    substitute boolean,
    substitutetext character varying(255),
    payed boolean,
    automatic boolean,
    variable_symbol text
);


ALTER TABLE public.user_attended_game OWNER TO hrajlarp;

--
-- Name: user_is_editor; Type: TABLE; Schema: public; Owner: hrajlarp; Tablespace: 
--

CREATE TABLE user_is_editor (
    user_id integer NOT NULL,
    game_id integer NOT NULL
);


ALTER TABLE public.user_is_editor OWNER TO hrajlarp;

--
-- Name: administrator_pkey; Type: CONSTRAINT; Schema: public; Owner: hrajlarp; Tablespace: 
--

ALTER TABLE ONLY administrator
    ADD CONSTRAINT administrator_pkey PRIMARY KEY (id);


--
-- Name: authorized_editor_pkey; Type: CONSTRAINT; Schema: public; Owner: hrajlarp; Tablespace: 
--

ALTER TABLE ONLY authorized_editor
    ADD CONSTRAINT authorized_editor_pkey PRIMARY KEY (id);


--
-- Name: game_pkey; Type: CONSTRAINT; Schema: public; Owner: hrajlarp; Tablespace: 
--

ALTER TABLE ONLY game
    ADD CONSTRAINT game_pkey PRIMARY KEY (id);


--
-- Name: hraj_user_pkey; Type: CONSTRAINT; Schema: public; Owner: hrajlarp; Tablespace: 
--

ALTER TABLE ONLY hraj_user
    ADD CONSTRAINT hraj_user_pkey PRIMARY KEY (id);


--
-- Name: prereg_notifications_pkey; Type: CONSTRAINT; Schema: public; Owner: hrajlarp; Tablespace: 
--

ALTER TABLE ONLY prereg_notifications
    ADD CONSTRAINT prereg_notifications_pkey PRIMARY KEY (user_id, game_id);


--
-- Name: user_attended_game_pkey; Type: CONSTRAINT; Schema: public; Owner: hrajlarp; Tablespace: 
--

ALTER TABLE ONLY user_attended_game
    ADD CONSTRAINT user_attended_game_pkey PRIMARY KEY (user_id, game_id);


--
-- Name: user_is_editor_pkey; Type: CONSTRAINT; Schema: public; Owner: hrajlarp; Tablespace: 
--

ALTER TABLE ONLY user_is_editor
    ADD CONSTRAINT user_is_editor_pkey PRIMARY KEY (user_id, game_id);


--
-- Name: administrator_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: hrajlarp
--

ALTER TABLE ONLY administrator
    ADD CONSTRAINT administrator_id_fkey FOREIGN KEY (id) REFERENCES hraj_user(id);


--
-- Name: game_added_by_fkey; Type: FK CONSTRAINT; Schema: public; Owner: hrajlarp
--

ALTER TABLE ONLY game
    ADD CONSTRAINT game_added_by_fkey FOREIGN KEY (added_by) REFERENCES hraj_user(id);


--
-- Name: user_attended_game_game_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: hrajlarp
--

ALTER TABLE ONLY user_attended_game
    ADD CONSTRAINT user_attended_game_game_id_fkey FOREIGN KEY (game_id) REFERENCES game(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: user_attended_game_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: hrajlarp
--

ALTER TABLE ONLY user_attended_game
    ADD CONSTRAINT user_attended_game_user_id_fkey FOREIGN KEY (user_id) REFERENCES hraj_user(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: user_is_editor_game_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: hrajlarp
--

ALTER TABLE ONLY user_is_editor
    ADD CONSTRAINT user_is_editor_game_id_fkey FOREIGN KEY (game_id) REFERENCES game(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: user_is_editor_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: hrajlarp
--

ALTER TABLE ONLY user_is_editor
    ADD CONSTRAINT user_is_editor_user_id_fkey FOREIGN KEY (user_id) REFERENCES hraj_user(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

