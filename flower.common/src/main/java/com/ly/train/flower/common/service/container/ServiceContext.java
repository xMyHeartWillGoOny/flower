/**
 * Copyright © 2019 同程艺龙 (zhihui.li@ly.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ly.train.flower.common.service.container;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.AsyncContext;
import com.ly.train.flower.common.service.message.FlowMessage;
import com.ly.train.flower.common.service.web.Web;
import com.ly.train.flower.common.util.CloneUtil;

public class ServiceContext  {

  /**
   * 附属参数
   */
  private Map<String, Object> attachments;
  private String id = UUID.randomUUID().toString().replaceAll("-", "");
  private Web web;
  private boolean sync;
  private String flowName;

  private FlowMessage flowMessage;

  private ServiceContext() {}

  public static <T> ServiceContext context(T message) {
    return context(message, null);
  }

  public static <T> ServiceContext context(T message, AsyncContext ctx) {
    ServiceContext context = new ServiceContext();
    context.setFlowMessage(new FlowMessage(message));
    if (ctx != null) {
      context.setWeb(new Web(ctx));
    }
    return context;
  }



  /**
   * 从当前对象创建一个副本
   * 
   * @return {@link ServiceContext}
   */
  public ServiceContext newInstance() {
    ServiceContext serviceContext = new ServiceContext();
    serviceContext.setWeb(this.getWeb());
    serviceContext.attachments = attachments;
    serviceContext.id = this.id;
    serviceContext.setFlowMessage(CloneUtil.clone(flowMessage));
    serviceContext.setSync(this.sync);
    serviceContext.setFlowName(this.flowName);
    return serviceContext;
  }

  public Web getWeb() {
    return web;
  }

  public void setWeb(Web web) {
    this.web = web;
  }

  public void addAttachment(String key, Object value) {
    if (attachments == null) {
      attachments = new ConcurrentHashMap<String, Object>();
    }
    attachments.put(key, value);
  }

  public Object getAttachment(String key) {
    return attachments.get(key);
  }

  public void removeAttachment(String key) {
    attachments.remove(key);
  }



  public FlowMessage getFlowMessage() {
    return flowMessage;
  }

  public void setFlowMessage(FlowMessage flowMessage) {
    this.flowMessage = flowMessage;
  }

  /**
   * 服务ID
   * 
   * @return string
   */
  public String getId() {
    return id;
  }

  public boolean isSync() {
    return sync;
  }

  /**
   * 同步调用
   * 
   * @param sync
   */
  public void setSync(boolean sync) {
    this.sync = sync;
  }

  public String getFlowName() {
    return flowName;
  }

  public void setFlowName(String flowName) {
    this.flowName = flowName;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ServiceContext [attachments=");
    builder.append(attachments);
    builder.append(", web=");
    builder.append(web);
    builder.append("]");
    return builder.toString();
  }


}
