ALTER TABLE public.tenant
ADD CONSTRAINT uk_tenant_slug UNIQUE (slug);
