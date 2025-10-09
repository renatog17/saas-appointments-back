ALTER TABLE public.agendamento
    ADD CONSTRAINT agendamento_fk_procedimento FOREIGN KEY (procedimento_id)
    REFERENCES public.procedimento (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;