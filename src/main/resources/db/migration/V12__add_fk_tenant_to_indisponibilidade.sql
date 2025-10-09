ALTER TABLE public.indisponibilidade
    ADD CONSTRAINT indisponibilidade_fk_tenant FOREIGN KEY (tenant_id)
    REFERENCES public.tenant (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;