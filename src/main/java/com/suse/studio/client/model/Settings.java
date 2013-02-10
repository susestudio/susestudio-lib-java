package com.suse.studio.client.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Settings {
	
	@Element(name = "memory_size")
	private int memorySize;

	@Element(name = "disk_size")
	private int diskSize;

	@Element(name = "swap_size")
	private int swapSize;

	@Element(name = "pae_enabled")
	private boolean paeEnabled;

	@Element(name = "xen_host_mode_enabled", required = false)
	private boolean xenHostModeEnabled;

	@Element(name = "cdrom_enabled")
	private boolean cdromEnabled;

	@Element(name = "webyast_enabled")
	private boolean webYaSTEnabled;

	@Element(name = "live_installer_enabled")
	private boolean liveInstallerEnabled;

	@Element(name = "public_clonable", required = false)
	private boolean publicClonable;

	@Element
	private int runlevel;

	@Element(name = "automatic_login", required = false)
	private String automaticLogin;

	public int getMemorySize() {
		return memorySize;
	}

	public int getDiskSize() {
		return diskSize;
	}

	public int getSwapSize() {
		return swapSize;
	}

	public boolean isPaeEnabled() {
		return paeEnabled;
	}

	public boolean isXenHostModeEnabled() {
		return xenHostModeEnabled;
	}

	public boolean isCdromEnabled() {
		return cdromEnabled;
	}

	public boolean isWebYaSTEnabled() {
		return webYaSTEnabled;
	}

	public boolean isLiveInstallerEnabled() {
		return liveInstallerEnabled;
	}

	public boolean isPublicClonable() {
		return publicClonable;
	}

	public int getRunlevel() {
		return runlevel;
	}

	public String getAutomaticLogin() {
		return automaticLogin;
	}

	public void setMemorySize(int memorySize) {
		this.memorySize = memorySize;
	}

	public void setDiskSize(int diskSize) {
		this.diskSize = diskSize;
	}

	public void setSwapSize(int swapSize) {
		this.swapSize = swapSize;
	}

	public void setPaeEnabled(boolean paeEnabled) {
		this.paeEnabled = paeEnabled;
	}

	public void setXenHostModeEnabled(boolean xenHostModeEnabled) {
		this.xenHostModeEnabled = xenHostModeEnabled;
	}

	public void setCdromEnabled(boolean cdromEnabled) {
		this.cdromEnabled = cdromEnabled;
	}

	public void setWebYaSTEnabled(boolean webYaSTEnabled) {
		this.webYaSTEnabled = webYaSTEnabled;
	}

	public void setLiveInstallerEnabled(boolean liveInstallerEnabled) {
		this.liveInstallerEnabled = liveInstallerEnabled;
	}

	public void setPublicClonable(boolean publicClonable) {
		this.publicClonable = publicClonable;
	}

	public void setRunlevel(int runlevel) {
		this.runlevel = runlevel;
	}

	public void setAutomaticLogin(String automaticLogin) {
		this.automaticLogin = automaticLogin;
	}	
}
