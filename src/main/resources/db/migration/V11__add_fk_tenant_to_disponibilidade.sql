ALTER TABLE public.disponibilidade
    ADD CONSTRAINT disponibilidade_fk_tenant FOREIGN KEY (tenant_id)
    REFERENCES public.tenant (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;