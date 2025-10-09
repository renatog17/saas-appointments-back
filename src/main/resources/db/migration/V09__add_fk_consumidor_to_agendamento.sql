ALTER TABLE public.agendamento
    ADD CONSTRAINT agendamento_fk_consumidor FOREIGN KEY (consumidor_id)
    REFERENCES public.consumidor (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;