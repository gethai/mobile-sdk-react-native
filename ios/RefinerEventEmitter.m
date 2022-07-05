#import "RefinerEventEmitter.h"

@interface RefinerEventEmitter ()
@property BOOL shouldEmit;
@end

@implementation RefinerEventEmitter

RCT_EXPORT_MODULE();

- (dispatch_queue_t)methodQueue
{
  return dispatch_get_main_queue();
}

- (NSArray<NSString *> *)supportedEvents
{
  return @[ @"onRefinerModalDismissed" ];
}

- (void)startObserving
{
  _shouldEmit = YES;
}

- (void)stopObserving
{
  _shouldEmit = NO;
}

// Emit event when modal is dismissed
- (void)modalDismissed:(BOOL)isActive
{
  if (_shouldEmit) {
      [self sendEventWithName:@"onRefinerModalDismissed" body:@{@"isActive" : @(isActive)}];
  }
}
@end
