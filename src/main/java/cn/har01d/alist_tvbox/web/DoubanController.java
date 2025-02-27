package cn.har01d.alist_tvbox.web;

import cn.har01d.alist_tvbox.dto.Versions;
import cn.har01d.alist_tvbox.service.DoubanService;
import cn.har01d.alist_tvbox.service.IndexService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DoubanController {
    private final DoubanService service;
    private final IndexService indexService;

    public DoubanController(DoubanService service, IndexService indexService) {
        this.service = service;
        this.indexService = indexService;
    }

    @GetMapping("/versions")
    public Versions getRemoteVersion() {
        Versions versions = new Versions();
        service.getRemoteVersion(versions);
        versions.setIndex(indexService.getRemoteVersion().trim());
        String appVersion = service.getAppRemoteVersion().trim();
        String[] parts = appVersion.split("\n");
        if (parts.length > 1) {
            appVersion = parts[0];
            versions.setChangelog(parts[1]);
        }
        versions.setApp(appVersion);
        return versions;
    }
}
