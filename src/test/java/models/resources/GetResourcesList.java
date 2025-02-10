package models.resources;

import lombok.Data;

import java.util.List;

@Data
public class GetResourcesList {

        String page, per_page, total, total_pages;

        List<GetResourcesData> data;

        GetResourcesSupport support;

}
