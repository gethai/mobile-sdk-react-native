#ifndef RNRefinerModule_h
#define RNRefinerModule_h

#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

@interface RefinerEventEmitter : RCTEventEmitter <RCTBridgeModule>
- (void)modalDismissed:(BOOL)isActive;
@end
#endif
