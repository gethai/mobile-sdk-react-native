
#import "RNRefiner.h"
#import <RefinerSDK/RefinerSDK-Swift.h>
@import RefinerSDK;

@implementation RNRefiner

bool shouldEmit;

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(initialize:(NSString *)projectId)
{
    [[Refiner instance] initializeWithProjectId: projectId];
    [self registerCallback];
}

RCT_EXPORT_METHOD(identifyUser:(NSString *)userId withUserTraits:(NSDictionary *)userTraits withLocale:(NSString *)locale)
{
    [[Refiner instance] identifyUserWithUserId: userId userTraits: userTraits locale: locale error: nil];
}

RCT_EXPORT_METHOD(resetUser)
{
    [[Refiner instance] resetUser];
}

RCT_EXPORT_METHOD(trackEvent:(NSString *)eventName)
{
    [[Refiner instance] trackEventWithName: eventName];
}

RCT_EXPORT_METHOD(trackScreen:(NSString *)screenName)
{
    [[Refiner instance] trackScreenWithName: screenName];
}

RCT_EXPORT_METHOD(showForm:(NSString *)formUuid withForce:(BOOL *)force)
{
    [[Refiner instance] showFormWithUuid: formUuid force: force];
}

RCT_EXPORT_METHOD(attachToResponse:(NSDictionary *)contextualData)
{
    [[Refiner instance] attachToResponseWithData: contextualData];
}

- (NSArray<NSString *> *)supportedEvents
{
  return @[ @"onShow", @"onBeforeShow", @"onClose", @"onComplete"];
}

- (void)startObserving
{
  shouldEmit = YES;
}

- (void)stopObserving
{
  shouldEmit = NO;
}

-(void)registerCallback {
    NSLog(@"Refiner registerCallback***********");
    Refiner.instance.onBeforeShow = ^(NSString *formId, NSObject* formConfig) {
        NSLog(@"Refiner onBeforeShow*********** %@", formId);
        if(shouldEmit)
            [self sendEventWithName:@"onBeforeShow" body:@{}]; 
    };
    Refiner.instance.onShow = ^(NSString *formId) {
        NSLog(@"Refiner onShow*********** %@", formId);
        if(shouldEmit)
            [self sendEventWithName:@"onShow" body:@{}];
    };
    Refiner.instance.onClose = ^(NSString *formId) {
        NSLog(@"Refiner onClose*********** %@", formId);
        if(shouldEmit)
            [self sendEventWithName:@"onClose" body:@{}];
    };
    Refiner.instance.onComplete = ^(NSString *formId, NSString *formData) {
        NSLog(@"Refiner onComplete*********** %@  %@", formId, formData);
        if(shouldEmit)
            [self sendEventWithName:@"onComplete" body:@{}];
    };
}
@end
